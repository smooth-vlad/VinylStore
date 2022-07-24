package com.android.vinylstore.di.activity_main

import com.android.vinylstore.ui.catalog.artists.search_artists_fragment.viewmodel.SearchArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.viewmodel.ArtistsViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [ActivityMainModule::class])
interface ActivityMainComponent {
    fun getMainViewModelFactory(): ArtistsViewModelFactory
    fun getTopArtistsViewModelFactory(): TopArtistsViewModelFactory
    fun getSearchArtistsViewModelFactory(): SearchArtistsViewModelFactory
}