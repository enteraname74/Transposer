package com.github.enteraname74.transposer.activities

import android.app.AlertDialog
import android.content.res.Resources
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.adapters.CreateTranspositionVpAdapter
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Scale
import com.github.enteraname74.transposer.classes.Transposition
import com.github.enteraname74.transposer.fragments.EndInstrumentFragment
import com.github.enteraname74.transposer.fragments.EndScaleFragment
import com.github.enteraname74.transposer.fragments.StartInstrumentFragment
import com.github.enteraname74.transposer.fragments.StartScaleFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream

/*
Activité permettant de creer une transposition.
Vu que c'est une activité, elle hérite d'AppCompatActivity.
 */
class TranspositionActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout

    // La liste des fragments représentant chaque étape de la création d'une transposition :
    private val fragmentList = ArrayList<Fragment>(
        arrayListOf(
            StartScaleFragment(),
            StartInstrumentFragment(),
            EndInstrumentFragment(),
            EndScaleFragment()
        )
    )

    // Le premier fragment, affiché lorsqu'on lance cette activité, est à la position 0 :
    var currentFragmentPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transposition)

        // Mettons en place notre système de tab et de fragments :
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager.adapter = CreateTranspositionVpAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, index ->
            tab.text = when (index) {
                0 -> {
                    resources.getString(R.string.start_scale)
                }
                1 -> {
                    resources.getString(R.string.start_instrument)
                }
                2 -> {
                    resources.getString(R.string.end_instrument)
                }
                3 -> {
                    resources.getString(R.string.end_scale)
                }
                else -> {
                    throw Resources.NotFoundException("Position not found")
                }
            }
        }.attach()

        val addTranspositionButton = findViewById<Button>(R.id.save_transposition_button)
        addTranspositionButton.setOnClickListener { addTransposition() }

        val previousStep = findViewById<ImageView>(R.id.previous_step)
        previousStep.setOnClickListener { goToPreviousStep() }

        val nextStep = findViewById<ImageView>(R.id.next_step)
        nextStep.setOnClickListener { goToNextStep() }

        val exitButton = findViewById<ImageView>(R.id.back_button)
        exitButton.setOnClickListener { finish() }

        // On pense à bien initialiser les valeurs de nos fragments :
        StartScaleFragment.startScale = AppData.scalesList[0]
        StartInstrumentFragment.startInstrument = AppData.instruments[0]
        EndInstrumentFragment.endInstrument = AppData.instruments[0]
    }

    // Fonction permet de créer une gamme de fin avec toutes les informations de chaque fragment :
    fun createEndScale(): Scale {
        // le décalage de notes :
        val toneVariation =
            EndInstrumentFragment.endInstrument.tone - StartInstrumentFragment.startInstrument.tone

        val newScale = Scale(StartScaleFragment.startScale.scaleName, ArrayList<String>())

        // Il faut savoir quelle liste on va utiliser pour décaler les notes (gamme en bémol ou en dièse) :
        // Si la gamme d départ se situe dans les 11 premières de la liste global, c'est une gamme en bémol :
        val noteslist = if (AppData.scalesList.indexOf(StartScaleFragment.startScale) < 12) {
            AppData.allNotesBemol
        } else {
            AppData.allNotesDiese
        }

        // on va chercher ensuite, pour chaque note initial, sa note d'arrivée :
        for (note in StartScaleFragment.startScale.scaleList) {

            val initialIndex = noteslist.indexOf(note)
            val endIndex = Math.floorMod((initialIndex + toneVariation), noteslist.size)

            newScale.scaleList.add(noteslist[endIndex])
        }

        return newScale
    }

    /*
     Procédure permettant d'enregistre notre transposition.
     Elle ouvre une fenêtre de dialog pour que l'utilisateur indique le titre de la transposition qu'il vient de créer :
     */
    private fun addTransposition() {

        val builder = AlertDialog.Builder(this@TranspositionActivity)
        builder.setTitle(getString(R.string.specify_the_name_of_the_new_transposition))

        // On créer notre transposition finale :
        val endScale = createEndScale()

        // L'entrée :
        val inputText = EditText(this@TranspositionActivity)
        // Le type d'entrée :
        inputText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(inputText)

        builder.setPositiveButton(R.string.save) { _, _ ->
            if (inputText.text.toString().trim() != "" && AppData.allTranspositions.find {
                    it.transpositionName == inputText.text.toString().trim()
                } == null) {
                val newTransposition = Transposition(
                    inputText.text.toString(),
                    StartScaleFragment.startScale.scaleList,
                    StartInstrumentFragment.startInstrument,
                    endScale.scaleList,
                    EndInstrumentFragment.endInstrument
                )

                AppData.allTranspositions.add(newTransposition)
                CoroutineScope(Dispatchers.IO).launch { writeAllTranspositions() }
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    R.string.a_title_must_be_set_correctly,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        builder.setNegativeButton(R.string.cancel) { dialogInterface, _ ->
            dialogInterface.cancel()
        }

        builder.show()
    }

    // Procédure permettant d'enregistrer toute nos partitions dans le fichier correspondant :
    private fun writeAllTranspositions() {
        val path = applicationContext.filesDir
        try {
            val oos = ObjectOutputStream(FileOutputStream(File(path, AppData.allTranspositionFile)))
            oos.writeObject(AppData.allTranspositions)
            oos.close()
        } catch (error: IOException) {
            Log.d("Error write", error.toString())
        }
    }

    // Procédure permettant de changer manuellement d'étape (de fragment donc) en allant à la précédante :
    private fun goToPreviousStep() {
        if (currentFragmentPos != 0) {
            tabLayout.selectTab(tabLayout.getTabAt(currentFragmentPos - 1))
        }
    }

    // Procédure permettant de changer manuellement d'étape (de fragment donc) en allant à la suivante :
    fun goToNextStep() {
        if (currentFragmentPos != (fragmentList.size - 1)) {
            tabLayout.selectTab(tabLayout.getTabAt(currentFragmentPos + 1))
        }
    }
}
