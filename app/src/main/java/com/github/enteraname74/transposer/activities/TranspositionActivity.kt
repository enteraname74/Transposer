package com.github.enteraname74.transposer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.MusicInstrument
import com.github.enteraname74.transposer.classes.Scale
import com.github.enteraname74.transposer.classes.Transposition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream

class TranspositionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var startScale : Scale
    private lateinit var startInstrument : MusicInstrument
    private lateinit var endScale: Scale
    private lateinit var endInstrument: MusicInstrument
    private lateinit var endScaleTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transposition)

        val scaleSpinner = findViewById<Spinner>(R.id.scale_spinner)
        val instrumentSpinner = findViewById<Spinner>(R.id.instrument_spinner)
        val endInstrumentSpinner = findViewById<Spinner>(R.id.end_instrument_spinner)
        endScaleTextView = findViewById(R.id.end_scale_values)

        val scaleAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, AppData.scalesList.map{ it.scaleName })
        val instrumentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, AppData.instruments.map{ it.instrumentName })
        val endInstrumentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, AppData.instruments.map{ it.instrumentName })

        scaleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        scaleSpinner.adapter = scaleAdapter
        scaleSpinner.onItemSelectedListener = this

        instrumentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        instrumentSpinner.adapter = instrumentAdapter
        instrumentSpinner.onItemSelectedListener = this

        endInstrumentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        endInstrumentSpinner.adapter = instrumentAdapter
        endInstrumentSpinner.onItemSelectedListener = this

        startScale = AppData.scalesList[0]
        startInstrument = AppData.instruments[0]
        endInstrument = AppData.instruments[0]
        endScale = AppData.scalesList[0]

        endScaleTextView.text = endScale.scaleList.toString()

        val exitButton = findViewById<ImageView>(R.id.back_button)
        exitButton.setOnClickListener { finish() }

        val addTranspositionButton = findViewById<Button>(R.id.save_transposition_button)
        addTranspositionButton.setOnClickListener { addTransposition() }
    }

    override fun onItemSelected(parent : AdapterView<*>?, view : View?, position: Int, id : Long) {
        when(parent?.id){
            R.id.scale_spinner -> startScale = AppData.scalesList.find { it.scaleName == parent.getItemAtPosition(position) } as Scale
            R.id.instrument_spinner -> startInstrument = AppData.instruments.find { it.instrumentName == parent.getItemAtPosition(position) } as MusicInstrument
            R.id.end_instrument_spinner -> endInstrument = AppData.instruments.find { it.instrumentName == parent.getItemAtPosition(position) } as MusicInstrument
            else -> {}
        }

        // le décalage de notes :
        val toneVariation = endInstrument.tone - startInstrument.tone
        Log.d("TONEVARIATION", toneVariation.toString())

        val newScale = Scale(startScale.scaleName, ArrayList<String>())

        // on va chercher ensuite, pour chaque note initial, sa note d'arrivée :
        for (note in startScale.scaleList){

            val initialIndex = AppData.allNotes.indexOf(note)
            Log.d("Initial Index", initialIndex.toString())
            val endIndex = Math.floorMod((initialIndex + toneVariation), AppData.allNotes.size)
            Log.d("End Index", endIndex.toString())

            newScale.scaleList.add(AppData.allNotes[endIndex])
        }
        endScale = newScale
        Log.d("SAME", (startScale == endScale).toString())
        endScaleTextView.text = endScale.scaleList.toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    private fun addTransposition() {
        val newTransposition = Transposition(
            "test",
            startScale.scaleList,
            startInstrument,
            endScale.scaleList,
            endInstrument
        )

        AppData.allTranspositions.add(newTransposition)
        CoroutineScope(Dispatchers.IO).launch { writeAllTranspositions() }
        finish()
    }

    private fun writeAllTranspositions(){
        val path = applicationContext.filesDir
        try {
            val oos = ObjectOutputStream(FileOutputStream(File(path, AppData.allTranspositionFile)))
            oos.writeObject(AppData.allTranspositions)
            oos.close()
        } catch (error : IOException){
            Log.d("Error write",error.toString())
        }
    }
}