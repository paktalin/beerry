package com.paktalin.beerapp

import org.json.JSONObject

class Beer(val name: String) {

    constructor(jsonObject: JSONObject): this(jsonObject.getString("name")) {

    }

}