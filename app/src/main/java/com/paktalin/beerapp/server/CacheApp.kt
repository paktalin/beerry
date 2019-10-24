package com.paktalin.beerapp.server

import android.app.Application
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.squareup.picasso.OkHttpDownloader

class CacheApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        val builder = Picasso.Builder(this)
        builder.downloader(OkHttpDownloader(this, Integer.MAX_VALUE.toLong()))
        val built = builder.build()
        Picasso.setSingletonInstance(built)
    }

    private val requestQueue: RequestQueue? = null
        get() {
            if (field == null)
                return Volley.newRequestQueue(applicationContext)
            return field
        }

    fun <T> addToRequestQueue(request: Request<T>, tag: String) {
        request.tag = tag
        requestQueue?.add(request)
    }

    fun cache(): Cache? {
        return requestQueue?.cache
    }

    companion object {
        @get:Synchronized
        var instance: CacheApp? = null
            private set
    }
}