package com.mcecelja.pocket.adapters.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mcecelja.pocket.data.dto.chat.ChatDTO
import com.mcecelja.pocket.databinding.ItemChatBinding
import com.mcecelja.pocket.listener.ChatItemClickListener

class ChatViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(chat: ChatDTO, chatItemClickListener: ChatItemClickListener) {
        val itemBinding = ItemChatBinding.bind(itemView)
        itemBinding.tvChatName.text = chat.post.title

        itemView.setOnClickListener { chatItemClickListener.onChatClicked(chat) }
    }
}