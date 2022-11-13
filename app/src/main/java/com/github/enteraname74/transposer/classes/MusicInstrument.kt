package com.github.enteraname74.transposer.classes

import java.io.Serializable

/*
Classe représentant un instrument.
Un instrument est définit par son nom et son ton en entier (différence entre l'instrument et le piano, instrument de référence).
La classe implémente Serializable ce qui lui permet d'être enregistrée dans un fichier.
 */
class MusicInstrument(
    val instrumentName : String,
    val tone : Int
) : Serializable