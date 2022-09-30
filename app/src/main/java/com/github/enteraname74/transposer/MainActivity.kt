package com.github.enteraname74.transposer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.cloud_button).setOnClickListener { toCloudActivity() }

    }

    private fun toCloudActivity(){
        val intent = Intent(this,CloudActivity::class.java)
        startActivity(intent)
    }
}