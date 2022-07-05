package com.android.vinylstore.lastfm_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LastFmApi(val apiKey: String) {
    val baseUrl = "https://ws.audioscrobbler.com/"

    private var _retrofit: Retrofit? = null
    val retrofit: Retrofit
        get() {
        if (_retrofit == null)
            initializeRetrofit()
        return _retrofit!!
    }

    private fun initializeRetrofit() {
        _retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}