package com.github.enteraname74.transposer.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.classes.AppData
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class SettingsActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var profilePicture: ShapeableImageView
    private var bitmapImage: Bitmap? = null
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        usernameEditText = findViewById(R.id.username_edit_text)
        profilePicture = findViewById(R.id.profile_picture)
        val backButton = findViewById<ImageView>(R.id.back_button)
        val saveButton = findViewById<Button>(R.id.save_button)

        backButton.setOnClickListener { finish() }
        saveButton.setOnClickListener { saveChangesAndQuit() }
        profilePicture.setOnClickListener { selectImage() }

        sharedPref = getSharedPreferences(AppData.SHARED_PREF_KEY, Context.MODE_PRIVATE)
        Log.d("SIZE THERE", sharedPref.all.size.toString())

        if (sharedPref.contains(AppData.USERNAME_KEY)) {
            usernameEditText.setText(sharedPref.getString(AppData.USERNAME_KEY, ""))
        }

        if (sharedPref.contains(AppData.PROFILE_PICTURE_KEY)) {
            val encodedImage = sharedPref.getString(AppData.PROFILE_PICTURE_KEY, "")
            val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
            bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

            profilePicture.setImageBitmap(bitmapImage)
        }

        //sharedPref.edit().clear().apply()

    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultImageLauncher.launch(intent)
    }

    private var resultImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data

                val inputStream = applicationContext.contentResolver.openInputStream(uri as Uri)
                bitmapImage = BitmapFactory.decodeStream(inputStream)
                profilePicture.setImageBitmap(bitmapImage)
            }
        }

    private fun saveChangesAndQuit() {
        CoroutineScope(Dispatchers.IO).launch {
            with(sharedPref.edit()) {
                putString(AppData.USERNAME_KEY, usernameEditText.text.toString())

                if (bitmapImage != null) {
                    val baos = ByteArrayOutputStream()
                    bitmapImage?.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val bytes = baos.toByteArray()
                    val encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT)
                    putString(AppData.PROFILE_PICTURE_KEY, encodedImage)
                }
                apply()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@SettingsActivity,
                        getString(R.string.changes_applied_successfully),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            finish()
        }
    }
}