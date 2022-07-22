package com.android.vinylstore.data.di.activity_main

import com.android.vinylstore.ui.main.search_artists_fragment.viewmodel.SearchArtistsViewModelFactory
import com.android.vinylstore.ui.main.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import com.android.vinylstore.ui.main.viewmodel.MainViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [ActivityMainModule::class])
interface ActivityMainComponent {
    fun getMainViewModelFactory(): MainViewModelFactory
    fun getTopArtistsViewModelFactory(): TopArtistsViewModelFactory
    fun getSearchArtistsViewModelFactory(): SearchArtistsViewModelFactory
}