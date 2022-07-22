package com.android.vinylstore.data.di.activity_main.fragment_search_artists

import com.android.vinylstore.ui.main.search_artists_fragment.viewmodel.SearchArtistsViewModelFactory
import com.android.vinylstore.ui.main.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@Subcomponent(modules = [FragmentSearchArtistsModule::class])
interface FragmentSearchArtistsComponent {
    fun getSearchArtistsModelFactory(): SearchArtistsViewModelFactory
}