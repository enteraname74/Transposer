package com.github.enteraname74.transposer.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.adapters.TranspositionsList
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Transposition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type


class CloudActivity : AppCompatActivity(), TranspositionsList.OnTranspositionListener {
    private var selectedTransposition: Transposition? = null
    private var spinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud)

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.scales_recycler_view)

        spinner = findViewById(R.id.progressBarCloud)

        val url = "https://transposer.skilldary.com/transposer/get_transpositions"
        val request = JsonObjectRequest(Request.Method.GET, url, null, { res ->
            val gson = Gson()
            val type: Type = object : TypeToken<ArrayList<Transposition?>?>() {}.type

            val data: JSONArray = res.getJSONArray("data")

            // On remplace startInstrument et endInstrument par l'objet correspondant.
            for (i in 0 until data.length()) {
                val item: JSONObject = data.get(i) as JSONObject
                item.put(
                    "startInstrument", JSONObject(
                        "{\"instrumentName\": \"${item.get("startInstrument")}\", \"tone\": ${
                            AppData.instruments.find {
                                it.instrumentName == item.get(
                                    "startInstrument"
                                )
                            }?.tone
                        }}"
                    )
                )

                item.put(
                    "endInstrument", JSONObject(
                        "{\"instrumentName\": \"${item.get("endInstrument")}\", \"tone\": ${
                            AppData.instruments.find {
                                it.instrumentName == item.get(
                                    "endInstrument"
                                )
                            }?.tone
                        }}"
                    )
                )
            }

            val transpositions: ArrayList<Transposition> =
                gson.fromJson(res["data"].toString(), type)

            AppData.cloudTransposition = transpositions

            // On enlève l'animation de chargement.
            spinner?.visibility = View.GONE

            recyclerView.adapter = TranspositionsList(this, transpositions, this, "Cloud")
        }, { _ ->
            // On enlève l'animation de chargement.
            spinner?.visibility = View.GONE
            Toast.makeText(
                applicationContext, resources.getString(R.string.error), Toast.LENGTH_SHORT
            ).show()
        })

        // On lance la requête.
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
        queue.start()
    }

    // Ouvre la transposition lorsque celle-ci est cliqué.
    override fun onTranspositionClick(position: Int) {
        val intent = Intent(applicationContext, SeeTranspositionActivity::class.java)
        intent.putExtra("POSITION", position)
        intent.putExtra("SOURCE", "Cloud")
        startActivity(intent)
    }

    // L'id du champ selectionné doit être différent de tous les autres champs disponibles dans les autres fragments pour éviter d'appeler le onContextItemSelected d'autres fragments :
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val globalIndex =
            AppData.cloudTransposition.indexOf(AppData.cloudTransposition[item.groupId])
        val element = AppData.cloudTransposition[globalIndex]
        return when (item.itemId) {
            12 -> {
                // SEND TO A CONTACT :
                val getContactIntent =
                    Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                selectedTransposition = element
                resultLauncher.launch(getContactIntent)
                true
            }
            14 -> {
                // SAVE LOCALLY :
                val listTranspositionNames: ArrayList<String> = ArrayList<String>()
                var i: Int = 0
                var alreadyExist: Boolean = false

                // On parcours toutes les transpositions locales, afin de voir si une transposition
                // de même nom que celle que l'on souhaite enregistrer, existe déjà.
                // Au cas où ce serait le cas, on mets de côté le nom des transpositions, pour
                // pouvoir l'enregistrer au format "nom (n)", n étant le nombre de transpositions
                // partageant le même nom.
                while (i < AppData.allTranspositions.size) {
                    val elmt = AppData.allTranspositions[i]

                    if (elmt.transpositionName == element.transpositionName) {
                        alreadyExist = true
                    }

                    listTranspositionNames.add(elmt.transpositionName)

                    i++
                }

                // Dans le cas où la transpositions existerait déjà, on l'enregistre au format
                // "nom (n)", n représentant le nombre de transpositions partageant le même nom.
                if (alreadyExist) {
                    var n: Int = 1
                    while (listTranspositionNames.contains("${element.transpositionName} (" + n + ")")) {
                        n++
                    }

                    element.transpositionName = "${element.transpositionName} (" + n + ")"
                }

                AppData.allTranspositions.add(element)

                AppData.writeAllTranspositions(applicationContext.filesDir)

                // On notifie l'utilisateur.
                Toast.makeText(
                    applicationContext,
                    R.string.saved_locally,
                    Toast.LENGTH_SHORT
                ).show()

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                val cursor =
                    applicationContext?.contentResolver?.query(uri as Uri, null, null, null, null)

                if (cursor?.moveToNext() as Boolean) {
                    val phoneIndex =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val num = cursor.getString(phoneIndex)

                    try {
                        val smsManager = SmsManager.getDefault()

                        var initialPartitionValue = ""
                        for (note in selectedTransposition!!.startPartition) {
                            initialPartitionValue += "$note "
                        }

                        val initialInstrumentText =
                            getString(R.string.initial_instrument) + "\n" + selectedTransposition!!.startInstrument.instrumentName + "\n\n"
                        val initialPartitionText =
                            getString(R.string.initial_partition) + "\n" + initialPartitionValue + "\n\n"

                        var endPartitionValue = ""
                        for (note in selectedTransposition!!.endPartition) {
                            endPartitionValue += "$note "
                        }

                        val endInstrumentText =
                            getString(R.string.final_instrument) + "\n" + selectedTransposition!!.endInstrument.instrumentName + "\n\n"
                        val endPartitionText =
                            getString(R.string.final_partition) + "\n" + endPartitionValue

                        // On divise le message au cas où celui-ci serait trop long.
                        val parts: ArrayList<String> =
                            smsManager.divideMessage(initialInstrumentText + initialPartitionText + endInstrumentText + endPartitionText)

                        smsManager.sendMultipartTextMessage(num, null, parts, null, null)

                        Toast.makeText(
                            applicationContext,
                            R.string.the_message_has_been_sent,
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (ex: Exception) {
                        Toast.makeText(
                            applicationContext,
                            R.string.the_message_cannot_be_sent,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
}
