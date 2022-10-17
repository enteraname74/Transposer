package com.github.enteraname74.transposer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.AppData

class SeeTranspositionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_transposition)

        val position = intent.getSerializableExtra("POSITION") as Int

        val transpositionName = findViewById<TextView>(R.id.transposition_name)
        val valuesField = findViewById<TextView>(R.id.values)

        transpositionName.text = AppData.allTranspositions[position].transpositionName
        valuesField.text = AppData.allTranspositions[position].endPartition.toString()

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener { finish() }
    }
}