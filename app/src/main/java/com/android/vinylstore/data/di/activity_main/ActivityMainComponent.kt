package com.android.vinylstore.data.di.activity_main

import com.android.vinylstore.data.di.activity_main.fragment_search_artists.FragmentSearchArtistsComponent
import com.android.vinylstore.data.di.activity_main.fragment_top_artists.FragmentTopArtistsComponent
import com.android.vinylstore.ui.main.viewmodel.MainViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [ActivityMainModule::class])
interface ActivityMainComponent {
    fun getMainViewModelFactory(): MainViewModelFactory
    fun getFragmentTopArtistsComponent(): FragmentTopArtistsComponent
    fun getFragmentSearchArtistsComponent(): FragmentSearchArtistsComponent
}