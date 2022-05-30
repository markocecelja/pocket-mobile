package com.mcecelja.pocket.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcecelja.pocket.R
import com.mcecelja.pocket.adapters.chat.ChatAdapter
import com.mcecelja.pocket.data.dto.chat.ChatDTO
import com.mcecelja.pocket.databinding.FragmentChatBinding
import com.mcecelja.pocket.listener.ChatItemClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatFragment : Fragment(), ChatItemClickListener {

    private lateinit var chatFragmentBinding: FragmentChatBinding

    private val chatViewModel by viewModel<ChatViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        chatFragmentBinding = FragmentChatBinding.inflate(inflater, container, false)

        chatViewModel.setChats()

        setupRecyclerView()

        chatViewModel.chats.observe(
            viewLifecycleOwner,
            { (chatFragmentBinding.rvChats.adapter as ChatAdapter).refreshData(it) })

        return chatFragmentBinding.root
    }

    private fun setupRecyclerView() {
        chatFragmentBinding.rvChats.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        chatFragmentBinding.rvChats.adapter =
            ChatAdapter(chatViewModel.chats.value ?: mutableListOf(), this)
    }

    override fun onChatClicked(chat: ChatDTO) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.fl_fragmentContainer,
                MessagesFragment.create(chat),
                MessagesFragment.TAG
            )
            .addToBackStack(TAG)
            .commit()
    }

    companion object {
        const val TAG = "Chats"
        fun create(): ChatFragment {
            return ChatFragment()
        }
    }
}