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
import com.github.enteraname74.transposer.adapters.ScalesList
import com.github.enteraname74.transposer.classes.Scale

class ScalesFragment : Fragment(), ScalesList.OnScaleListener {
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : ScalesList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ScalesList(activity?.applicationContext as Context, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scales, container, false)

        recyclerView = view.findViewById(R.id.scales_recycler_view)
        recyclerView.adapter = adapter

        return view
    }

    override fun onScaleClick(position: Int) {
        Log.d("INFOS :", position.toString())
        val intent = Intent(context, SeeScaleActivity::class.java)
        intent.putExtra("POSITION", position)
        startActivity(intent)
    }
}