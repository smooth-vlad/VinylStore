package com.android.vinylstore.data.repository

import com.android.vinylstore.data.db.data_entity.CartItem
import com.android.vinylstore.data.db.database.VinylsStoreDatabase
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.repository.data_source.SearchArtistsPagingSource
import com.android.vinylstore.data.repository.data_source.TopArtistsPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


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
}