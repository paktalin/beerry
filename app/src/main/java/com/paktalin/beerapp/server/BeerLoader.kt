package com.paktalin.beerapp.server

import com.android.volley.toolbox.JsonArrayRequest
import com.paktalin.beerapp.Beer
import org.json.JSONArray

class BeerLoader() {

    private var suffix = ""

    constructor(abvRange: IntRange, ibuRange: IntRange, ebcRange: IntRange): this() {
        val gt: (IntRange) -> Int? = { range -> if (range.first != 0) range.first else null }
        val lt: (IntRange) -> Int? = { range -> if (range.last != Int.MAX_VALUE) range.last else null }

        val abvGt = gt(abvRange)?.let { "&abv_gt=$it" } ?: ""
        val abvLt = lt(abvRange)?.let { "&abv_lt=$it" } ?: ""
        val ibuGt = gt(ibuRange)?.let { "&ibu_gt=$it" } ?: ""
        val ibuLt = lt(ibuRange)?.let { "&ibu_lt=$it" } ?: ""
        val ebcGt = gt(ebcRange)?.let { "&ebc_gt=$it" } ?: ""
        val ebcLt = lt(ebcRange)?.let { "&ebc_lt=$it" } ?: ""

        suffix = abvGt + abvLt + ibuGt + ibuLt + ebcGt + ebcLt
    }

    private var beerListUrl: (page: Int) -> String =
        { page -> "https://api.punkapi.com/v2/beers?page=$page&per_page=$BEER_PER_PAGE$suffix" }

    companion object {
        private const val BEER_PER_PAGE = 25

        fun nextPage(itemsCount: Int): Int {
            return itemsCount / BEER_PER_PAGE + 1
        }
    }

    fun loadBeers(onSuccess: (MutableList<Beer>) -> Unit, page: Int = 1) {
        val request = JsonArrayRequest(beerListUrl(page),
            { onSuccess(processResponse(it)) },
            {
                val data = CacheApp.instance?.cache()?.get(beerListUrl(page))?.data
                data?.let { onSuccess(processResponse(JSONArray(String(data)))) }
            }
        )
        CacheApp.instance?.addToRequestQueue(request, beerListUrl(page))
    }

    private fun processResponse(jsonArray: JSONArray): MutableList<Beer> {
        val beers = mutableListOf<Beer>()
        for (i in 0 until jsonArray.length()) {
            val beer = Beer(jsonArray.getJSONObject(i))
            beers.add(beer)
        }
        return beers
    }
}