package com.android.vinylstore.data.repository

import com.android.vinylstore.BuildConfig
import com.android.vinylstore.data.db.data_entity.CartItem
import com.android.vinylstore.data.db.database.VinylsStoreDatabase
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.lastfm_api.responses.AlbumInfoResponse
import com.android.vinylstore.data.lastfm_api.responses.ArtistInfoResponse
import com.android.vinylstore.data.lastfm_api.responses.TopAlbumsResponse
import com.android.vinylstore.data.repository.data_source.SearchArtistsPagingSource
import com.android.vinylstore.data.repository.data_source.TopArtistsPagingSource


class VinylsRepository constructor(
    private val albumsApiService: AlbumsApiService,
    private val database: VinylsStoreDatabase
) {
    fun topArtistsPagingSource() = TopArtistsPagingSource(albumsApiService)
    fun searchArtistsPagingSource(query: String) =
        SearchArtistsPagingSource(albumsApiService, query)

    fun addItemToCart(cartItem: CartItem) = database.cartItemsDao().insert(cartItem)
    fun clearCart() = database.cartItemsDao().clear()

    val cartItems = database.cartItemsDao().getAll()

    suspend fun getTopAlbumsForArtist(artistName: String): TopAlbumsResponse =
        albumsApiService.getTopAlbumsForArtist(
            apiKey = BuildConfig.LASTFM_API_KEY,
            artist = artistName
        )

    suspend fun getArtistInfo(artistName: String): ArtistInfoResponse =
        albumsApiService.getInfoArtist(
            apiKey = BuildConfig.LASTFM_API_KEY,
            artist = artistName
        )

    suspend fun getAlbumInfo(albumName: String, artistName: String): AlbumInfoResponse =
        albumsApiService.getInfoAlbum(
            apiKey = BuildConfig.LASTFM_API_KEY,
            artist = artistName,
            album = albumName
        )
}