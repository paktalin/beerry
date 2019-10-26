package com.paktalin.beerapp

import com.paktalin.beerapp.ui.BeerColorSet
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

const val KEY_BEER_ID = "id"
const val KEY_BEER_NAME = "name"
const val KEY_IMAGE_URL = "image_url"
const val KEY_ABV = "abv"
const val KEY_IBU = "ibu"
const val KEY_EBC = "ebc"


data class Beer(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val abv: Double?,
    val ibu: Double?,
    val ebc: Double?): Serializable {

    var isFavorite: Boolean = false
    var colorSet: BeerColorSet? = null

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getLong(KEY_BEER_ID),
        jsonObject.getString(KEY_BEER_NAME),
        jsonObject.getString(KEY_IMAGE_URL),
        jsonObject.optDouble(KEY_ABV),
        jsonObject.optDouble(KEY_IBU),
        jsonObject.optDouble(KEY_EBC)
    )

    fun toJson(): JSONObject {
        return JSONObject()
            .put(KEY_BEER_ID, id)
            .put(KEY_BEER_NAME, name)
            .put(KEY_IMAGE_URL, imageUrl)
            .put(KEY_ABV, if (abv?.isNaN() == true) null else abv)
            .put(KEY_IBU, if (ibu?.isNaN() == true) null else ibu)
            .put(KEY_EBC, if (ebc?.isNaN() == true) null else ebc)
    }

    companion object {
        fun beersFromJsonArray(jsonArray: JSONArray, favorites: MutableList<Beer>? = null): MutableList<Beer> {
            val beers = mutableListOf<Beer>()
            val favoriteIds = favorites?.map(Beer::id)
            for (i in 0 until jsonArray.length()) {
                val beer = Beer(jsonArray.getJSONObject(i))
                if (favoriteIds != null && beer.id in favoriteIds) beer.isFavorite = true
                beers.add(beer)
            }
            return beers
        }
    }
}