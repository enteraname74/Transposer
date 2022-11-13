package com.github.enteraname74.transposer.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.enteraname74.transposer.fragments.EndInstrumentFragment
import com.github.enteraname74.transposer.fragments.EndScaleFragment
import com.github.enteraname74.transposer.fragments.StartInstrumentFragment
import com.github.enteraname74.transposer.fragments.StartScaleFragment

/*
La classe permettant de gérer les tabs et les fragments de l'activité de création de transpositions.
Elle hérite de FragmentStateAdapter pour gérer et afficher nos fragments.
 */
class CreateTranspositionVpAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StartScaleFragment()
            1 -> StartInstrumentFragment()
            2 -> EndInstrumentFragment()
            3 -> EndScaleFragment()
            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }
}