package com.android.vinylstore.lastfm_api

import com.android.vinylstore.lastfm_api.interfaces.AlbumsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LastFmApi(val apiKey: String) {
    val baseUrl = "https://ws.audioscrobbler.com/"

    private var _albumsApiService: AlbumsApiService? = null
    val albumsApiService: AlbumsApiService
        get() {
            if (_albumsApiService == null)
                _albumsApiService = retrofit.create(AlbumsApiService::class.java)
            return _albumsApiService!!
        }

    private var _retrofit: Retrofit? = null
    private val retrofit: Retrofit
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