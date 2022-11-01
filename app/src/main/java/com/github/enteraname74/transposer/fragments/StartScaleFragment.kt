package com.github.enteraname74.transposer.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.activities.TranspositionActivity
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Scale

class StartScaleFragment : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start_scale, container, false)

        val scaleSpinner = view.findViewById<Spinner>(R.id.scale_spinner)
        val scaleAdapter = ArrayAdapter(context as Context, android.R.layout.simple_spinner_item, AppData.scalesList.map{ it.scaleName })

        scaleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        scaleSpinner.adapter = scaleAdapter
        scaleSpinner.onItemSelectedListener = this

        return view
    }

    override fun onResume() {
        super.onResume()
        // Le changement de position courante se fait quand on change de fragment (à la main, ou en utilisant les boutons)
        (activity as TranspositionActivity).currentFragmentPos = 0
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        startScale = AppData.scalesList.find{ it.scaleName == parent?.getItemAtPosition(position)} as Scale
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    companion object {
        var startScale : Scale = AppData.scalesList[0]
    }
}