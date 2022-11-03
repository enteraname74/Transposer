package com.github.enteraname74.transposer.fragments

import android.app.Activity
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
import com.github.enteraname74.transposer.activities.SeeTranspositionActivity
import com.github.enteraname74.transposer.adapters.TranspositionsList
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Scale
import com.github.enteraname74.transposer.classes.Transposition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream

class TranspositionsFragment : Fragment(), TranspositionsList.OnTranspositionListener {
    private lateinit var transpositionRecyclerView: RecyclerView
    private lateinit var selectedTransposition : Transposition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transpositions, container, false)

        transpositionRecyclerView = view.findViewById(R.id.transpositions_recycler_view)
        transpositionRecyclerView.adapter = TranspositionsList(context as Context,AppData.allTranspositions, this)

        return view
    }

    override fun onResume() {
        super.onResume()
        transpositionRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onTranspositionClick(position: Int) {
        val intent = Intent(context, SeeTranspositionActivity::class.java)
        intent.putExtra("POSITION", position)
        intent.putExtra("SOURCE", "Transpositions")
        startActivity(intent)
    }

    // L'id du champ selectionné doit être différent de tous les autres champs disponibles dans les autres fragments pour éviter d'appeler le onContextItemSelected d'autres fragments :
    override fun onContextItemSelected(item: MenuItem): Boolean {
        println(item.itemId.toString())
        val globalIndex = AppData.allTranspositions.indexOf(AppData.allTranspositions[item.groupId])
        val element = AppData.allTranspositions[globalIndex]
        return when (item.itemId) {
            10 -> {
                // DELETE TRANSPOSITION
                AppData.allTranspositions.remove(element)
                AppData.favouritesList.remove(element)


                transpositionRecyclerView.adapter?.notifyItemRemoved(item.groupId)
                CoroutineScope(Dispatchers.IO).launch { AppData.writeAllTranspositions(context?.applicationContext?.filesDir as File) }
                Toast.makeText(context, R.string.transposition_has_been_deleted, Toast.LENGTH_SHORT).show()
                true
            }
            11 -> {
                // CHANGE FAVOURITE STATUE OF TRANSPOSITION
                if (AppData.allTranspositions[item.groupId].isFavourite) {
                    AppData.allTranspositions[item.groupId].isFavourite = false
                    AppData.favouritesList.remove(element)
                    Toast.makeText(context, R.string.transposition_removed_from_favourite, Toast.LENGTH_SHORT).show()
                } else {
                    AppData.allTranspositions[item.groupId].isFavourite = true
                    AppData.favouritesList.add(element)
                    Toast.makeText(context, R.string.transposition_added_to_favourite, Toast.LENGTH_SHORT).show()
                }
                CoroutineScope(Dispatchers.IO).launch { AppData.writeAllTranspositions(context?.applicationContext?.filesDir as File) }
                true
            }
            12 -> {
                // SEND TO A CONTACT :
                Log.d("SEND TRANSPO","")
                val getContactIntent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                selectedTransposition = element
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
                Log.d("RESULT", num.toString())

                try {
                    val smsManager = SmsManager.getDefault()

                    var initialPartitionValue = ""
                    for (note in selectedTransposition.startPartition){
                        initialPartitionValue += "$note "
                    }

                    val initialInstrumentText = getString(R.string.initial_instrument) + "\n" + selectedTransposition.startInstrument.instrumentName + "\n\n"
                    val initialPartitionText = getString(R.string.initial_partition)+ "\n" + initialPartitionValue + "\n\n"

                    var endPartitionValue = ""
                    for (note in selectedTransposition.endPartition){
                        endPartitionValue += "$note "
                    }

                    val endInstrumentText = getString(R.string.final_instrument) + "\n" + selectedTransposition.endInstrument.instrumentName + "\n\n"
                    val endPartitionText = getString(R.string.final_partition)+ "\n" + endPartitionValue

                    // On divise le message au cas où celui-ci serait trop long.
                    val parts: ArrayList<String> =
                        smsManager.divideMessage(initialInstrumentText + initialPartitionText + endInstrumentText + endPartitionText)

                    smsManager.sendMultipartTextMessage(num, null, parts, null, null)

                    Toast.makeText(context, R.string.the_message_has_been_sent,Toast.LENGTH_SHORT).show()
                } catch (ex : Exception) {
                    Toast.makeText(context, R.string.the_message_cannot_be_sent,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}