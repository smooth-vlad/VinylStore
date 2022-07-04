package com.android.vinylstore.lastfm_api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsApiService {
    @GET("/2.0/")
    fun getTopArtists(
        @Query("method") method: String = "chart.gettopartists",
        @Query("format") format: String = "json",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null
    ): Call<TopArtistResponse>
}