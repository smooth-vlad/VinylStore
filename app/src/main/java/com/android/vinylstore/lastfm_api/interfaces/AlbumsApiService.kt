package com.android.vinylstore.lastfm_api.interfaces

import com.android.vinylstore.lastfm_api.responses.TopAlbumsResponse
import com.android.vinylstore.lastfm_api.responses.TopArtistResponse
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

    // Example: http://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=cher&api_key=35a05fb4101e2310860399151c60f746&format=json
    @GET("/2.0/")
    fun getTopAlbums(
        @Query("method") method: String = "artist.gettopalbums",
        @Query("format") format: String = "json",
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String,
        @Query("autocorrect") autoCorrect: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null
    ): Call<TopAlbumsResponse>
}