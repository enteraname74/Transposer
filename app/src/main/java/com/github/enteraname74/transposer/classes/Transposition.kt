package com.github.enteraname74.transposer.classes

import java.io.Serializable

class Transposition(
    val transpositionName : String,
    val startPartition : ArrayList<String>,
    val startInstrument: MusicInstrument,
    val endPartition : ArrayList<String>,
    val endInstrument: MusicInstrument,
    val isFavourite : Boolean = false
) : Serializable{
}