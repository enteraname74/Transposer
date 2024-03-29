package com.github.enteraname74.transposer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.github.enteraname74.transposer.R
import com.github.enteraname74.transposer.activities.TranspositionActivity
import com.github.enteraname74.transposer.classes.AppData
import com.github.enteraname74.transposer.classes.MusicInstrument

/*
Fragment représentant l'étape de l'instrument initial à indiquer dans l'activité de création de transposition.
Le fragment hérité de Fragment() et implémente le listener AdapterView.OnItemSelectedListener pour le spinner.
 */
class StartInstrumentFragment : Fragment(), AdapterView.OnItemSelectedListener {
    /*
    On veut pouvoir passer automatiquement au fragment suivante quand on séléctionne un élément de notre spinner.
    Or, lors de la création de notre fragment, on passe obligatoirement dans le listener de séléction,
    ce qui va alors nous faire passer au fragment suivant.

    On veut éviter ce cas la. On initialise alors un booléen qui empêchera de passer au fragment suivant
    lors de la création du fragment courant.

    Le booleen passera à false quand l'utilisateur lui même séléctionnera un élément.
    */
    private var createFragmentState = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Fonction permettant d'initialiser et de gérer tout ce qui touche à la vue :
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start_instrument, container, false)
        val instrumentSpinner = view.findViewById<Spinner>(R.id.instrument_spinner)
        val instrumentAdapter = ArrayAdapter(
            context as Context,
            android.R.layout.simple_spinner_item,
            AppData.instruments.map { it.instrumentName })

        instrumentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        instrumentSpinner.adapter = instrumentAdapter
        instrumentSpinner.onItemSelectedListener = this

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
        (activity as TranspositionActivity).currentFragmentPos = 1
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        startInstrument =
            AppData.instruments.find { it.instrumentName == parent?.getItemAtPosition(position) } as MusicInstrument
        // onItemSelected est appelé lorsqu'on crée le fragment. Il ne faut pas passer au prochain fragment dans ce cas la.
        if (!createFragmentState) {
            (activity as TranspositionActivity).goToNextStep()
        }
        createFragmentState = false
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    /*
    Les données du fragment sont stockées dans un companion object pour y avoir accès depuis l'activité
    parente pour créer notre transposition
    */
    companion object {
        var startInstrument: MusicInstrument = AppData.instruments[0]
    }
}
