package com.github.enteraname74.transposer.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.activities.SeeScaleActivity
import com.github.enteraname74.transposer.activities.SeeTranspositionActivity
import com.github.enteraname74.transposer.adapters.TranspositionsList
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Transposition

class TranspositionsFragment : Fragment(), TranspositionsList.OnTranspositionListener {
    private lateinit var transpositionRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transpositions, container, false)

        transpositionRecyclerView = view.findViewById(R.id.transpositions_recycler_view)
        transpositionRecyclerView.adapter = TranspositionsList(context as Context,AppData.allTranspositions, this)

        return view
    }

    override fun onResume() {
        super.onResume()
        transpositionRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onTranspositionClick(position: Int) {
        val intent = Intent(context, SeeTranspositionActivity::class.java)
        intent.putExtra("POSITION", position)
        startActivity(intent)
    }

    override fun onTranspositionLongClick(position: Int) {

    }
}