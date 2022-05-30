package com.mcecelja.pocket.adapters.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcecelja.pocket.R
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.data.dto.chat.MessageDTO
import com.mcecelja.pocket.enums.PreferenceEnum

class MessageAdapter(messages: List<MessageDTO>) : RecyclerView.Adapter<MessageViewHolder>() {

    private val messages: MutableList<MessageDTO> = mutableListOf()

    init {
        refreshData(messages)
    }

    fun refreshData(messages: List<MessageDTO>) {
        this.messages.clear()
        this.messages.addAll(messages)
        this.notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]

        if (message.createdBy!!.id == PreferenceManager.getPreference(PreferenceEnum.CURRENT_USER_ID)) {
            return 0;
        }

        return 1;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        var view: View? = null

        when (viewType) {

            0 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_right, parent, false)
            }

            1 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_left, parent, false)
            }
        }

        return MessageViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)

    }

    override fun getItemCount(): Int {
        return messages.size
    }
}