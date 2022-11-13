package com.github.enteraname74.transposer.classes

import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream

/*
Classe utilisée pour stocker toutes les constantes et les données de l'applications utilisées
dans nos activités.

Tout est stocké dans un companion object pour un accès simple.
 */
class AppData {
    companion object {
        // Clés pour les shared preferences :
        const val SHARED_PREF_KEY = "SHARED_PREF"
        const val USERNAME_KEY = "USERNAME"
        const val PROFILE_PICTURE_KEY = "PROFILE PICTURE"

        // nom du fichier où sont sauvegardé les transpositions de l'utilisateur :
        val allTranspositionFile = "allTranspositions.transpose"

        // Toutes les notes au format diese :
        val allNotesDiese = ArrayList<String>(
            listOf(
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
            )
        )

        // Toutes les notes au format bémol :
        val allNotesBemol = ArrayList<String>(
            listOf(
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
            )
        )

        // Liste d'instruments :
        val instruments = ArrayList<MusicInstrument>(
            listOf(
                MusicInstrument("Alto Saxophone", 3),
                MusicInstrument("Guitar", 4),
                MusicInstrument("Piano", 0),
                MusicInstrument("Tenor Saxophone", 10),
                MusicInstrument("Trumpet", 10)
            )
        )

        // Liste de toutes les gammes :
        val scalesList = ArrayList<Scale>(
            listOf(
                Scale(
                    "DO Majeur",
                    ArrayList(listOf("DO", "RE", "MI", "FA", "SOL", "LA", "SI", "DO"))
                ),
                Scale(
                    "LA Mineur",
                    ArrayList(listOf("DO", "RE", "MI", "FA", "SOL", "LA", "SI", "DO"))
                ),
                Scale(
                    "FA Majeur",
                    ArrayList(listOf("DO", "RE", "MI", "FA", "SOL", "LA", "SI♭", "DO"))
                ),
                Scale(
                    "RE Mineur",
                    ArrayList(listOf("DO", "RE", "MI", "FA", "SOL", "LA", "SI♭", "DO"))
                ),
                Scale(
                    "SI♭ Majeur",
                    ArrayList(listOf("DO", "RE", "MI♭", "FA", "SOL", "LA", "SI♭", "DO"))
                ),
                Scale(
                    "SOL Mineur",
                    ArrayList(listOf("DO", "RE", "MI♭", "FA", "SOL", "LA", "SI", "DO"))
                ),
                Scale(
                    "MI♭ Majeur",
                    ArrayList(listOf("DO", "RE", "MI♭", "FA", "SOL", "LA♭", "SI♭", "DO"))
                ),
                Scale(
                    "DO Mineur",
                    ArrayList(listOf("DO", "RE", "MI♭", "FA", "SOL", "LA♭", "SI♭", "DO"))
                ),
                Scale(
                    "LA♭ Majeur",
                    ArrayList(listOf("DO", "RE♭", "MI♭", "FA", "SOL", "LA♭", "SI♭", "DO"))
                ),
                Scale(
                    "FA Mineur",
                    ArrayList(listOf("DO", "RE♭", "MI♭", "FA", "SOL", "LA♭", "SI♭", "DO"))
                ),
                Scale(
                    "RE♭ Majeur",
                    ArrayList(listOf("DO", "RE♭", "MI♭", "FA", "SOL♭", "LA♭", "SI♭", "DO"))
                ),
                Scale(
                    "SI♭ Mineur",
                    ArrayList(listOf("DO", "RE♭", "MI♭", "FA", "SOL♭", "LA♭", "SI♭", "DO"))
                ),
                Scale(
                    "FA# Majeur",
                    ArrayList(listOf("DO#", "RE#", "MI#", "FA#", "SOL#", "LA#", "SI", "DO#"))
                ),
                Scale(
                    "RE# Mineur",
                    ArrayList(listOf("DO#", "RE#", "MI#", "FA#", "SOL#", "LA#", "SI", "DO#"))
                ),
                Scale(
                    "SI Majeur",
                    ArrayList(listOf("DO#", "RE#", "MI", "FA#", "SOL#", "LA#", "SI", "DO#"))
                ),
                Scale(
                    "SOL# Mineur",
                    ArrayList(listOf("DO#", "RE#", "MI", "FA#", "SOL#", "LA#", "SI", "DO#"))
                ),
                Scale(
                    "MI Majeur",
                    ArrayList(listOf("DO#", "RE#", "MI", "FA#", "SOL#", "LA", "SI", "DO#"))
                ),
                Scale(
                    "DO# Mineur",
                    ArrayList(listOf("DO#", "RE#", "MI", "FA#", "SOL#", "LA", "SI", "DO#"))
                ),
                Scale(
                    "LA Majeur",
                    ArrayList(listOf("DO#", "RE", "MI", "FA#", "SOL#", "LA", "SI", "DO#"))
                ),
                Scale(
                    "FA# Mineur",
                    ArrayList(listOf("DO#", "RE", "MI", "FA#", "SOL#", "LA", "SI", "DO#"))
                ),
                Scale(
                    "RE Majeur",
                    ArrayList(listOf("DO#", "RE", "MI", "FA#", "SOL", "LA", "SI", "DO#"))
                ),
                Scale(
                    "SI Mineur",
                    ArrayList(listOf("DO#", "RE", "MI", "FA#", "SOL", "LA", "SI", "DO#"))
                ),
                Scale(
                    "SOL Majeur",
                    ArrayList(listOf("DO", "RE", "MI", "FA#", "SOL", "LA", "SI", "DO"))
                ),
                Scale(
                    "MI Mineur",
                    ArrayList(listOf("DO", "RE", "MI", "FA#", "SOL", "LA", "SI", "DO"))
                )
            )
        )

        /*
        Liste accueillant toutes les transpositions de l'utilisateur.
        Elles sera remplis lors du lancement de l'application :
         */
        var allTranspositions = ArrayList<Transposition>()

        // Liste accueillant les transpositions favorites de l'utilisateur :
        var favouritesList = ArrayList<Transposition>()

        // Liste accueillant les transpositions du cloud :
        var cloudTransposition = ArrayList<Transposition>()

        /*
        Procédure permettant d'écrire toutes nos partitions dans le fichier où elles sont entreposées.
        Il faudra passer le chemin jusqu'au fichier :
         */
        fun writeAllTranspositions(path: File) {
            try {
                val oos = ObjectOutputStream(FileOutputStream(File(path, allTranspositionFile)))
                oos.writeObject(allTranspositions)
                oos.close()
            } catch (error: IOException) {
                Log.d("Error write", error.toString())
            }
        }

    }
}