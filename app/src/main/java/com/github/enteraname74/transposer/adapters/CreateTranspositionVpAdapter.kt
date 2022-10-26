package com.github.enteraname74.transposer.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.enteraname74.transposer.fragments.*

class CreateTranspositionVpAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> StartScaleFragment()
            1 -> StartInstrumentFragment()
            2 -> EndInstrumentFragment()
            3 -> EndScaleFragment()
            else -> { throw Resources.NotFoundException("Position not found")}
        }
    }
}