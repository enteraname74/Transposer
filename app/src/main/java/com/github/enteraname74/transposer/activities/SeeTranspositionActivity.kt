package com.github.enteraname74.transposer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Transposition

class SeeTranspositionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_transposition)

        val position = intent.getSerializableExtra("POSITION") as Int
        val source = intent.getSerializableExtra("SOURCE") as String

        val transpositionName = findViewById<TextView>(R.id.transposition_name)

        val startPartitionField = findViewById<TextView>(R.id.start_partition)
        val startInstrumentField = findViewById<TextView>(R.id.start_instrument)
        val endPartitionField = findViewById<TextView>(R.id.end_partition)
        val endInstrumentField = findViewById<TextView>(R.id.end_instrument)

        // En fonction de la liste actuelle, on définit où on va chercher notre partition :
        val currentList = if(source == "Favourites"){
            AppData.favouritesList
        } else if (source == "Cloud"){
            AppData.cloudTransposition
        }
        else {
            AppData.allTranspositions
        }

        transpositionName.text = currentList[position].transpositionName

        var startPartitionText = ""
        for (note in currentList[position].startPartition){
            startPartitionText += " $note "
        }
        startPartitionField.text = startPartitionText
        startInstrumentField.text = currentList[position].startInstrument.instrumentName

        var endPartitionText = ""
        for (note in currentList[position].endPartition){
            endPartitionText += " $note "
        }
        endPartitionField.text = endPartitionText
        endInstrumentField.text = currentList[position].endInstrument.instrumentName

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener { finish() }
    }
}