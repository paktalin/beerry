package com.paktalin.beerapp.ui

import java.text.DecimalFormat

fun Double.formatAbv(): String {
    return DecimalFormat("##.#'%'").format(this)
}
fun Double.format(): String {
    return DecimalFormat("###").format(this)
}

fun String.formatUnits(): String {
    return when(this) {
        "litres" -> "lt"
        "kilograms" -> "kg"
        else -> this
    }
}