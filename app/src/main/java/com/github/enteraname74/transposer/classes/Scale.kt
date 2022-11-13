package com.github.enteraname74.transposer.classes

/*
Classe représentant une gamme.
Une gamme est représentée par son nom et sa liste de notes.
La classe implémente Serializable ce qui lui permet d'être enregistrée dans un fichier.
 */
class Scale(
    var scaleName : String,
    var scaleList : ArrayList<String>
) {
}