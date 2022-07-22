package com.android.vinylstore.data.di.activity_main.fragment_top_artists

import com.android.vinylstore.ui.main.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import dagger.Subcomponent

@Subcomponent(modules = [FragmentTopArtistsModule::class])
interface FragmentTopArtistsComponent {
    fun getTopArtistsModelFactory(): TopArtistsViewModelFactory
}