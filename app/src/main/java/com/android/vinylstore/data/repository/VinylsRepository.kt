package com.android.vinylstore.data.repository

import com.android.vinylstore.data.ArtistsPagingSource
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import javax.inject.Inject


class VinylsRepository @Inject constructor(private val albumsApiService: AlbumsApiService) {
    fun artistsPagingSource() = ArtistsPagingSource(albumsApiService)
}