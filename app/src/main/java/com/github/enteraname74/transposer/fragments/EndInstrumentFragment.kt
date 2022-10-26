package com.github.enteraname74.transposer.fragments

import android.content.Context
import android.os.Bundle
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


class EndInstrumentFragment : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_end_instrument, container, false)

        val endInstrumentSpinner = view.findViewById<Spinner>(R.id.end_instrument_spinner)
        val endInstrumentAdapter = ArrayAdapter(context as Context, android.R.layout.simple_spinner_item, AppData.instruments.map{ it.instrumentName })

        endInstrumentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        endInstrumentSpinner.adapter = endInstrumentAdapter
        endInstrumentSpinner.onItemSelectedListener = this

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        endInstrument = AppData.instruments.find { it.instrumentName == parent?.getItemAtPosition(position) } as MusicInstrument
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    companion object {
        var endInstrument : MusicInstrument = AppData.instruments[0]
    }
}