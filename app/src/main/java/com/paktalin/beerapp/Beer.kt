package com.paktalin.beerapp

import org.json.JSONObject

class Beer(val name: String, val imageUrl: String) {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString("name"),
        jsonObject.getString("image_url")
    )

}