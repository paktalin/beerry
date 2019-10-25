package com.paktalin.beerapp

import android.content.Context
import org.json.JSONArray

private const val SHARED_PREF_NAME = "BeerAppSharedPreferences"
private const val SHARED_PREF_MODE = 0

private const val KEY_JSON_FAVORITE = "favorite_json"

fun Context.saveAsFavorite(beer: Beer) {
    val pref = getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_MODE)
    val oldFavorite = pref.getString(KEY_JSON_FAVORITE, null)
    val updated = updateFavorite(oldFavorite, beer)
    pref.edit()
        .apply { putString(KEY_JSON_FAVORITE, updated) }
        .apply()
}

fun Context.removeFromFavorite(beer: Beer) {
    val favoritesJson = getJsonArray(this) ?: return
    for (i in 0 until favoritesJson.length()) {
        if (beer.id == favoritesJson.getJSONObject(i).getLong(KEY_BEER_ID)) {
            favoritesJson.remove(i)
            getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_MODE).edit()
                .apply { putString(KEY_JSON_FAVORITE, favoritesJson.toString()) }
                .apply()
            return
        }
    }
}

fun Context.getFavoriteBeers(): MutableList<Beer>? {
    val favoritesJson = getJsonArray(this) ?: return null
    val beers = mutableListOf<Beer>()
    for (i in 0 until favoritesJson.length())
        beers.add(Beer(favoritesJson.getJSONObject(i)))
    return beers
}

private fun updateFavorite(oldFavorite: String?, beer: Beer): String {
    val favoriteBeers = if (oldFavorite != null) JSONArray(oldFavorite) else JSONArray()
    favoriteBeers.put(beer.toJson())
    return favoriteBeers.toString()
}

private fun getJsonArray(context: Context): JSONArray? {
    val pref = context.getSharedPreferences(SHARED_PREF_NAME, SHARED_PREF_MODE)
    val favoritesString = pref.getString(KEY_JSON_FAVORITE, null) ?: return null
    return JSONArray(favoritesString)
}