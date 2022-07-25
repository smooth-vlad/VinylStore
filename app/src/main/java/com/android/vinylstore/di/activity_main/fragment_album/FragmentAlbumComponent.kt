package com.android.vinylstore.di.activity_main.fragment_album

import com.android.vinylstore.ui.catalog.album.viewmodel.AlbumViewModelFactory
import com.android.vinylstore.ui.catalog.artist.viewmodel.ArtistViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@Subcomponent(modules = [FragmentAlbumModule::class])
interface FragmentAlbumComponent {
    fun getAlbumViewModelFactory(): AlbumViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @Named("arg_album_name_FragmentAlbumModule") @BindsInstance albumName: String,
            @Named("arg_artist_name_FragmentAlbumModule") @BindsInstance artistName: String
        ): FragmentAlbumComponent
    }
}