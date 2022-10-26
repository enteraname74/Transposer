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
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.MusicInstrument
import com.github.enteraname74.transposer.classes.Scale

class StartInstrumentFragment : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start_instrument, container, false)
        val instrumentSpinner = view.findViewById<Spinner>(R.id.instrument_spinner)
        val instrumentAdapter = ArrayAdapter(context as Context, android.R.layout.simple_spinner_item, AppData.instruments.map{ it.instrumentName })

        instrumentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        instrumentSpinner.adapter = instrumentAdapter
        instrumentSpinner.onItemSelectedListener = this

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        startInstrument = AppData.instruments.find { it.instrumentName == parent?.getItemAtPosition(position) } as MusicInstrument
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    companion object {
        var startInstrument : MusicInstrument = AppData.instruments[0]
    }
}
