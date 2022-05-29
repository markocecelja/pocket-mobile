package com.mcecelja.pocket.enums

import androidx.fragment.app.Fragment
import com.mcecelja.pocket.R
import com.mcecelja.pocket.ui.chat.ChatFragment

enum class TabFragmentEnum(
    val position: Int,
    val fragment: Fragment,
    val title: String,
    val icon: Int
) {

    CHAT(
        0,
        ChatFragment.create(),
        "Razgovori",
        R.drawable.chat
    );

    companion object {
        fun getByPosition(position: Int): TabFragmentEnum? {
            for (tabFragmentEnum in values()) {
                if (tabFragmentEnum.position == position) {
                    return tabFragmentEnum
                }
            }

            return null
        }
    }
}