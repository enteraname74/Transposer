package com.github.enteraname74.transposer.classes

import java.io.Serializable

class Transposition(
    var transpositionName: String,
    val startPartition: ArrayList<String>,
    val startInstrument: MusicInstrument,
    val endPartition: ArrayList<String>,
    val endInstrument: MusicInstrument,
    var isFavourite: Boolean = false
) : Serializable {
}