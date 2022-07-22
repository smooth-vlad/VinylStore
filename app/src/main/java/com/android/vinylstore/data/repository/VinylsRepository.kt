package com.android.vinylstore.data.repository

import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.repository.data_source.SearchArtistsPagingSource
import com.android.vinylstore.data.repository.data_source.TopArtistsPagingSource
import javax.inject.Inject


class VinylsRepository constructor(private val albumsApiService: AlbumsApiService) {
    fun topArtistsPagingSource() = TopArtistsPagingSource(albumsApiService)
    fun searchArtistsPagingSource(query: String) = SearchArtistsPagingSource(albumsApiService, query)
}