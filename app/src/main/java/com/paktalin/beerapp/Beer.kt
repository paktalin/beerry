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
const val KEY_FIRST_BREWED = "first_brewed"
const val KEY_FG = "target_fg"
const val KEY_OG = "target_og"
const val KEY_SRM = "srm"
const val KEY_PH = "ph"
const val KEY_ATTENUATION = "attenuation_level"
const val KEY_VOLUME = "volume"
const val KEY_BOIL_VOLUME = "boil_volume"
const val KEY_UNIT = "value"
const val KEY_VALUE = "unit"

class Beer constructor(val id: Long,
                       val name: String,
                       val imageUrl: String?,
                       abv: Double?,
                       ibu: Double?,
                       ebc: Double?,
                       val firstBrewed: String?,
                       fg: Double?,
                       og: Double?,
                       srm: Double?,
                       ph: Double?,
                       attenuation: Double?,
                       volume: Double?,
                       val volumeUnit: String?,
                       boilVolume: Double?,
                       val boilVolumeUnit: String?): Serializable {

    var abv: Double?
    var ibu: Double?
    var ebc: Double?
    var fg: Double?
    var og: Double?
    var srm: Double?
    var ph: Double?
    var attenuation: Double?
    var volume: Double?
    var boilVolume: Double?

    init {
        this.abv = abv?.nonNan()
        this.ibu = ibu?.nonNan()
        this.ebc = ebc?.nonNan()
        this.fg = fg?.nonNan()
        this.og = og?.nonNan()
        this.srm = srm?.nonNan()
        this.ph = ph?.nonNan()
        this.attenuation = attenuation?.nonNan()
        this.volume = volume?.nonNan()
        this.boilVolume = boilVolume?.nonNan()
    }

    private fun Double.nonNan(): Double? { return if(isNaN()) null else this }


    var isFavorite: Boolean = false
    var colorSet: BeerColorSet? = null

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getLong(KEY_BEER_ID),
        jsonObject.getString(KEY_BEER_NAME),
        jsonObject.getString(KEY_IMAGE_URL),
        jsonObject.optDouble(KEY_ABV),
        jsonObject.optDouble(KEY_IBU),
        jsonObject.optDouble(KEY_EBC),
        jsonObject.optString(KEY_FIRST_BREWED),
        jsonObject.optDouble(KEY_FG),
        jsonObject.optDouble(KEY_OG),
        jsonObject.optDouble(KEY_SRM),
        jsonObject.optDouble(KEY_PH),
        jsonObject.optDouble(KEY_ATTENUATION),
        jsonObject.optJSONObject(KEY_VOLUME)?.optDouble(KEY_VALUE),
        jsonObject.optJSONObject(KEY_VOLUME)?.optString(KEY_UNIT),
        jsonObject.optJSONObject(KEY_BOIL_VOLUME)?.optDouble(KEY_VALUE),
        jsonObject.optJSONObject(KEY_BOIL_VOLUME)?.optString(KEY_UNIT)
    )

    fun toJson(): JSONObject {
        return JSONObject()
            .put(KEY_BEER_ID, id)
            .put(KEY_BEER_NAME, name)
            .put(KEY_IMAGE_URL, imageUrl)
            .put(KEY_ABV, abv)
            .put(KEY_IBU, ibu)
            .put(KEY_EBC, ebc)
            .put(KEY_FIRST_BREWED, firstBrewed)
            .put(KEY_FG, fg)
            .put(KEY_OG, og)
            .put(KEY_SRM, srm)
            .put(KEY_PH, ph)
            .put(KEY_ATTENUATION, attenuation)
            .put(KEY_VOLUME, volume)
            .put(KEY_VOLUME, JSONObject().put(KEY_VALUE, volume).put(KEY_UNIT, volumeUnit))
            .put(KEY_BOIL_VOLUME, JSONObject().put(KEY_VALUE, boilVolume).put(KEY_UNIT, boilVolumeUnit))
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