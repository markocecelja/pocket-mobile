package com.mcecelja.pocket.adapters.chat

import android.os.Build
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.data.dto.chat.MessageDTO
import com.mcecelja.pocket.databinding.ItemMessageLeftBinding
import com.mcecelja.pocket.databinding.ItemMessageRightBinding
import com.mcecelja.pocket.enums.PreferenceEnum
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(message: MessageDTO) {

        val outputDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = LocalDateTime.parse(message.createdDateTime)
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            formatter.format(localDateTime)
        } else {
            message.createdDateTime
        }

        if(message.createdBy!!.id == PreferenceManager.getPreference(PreferenceEnum.CURRENT_USER_ID)) {
            val itemBinding = ItemMessageRightBinding.bind(itemView)
            itemBinding.tvMessage.text = message.text
            itemBinding.tvDate.text = outputDate
        } else {
            val itemBinding = ItemMessageLeftBinding.bind(itemView)
            itemBinding.tvMessage.text = message.text
            itemBinding.tvDate.text = outputDate
        }
    }
}