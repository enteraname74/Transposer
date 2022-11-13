package com.github.enteraname74.transposer.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.enteraname74.transposer.fragments.FavouritesFragment
import com.github.enteraname74.transposer.fragments.ScalesFragment
import com.github.enteraname74.transposer.fragments.TranspositionsFragment

/*
La classe permettant de gérer les tabs et les fragments de l'activité principale
Elle hérite de FragmentStateAdapter pour gérer et afficher nos fragments.
 */
class VpAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { ScalesFragment() }
            1 -> { TranspositionsFragment() }
            2 -> { FavouritesFragment() }
            else -> { throw Resources.NotFoundException("Position not found")}
        }
    }
}