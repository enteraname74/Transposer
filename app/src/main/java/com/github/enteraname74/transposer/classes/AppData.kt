package com.github.enteraname74.transposer.classes

import kotlin.collections.ArrayList

class AppData {
    companion object {

        val allTranspositionFile = "allTranspositions.transpose"

        val allNotes = ArrayList<String>(listOf(
            "DO",
            "DO#",
            "RE",
            "RE#",
            "MI",
            "FA",
            "FA#",
            "SOL",
            "SOL#",
            "LA",
            "LA#",
            "SI"
        ))

        val instruments = ArrayList<MusicInstrument>(listOf(
            MusicInstrument("Piano", 0),
            MusicInstrument("Saxophone Alto", 3),
            MusicInstrument("Saxophone Tenor", 10)
        ))

        val scalesList = ArrayList<Scale>(listOf(
            Scale("DO Majeur", ArrayList(listOf("DO","RE","MI","FA","SOL","LA","SI","DO"))),
            Scale("La Mineur", ArrayList(listOf("DO","RE","MI","FA","SOL","LA","SI","DO"))),
            Scale("FA Majeur", ArrayList(listOf("DO","RE","MI","FA","SOL","LA#","SI","DO"))),
            Scale("RE Mineur", ArrayList(listOf("DO","RE","MI","FA","SOL","LA#","SI","DO"))),
            Scale("SIB Majeur", ArrayList(listOf("DO","RE#","MI","FA","SOL","LA#","SI","DO"))),
            Scale("SOL Mineur", ArrayList(listOf("DO","RE#","MI","FA","SOL","LA#","SI","DO"))),
            Scale("MIB Majeur", ArrayList(listOf("DO","RE#","MI","FA","SOL#","LA#","SI","DO"))),
            Scale("DO Mineur", ArrayList(listOf("DO","RE#","MI","FA","SOL#","LA#","SI","DO"))),
            Scale("LAB Majeur", ArrayList(listOf("DO#","RE#","MI","FA","SOL#","LA#","SI","DO#"))),
            Scale("FA Majeur", ArrayList(listOf("DO#","RE#","MI","FA","SOL#","LA#","SI","DO#"))))
        )

        var allTranspositions = ArrayList<Transposition>()
    }
}