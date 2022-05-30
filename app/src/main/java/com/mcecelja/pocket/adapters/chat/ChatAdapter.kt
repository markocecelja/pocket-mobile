package com.mcecelja.pocket.adapters.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcecelja.pocket.R
import com.mcecelja.pocket.data.dto.chat.ChatDTO
import com.mcecelja.pocket.listener.ChatItemClickListener

class ChatAdapter(chats: List<ChatDTO>, private val chatItemClickListener: ChatItemClickListener) : RecyclerView.Adapter<ChatViewHolder>() {

    private val chats: MutableList<ChatDTO> = mutableListOf()
    init {
        refreshData(chats)
    }

    fun refreshData(chats: List<ChatDTO>) {
        this.chats.clear()
        this.chats.addAll(chats)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)

        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chats[position]
        holder.bind(chat, chatItemClickListener)

    }

    override fun getItemCount(): Int {
        return chats.size
    }
}