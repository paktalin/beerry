package com.paktalin.beerapp.ui.home

import java.io.Serializable

class BeerFilter(abvRange: IntRange, ibuRange: IntRange, ebcRange: IntRange): Serializable {
    val abvLeft = abvRange.first
    val abvRight = abvRange.last
    val ibuLeft = ibuRange.first
    val ibuRight = ibuRange.last
    val ebcLeft = ebcRange.first
    val ebcRight = ebcRange.last

    val abvGt = gt(abvRange)?.let { "&abv_gt=$it" } ?: ""
    val abvLt = lt(abvRange)?.let { "&abv_lt=$it" } ?: ""
    val ibuGt = gt(ibuRange)?.let { "&ibu_gt=$it" } ?: ""
    val ibuLt = lt(ibuRange)?.let { "&ibu_lt=$it" } ?: ""
    val ebcGt = gt(ebcRange)?.let { "&ebc_gt=$it" } ?: ""
    val ebcLt = lt(ebcRange)?.let { "&ebc_lt=$it" } ?: ""
}

fun gt(range: IntRange): Int? {
    return if (range.first != 0) range.first else null
}

fun lt(range: IntRange): Int? {
    return if (range.last != Int.MAX_VALUE) range.last else null
}