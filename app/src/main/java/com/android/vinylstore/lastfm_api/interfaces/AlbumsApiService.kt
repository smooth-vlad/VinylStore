package com.android.vinylstore.lastfm_api.interfaces

import com.android.vinylstore.lastfm_api.responses.TopAlbumsResponse
import com.android.vinylstore.lastfm_api.responses.TopArtistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsApiService {
    @GET("/2.0/")
    fun getTopArtists(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("format") format: String = "json",
        @Query("method") method: String = "chart.gettopartists"
    ): Call<TopArtistResponse>

    @GET("/2.0/")
    fun getTopAlbums(
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String,
        @Query("autocorrect") autoCorrect: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("format") format: String = "json",
        @Query("method") method: String = "artist.gettopalbums"
    ): Call<TopAlbumsResponse>
}