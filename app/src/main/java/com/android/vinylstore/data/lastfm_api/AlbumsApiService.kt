package com.android.vinylstore.data.lastfm_api

import com.android.vinylstore.data.lastfm_api.responses.*
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
    ): Call<TopArtistsResponse>

    @GET("/2.0/")
    fun searchArtists(
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("format") format: String = "json",
        @Query("method") method: String = "artist.search"
    ): Call<SearchArtistsResponse>

    @GET("/2.0/")
    suspend fun getTopAlbumsForArtist(
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String,
        @Query("autocorrect") autoCorrect: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("format") format: String = "json",
        @Query("method") method: String = "artist.gettopalbums"
    ): TopAlbumsResponse

    @GET("/2.0/")
    suspend fun getInfoArtist(
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String,
        @Query("lang") lang: String? = null,
        @Query("autocorrect") autoCorrect: Boolean? = null,
        @Query("format") format: String = "json",
        @Query("method") method: String = "artist.getinfo"
    ): ArtistInfoResponse

    @GET("/2.0/")
    fun getInfoAlbum(
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String,
        @Query("album") album: String,
        @Query("lang") lang: String? = null,
        @Query("autocorrect") autoCorrect: Boolean? = null,
        @Query("format") format: String = "json",
        @Query("method") method: String = "album.getinfo"
    ): Call<AlbumInfoResponse>
}