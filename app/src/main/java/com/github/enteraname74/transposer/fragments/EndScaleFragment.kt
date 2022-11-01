package com.github.enteraname74.transposer.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.activities.TranspositionActivity
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Scale

class EndScaleFragment : Fragment() {
    private lateinit var endScaleTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_end_scale, container, false)

        endScaleTextView = view.findViewById(R.id.end_scale_values)
        endScaleTextView.text = endScale.scaleList.toString()

        return view
    }

    override fun onResume() {
        // Dès que l'on revient sur ce fragment, on met à jour la vue :
        super.onResume()
        // Le changement de position courante se fait quand on change de fragment (à la main, ou en utilisant les boutons)
        (activity as TranspositionActivity).currentFragmentPos = 3

        val toneVariation = EndInstrumentFragment.endInstrument.tone - StartInstrumentFragment.startInstrument.tone

        // On initialise notre gamme d'arrivée :
        endScale = Scale(
            StartScaleFragment.startScale.scaleName,
            ArrayList<String>()
        )

        // Pour chaque note de la gamme de départ, on trouve son équivalent dans la gamme d'arrivée :
        for (note in StartScaleFragment.startScale.scaleList){

            val initialIndex = AppData.allNotes.indexOf(note)
            val endIndex = Math.floorMod((initialIndex + toneVariation), AppData.allNotes.size)

            endScale.scaleList.add(AppData.allNotes[endIndex])
            endScaleTextView.text = endScale.scaleList.toString()
        }
    }

    companion object {
        var endScale : Scale = AppData.scalesList[0]
    }
}