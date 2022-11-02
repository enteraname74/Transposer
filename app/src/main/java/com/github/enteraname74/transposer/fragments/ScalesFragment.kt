package com.github.enteraname74.transposer.fragments

import android.app.Activity
import android.app.appsearch.AppSearchResult.RESULT_OK
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.activities.SeeScaleActivity
import com.github.enteraname74.transposer.adapters.ScalesList
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Scale
import java.time.Duration

class ScalesFragment : Fragment(), ScalesList.OnScaleListener {
    private lateinit var recyclerView : RecyclerView
    private var selectedScale : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scales, container, false)

        recyclerView = view.findViewById(R.id.scales_recycler_view)
        recyclerView.adapter = ScalesList(context as Context, this)

        return view
    }

    override fun onScaleClick(position: Int) {
        Log.d("INFOS :", position.toString())
        val intent = Intent(context, SeeScaleActivity::class.java)
        intent.putExtra("POSITION", position)
        startActivity(intent)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            0 -> {
                // SEND TO A CONTACT :
                val getContactIntent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                selectedScale = item.groupId
                resultLauncher.launch(getContactIntent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val uri = result.data?.data
            val cursor = context?.contentResolver?.query(uri as Uri, null, null, null, null)

            if (cursor?.moveToNext() as Boolean){
                val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val num = cursor.getString(phoneIndex)

                try {
                    val smsManager = SmsManager.getDefault()

                    var partitionText = ""
                    for (note in AppData.scalesList[selectedScale].scaleList){
                        partitionText += "$note "
                    }

                    smsManager?.sendTextMessage(num,null,
                        AppData.scalesList[selectedScale].scaleName +  " : \n" + partitionText,null,null)
                    Toast.makeText(context, R.string.the_message_has_been_sent,Toast.LENGTH_SHORT).show()
                } catch (ex : Exception) {
                    Toast.makeText(context, R.string.the_message_cannot_be_sent,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}