package com.mcecelja.pocket.adapters.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.data.dto.chat.MessageDTO
import com.mcecelja.pocket.databinding.ItemMessageLeftBinding
import com.mcecelja.pocket.databinding.ItemMessageRightBinding
import com.mcecelja.pocket.enums.PreferenceEnum

class MessageViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(message: MessageDTO) {

        if(message.createdBy!!.id == PreferenceManager.getPreference(PreferenceEnum.CURRENT_USER_ID)) {
            val itemBinding = ItemMessageRightBinding.bind(itemView)
            itemBinding.tvMessage.text = message.text
        } else {
            val itemBinding = ItemMessageLeftBinding.bind(itemView)
            itemBinding.tvMessage.text = message.text
        }
    }
}