package com.android.vinylstore.di.activity_main

import com.android.vinylstore.di.activity_main.fragment_artist.FragmentArtistComponent
import com.android.vinylstore.ui.cart.viewmodel.CartViewModelFactory
import com.android.vinylstore.ui.catalog.artists.search_artists_fragment.viewmodel.SearchArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.viewmodel.ArtistsViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [ActivityMainModule::class])
interface ActivityMainComponent {
    fun getMainViewModelFactory(): ArtistsViewModelFactory
    fun getCartModelFactory(): CartViewModelFactory
    fun getTopArtistsViewModelFactory(): TopArtistsViewModelFactory
    fun getSearchArtistsViewModelFactory(): SearchArtistsViewModelFactory

    fun getFragmentArtistComponentFactory(): FragmentArtistComponent.Factory
}