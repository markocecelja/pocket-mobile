package com.mcecelja.pocket.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mcecelja.pocket.enums.TabFragmentEnum

class PageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = ArrayList<TabFragmentEnum>()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }

    fun addFragment(fragment: TabFragmentEnum) {
        fragments.add(fragment)
    }
}