package com.example.orderskuy

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class MySingleton private constructor(private val mCtx: Context) {
    private var requestQueue: RequestQueue?
    fun getRequestQueue(): RequestQueue? {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.applicationContext)
        }
        return requestQueue
    }

    fun <T> addToRequestque(request: Request<T>?) {
        requestQueue!!.add(request)
    }

    companion object {
        private var mInstance: MySingleton? = null

        @Synchronized
        fun getInstance(context: Context): MySingleton? {
            if (mInstance == null) {
                mInstance = MySingleton(context)
            }
            return mInstance
        }
    }

    init {
        requestQueue = getRequestQueue()
    }
}