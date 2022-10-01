package com.github.enteraname74.transposer.classes

import kotlin.collections.ArrayList

class AppData {
    companion object {
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
    }
}