package com.android.vinylstore.di.activity_main.fragment_artist

import com.android.vinylstore.ui.catalog.artist.viewmodel.ArtistViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@Subcomponent(modules = [FragmentArtistModule::class])
interface FragmentArtistComponent {
    fun getArtistViewModelFactory(): ArtistViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(@Named("arg_artist_name") @BindsInstance artistName: String): FragmentArtistComponent
    }
}