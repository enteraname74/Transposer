package com.github.enteraname74.transposer.classes

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import kotlin.collections.ArrayList

class AppData {
    companion object {
        val allTranspositionFile = "allTranspositions.transpose"

        val allNotesDiese = ArrayList<String>(listOf(
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

        val allNotesBemol = ArrayList<String>(listOf(
            "DO♭",
            "DO",
            "RE♭",
            "RE",
            "MI♭",
            "MI",
            "FA",
            "SOL♭",
            "SOL",
            "LA♭",
            "LA",
            "SI♭",
            "SI"
        ))

        val instruments = ArrayList<MusicInstrument>(listOf(
            MusicInstrument("Piano", 0),
            MusicInstrument("Saxophone Alto", 3),
            MusicInstrument("Guitare Acoustique", 4),
            MusicInstrument("Saxophone Tenor", 10)
        ))

        val scalesList = ArrayList<Scale>(listOf(
            Scale("DO Majeur", ArrayList(listOf("DO","RE","MI","FA","SOL","LA","SI","DO"))),
            Scale("LA Mineur", ArrayList(listOf("DO","RE","MI","FA","SOL","LA","SI","DO"))),
            Scale("FA Majeur", ArrayList(listOf("DO","RE","MI","FA","SOL","LA","SI♭","DO"))),
            Scale("RE Mineur", ArrayList(listOf("DO","RE","MI","FA","SOL","LA","SI♭","DO"))),
            Scale("SI♭ Majeur", ArrayList(listOf("DO","RE","MI♭","FA","SOL","LA","SI♭","DO"))),
            Scale("SOL Mineur", ArrayList(listOf("DO","RE","MI♭","FA","SOL","LA","SI","DO"))),
            Scale("MI♭ Majeur", ArrayList(listOf("DO","RE","MI♭","FA","SOL","LA♭","SI♭","DO"))),
            Scale("DO Mineur", ArrayList(listOf("DO","RE","MI♭","FA","SOL","LA♭","SI♭","DO"))),
            Scale("LA♭ Majeur", ArrayList(listOf("DO","RE♭","MI♭","FA","SOL","LA♭","SI♭","DO"))),
            Scale("FA Mineur", ArrayList(listOf("DO","RE♭","MI♭","FA","SOL","LA♭","SI♭","DO"))),
            Scale("RE♭ Majeur", ArrayList(listOf("DO","RE♭","MI♭","FA","SOL♭","LA♭","SI♭","DO"))),
            Scale("SI♭ Mineur", ArrayList(listOf("DO","RE♭","MI♭","FA","SOL♭","LA♭","SI♭","DO"))),
            Scale("FA# Majeur", ArrayList(listOf("DO#","RE#","MI#","FA#","SOL#","LA#","SI","DO#"))),
            Scale("RE# Mineur", ArrayList(listOf("DO#","RE#","MI#","FA#","SOL#","LA#","SI","DO#"))),
            Scale("SI Majeur", ArrayList(listOf("DO#","RE#","MI","FA#","SOL#","LA#","SI","DO#"))),
            Scale("SOL# Mineur", ArrayList(listOf("DO#","RE#","MI","FA#","SOL#","LA#","SI","DO#"))),
            Scale("MI Majeur", ArrayList(listOf("DO#","RE#","MI","FA#","SOL#","LA","SI","DO#"))),
            Scale("DO# Mineur", ArrayList(listOf("DO#","RE#","MI","FA#","SOL#","LA","SI","DO#"))),
            Scale("LA Majeur", ArrayList(listOf("DO#","RE","MI","FA#","SOL#","LA","SI","DO#"))),
            Scale("FA# Mineur", ArrayList(listOf("DO#","RE","MI","FA#","SOL#","LA","SI","DO#"))),
            Scale("RE Majeur", ArrayList(listOf("DO#","RE","MI","FA#","SOL","LA","SI","DO#"))),
            Scale("SI Mineur", ArrayList(listOf("DO#","RE","MI","FA#","SOL","LA","SI","DO#"))),
            Scale("SOL Majeur", ArrayList(listOf("DO","RE","MI","FA#","SOL","LA","SI","DO"))),
            Scale("MI Mineur", ArrayList(listOf("DO","RE","MI","FA#","SOL","LA","SI","DO"))))
        )

        var allTranspositions = ArrayList<Transposition>()

        var favouritesList = ArrayList<Transposition>()

        var cloudTransposition = ArrayList<Transposition>()

        fun writeAllTranspositions(path : File){
            try {
                val oos = ObjectOutputStream(FileOutputStream(File(path, allTranspositionFile)))
                oos.writeObject(allTranspositions)
                oos.close()
            } catch (error : IOException){
                Log.d("Error write",error.toString())
            }
        }

    }
}