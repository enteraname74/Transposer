package com.github.enteraname74.transposer.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.github.enteraname74.transposer.R

class CloudActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud)

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.scales_recycler_view)
    }
}
