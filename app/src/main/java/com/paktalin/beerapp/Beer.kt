package com.paktalin.beerapp

import org.json.JSONObject

data class Beer(
    val name: String,
    val imageUrl: String,
    val abv: Double?,
    val ibu: Double?,
    val ebc: Double?
) {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString("name"),
        jsonObject.getString("image_url"),
        jsonObject.optDouble("abv"),
        jsonObject.optDouble("ibu"),
        jsonObject.optDouble("ebc")
    )
}