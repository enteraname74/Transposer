package com.github.enteraname74.transposer.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.enteraname74.transposer.fragments.FavouritesFragment
import com.github.enteraname74.transposer.fragments.ScalesFragment

class VpAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { ScalesFragment() }
            1 -> { FavouritesFragment() }
            else -> { throw Resources.NotFoundException("Position not found")}
        }
    }
}