package com.android.vinylstore.data.repository

import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.repository.data_source.ArtistsPagingSource
import javax.inject.Inject


class VinylsRepository @Inject constructor(private val albumsApiService: AlbumsApiService) {
    fun artistsPagingSource() = ArtistsPagingSource(albumsApiService)
}