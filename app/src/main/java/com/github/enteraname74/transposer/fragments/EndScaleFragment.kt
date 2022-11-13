package com.github.enteraname74.transposer.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.activities.TranspositionActivity
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.Scale

/*
Fragment représentant l'étape de la gamme de fin à indiquer dans l'activité de création de transposition.
Le fragment hérité de Fragment().
 */
class EndScaleFragment : Fragment() {
    private lateinit var endScaleTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Fonction permettant d'initialiser et de gérer tout ce qui touche à la vue :
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

    /*
    Il faut mettre à jour certaines données quand on revient sur un fragment :7
    On met alors à jour la position courante du fragment actuel dans sa liste.
    Pour ce faire, on change la valeur de currentFragmentPos, une variable de l'activité parente du fragment.
     */
    override fun onResume() {
        super.onResume()
        // Le changement de position courante se fait quand on change de fragment (à la main, ou en utilisant les boutons)
        (activity as TranspositionActivity).currentFragmentPos = 3

        // Dès que l'on revient sur ce fragment, on met à jour la vue :
        endScale = (activity as TranspositionActivity).createEndScale()

        var partitionText = ""
        for (note in endScale.scaleList){
            partitionText += " $note "
        }

        endScaleTextView.text = partitionText
    }

    /*
    Les données du fragment sont stockées dans un companion object pour y avoir accès depuis l'activité
    parente pour créer notre transposition
    */
    companion object {
        var endScale : Scale = AppData.scalesList[0]
    }
}