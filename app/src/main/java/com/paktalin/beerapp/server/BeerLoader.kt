package com.paktalin.beerapp.server

import com.android.volley.toolbox.JsonArrayRequest
import com.paktalin.beerapp.Beer
import com.paktalin.beerapp.BeerFilter
import org.json.JSONArray

class BeerLoader(beerFilter: BeerFilter?) {

    private var suffix = ""

    private var beerListUrl: (page: Int) -> String =
        { page -> "https://api.punkapi.com/v2/beers?page=$page&per_page=$BEER_PER_PAGE$suffix" }

    init {
        beerFilter?.apply {
            suffix = abvGt + abvLt + ibuGt + ibuLt + ebcGt + ebcLt
        }
    }

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