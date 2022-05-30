package com.mcecelja.pocket.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mcecelja.pocket.adapters.chat.MessageAdapter
import com.mcecelja.pocket.data.dto.chat.ChatDTO
import com.mcecelja.pocket.data.dto.chat.MessageDTO
import com.mcecelja.pocket.databinding.FragmentMessagesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class MessagesFragment : Fragment() {

    private lateinit var messagesFragmentBinding: FragmentMessagesBinding

    private lateinit var stompClient: StompClient

    private val messagesViewModel by viewModel<MessagesViewModel>()

    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        messagesFragmentBinding = FragmentMessagesBinding.inflate(inflater, container, false)

        setupRecyclerView()

        messagesViewModel.messages.observe(
            viewLifecycleOwner,
            { (messagesFragmentBinding.rvMessages.adapter as MessageAdapter).refreshData(it) })

        messagesViewModel.chat.observe(
            viewLifecycleOwner,
            { messagesViewModel.setMessages(it.id) })

        val chat = arguments?.getSerializable(KEY_CHAT) as ChatDTO
        chat.let {
            messagesViewModel.setChat(chat)
        }

        messagesFragmentBinding.ibSend.setOnClickListener { sendMessage() }

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://192.168.36.14:8443/chat")
        stompClient.connect()

        stompClient.topic("/topic/messages").subscribe {
            messagesViewModel.addMessage(gson.fromJson(it.payload, MessageDTO::class.java))
        }

        return messagesFragmentBinding.root
    }

    private fun setupRecyclerView() {
        messagesFragmentBinding.rvMessages.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            true
        )

        messagesFragmentBinding.rvMessages.adapter =
            MessageAdapter(messagesViewModel.messages.value ?: mutableListOf())
    }

    companion object {
        const val TAG = "Messages"
        private const val KEY_CHAT = "chat"

        fun create(chat: ChatDTO): MessagesFragment {
            val args = Bundle()
            args.putSerializable(KEY_CHAT, chat)

            val fragment = MessagesFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private fun sendMessage() {

        val text = messagesFragmentBinding.etMessage.text.toString()

        messagesViewModel.sendMessage(text)

        messagesFragmentBinding.etMessage.text?.clear()
    }
}