package com.paktalin.beerapp

import org.json.JSONObject

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
    val ebc: Double?
) {

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
            .put(KEY_ABV, abv)
            .put(KEY_IBU, ibu)
            .put(KEY_EBC, ebc)
    }
}