package com.paktalin.beerapp.server

import android.app.Application
import com.android.volley.*
import com.android.volley.toolbox.Volley

class BackendVolley : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
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

    fun cancelPendingRequests(tag: Any) {
        if (requestQueue != null)
            requestQueue!!.cancelAll(tag)
    }

    fun cache(): Cache? {
        return requestQueue?.cache
    }

    companion object {
        @get:Synchronized
        var instance: BackendVolley? = null
            private set
    }
}