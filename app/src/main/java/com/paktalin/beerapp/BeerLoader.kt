package com.paktalin.beerapp

import com.android.volley.toolbox.JsonArrayRequest
import com.paktalin.beerapp.server.BackendVolley
import org.json.JSONArray

class BeerLoader(page: Int = 1) {

    private val beerListUrl = "https://api.punkapi.com/v2/beers?page=$page&per_page=$BEER_PER_PAGE"

    companion object {
        private const val BEER_PER_PAGE = 25

        fun nextPage(itemsCount: Int): Int {
            return itemsCount/BEER_PER_PAGE + 1
        }
    }

    fun loadBeers(onSuccess: (MutableList<Beer>) -> Unit) {
        val request = JsonArrayRequest(beerListUrl,
            { onSuccess(processResponse(it)) },
            {
                val data = BackendVolley.instance?.cache()?.get(beerListUrl)?.data
                data?.let { onSuccess(processResponse(JSONArray(String(data)))) }
            }
        )
        BackendVolley.instance?.addToRequestQueue(request, beerListUrl)
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