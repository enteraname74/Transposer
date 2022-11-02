package com.github.enteraname74.transposer.fragments

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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

        endScale = (activity as TranspositionActivity).createEndScale()

        var partitionText = ""
        for (note in endScale.scaleList){
            partitionText += " $note "
        }

        endScaleTextView.text = partitionText
    }

    companion object {
        var endScale : Scale = AppData.scalesList[0]
    }
}