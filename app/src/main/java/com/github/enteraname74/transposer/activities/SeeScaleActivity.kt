package com.github.enteraname74.transposer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.AppData
import org.w3c.dom.Text

class SeeScaleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_scale)

        val position = intent.getSerializableExtra("POSITION") as Int

        val scaleName = findViewById<TextView>(R.id.scale_name)
        val valuesField = findViewById<TextView>(R.id.scale_values)

        var partitionText = ""
        for (note in AppData.scalesList[position].scaleList){
            partitionText += " $note "
        }

        scaleName.text = AppData.scalesList[position].scaleName
        valuesField.text = partitionText

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener { finish() }
    }
}