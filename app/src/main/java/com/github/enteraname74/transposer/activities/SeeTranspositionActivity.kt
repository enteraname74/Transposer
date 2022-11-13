package com.github.enteraname74.transposer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.AppData

/*
Activité permettant de visualiser une transposition.
Vu que c'est une activité, elle hérite d'AppCompatActivity.
 */
class SeeTranspositionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_transposition)

        // Récupérons la position de la transposition :
        val position = intent.getSerializableExtra("POSITION") as Int
        /*
         Récupérons la source (dans quelle liste on a séléctionnée la partition) afin de savoir
         dans quelle liste aller chercher notre transposition avec notre position.
         */
        val source = intent.getSerializableExtra("SOURCE") as String

        val transpositionName = findViewById<TextView>(R.id.transposition_name)

        val startPartitionField = findViewById<TextView>(R.id.start_partition)
        val startInstrumentField = findViewById<TextView>(R.id.start_instrument)
        val endPartitionField = findViewById<TextView>(R.id.end_partition)
        val endInstrumentField = findViewById<TextView>(R.id.end_instrument)

        // En fonction de la liste actuelle, on définit où on va chercher notre partition :
        val currentList = when (source) {
            "Favourites" -> {
                AppData.favouritesList
            }
            "Cloud" -> {
                AppData.cloudTransposition
            }
            else -> {
                AppData.allTranspositions
            }
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