package com.github.enteraname74.transposer.classes

import java.io.Serializable

/*
Classe représentant une transposition.
Une transpostion est représentée par un nom,
une partition de départ (liste de notes),
un instrument de départ,
une partition finale (liste de notes),
un instrument final,
un booleen permettant de savoir si la transposition est dans nos favoris.
La classe implémente Serializable ce qui lui permet d'être enregistrée dans un fichier.
 */
class Transposition(
    var transpositionName: String,
    val startPartition: ArrayList<String>,
    val startInstrument: MusicInstrument,
    val endPartition: ArrayList<String>,
    val endInstrument: MusicInstrument,
    var isFavourite: Boolean = false
) : Serializable {
}