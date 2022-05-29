package com.mcecelja.pocket.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcecelja.pocket.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private lateinit var chatFragmentBinding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        chatFragmentBinding = FragmentChatBinding.inflate(inflater, container, false)

        return chatFragmentBinding.root
    }

    companion object {
        const val TAG = "Chats"
        fun create(): ChatFragment {
            return ChatFragment()
        }
    }
}