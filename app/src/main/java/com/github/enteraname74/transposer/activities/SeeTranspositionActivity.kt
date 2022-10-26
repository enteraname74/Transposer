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
        val valuesField = findViewById<TextView>(R.id.values)

        // En foncti
        val currentList = if(source == "Favourites"){
            AppData.favouritesList
        } else {
            AppData.allTranspositions
        }

        transpositionName.text = currentList[position].transpositionName
        valuesField.text = currentList[position].endPartition.toString()

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener { finish() }
    }
}