package com.android.vinylstore.di.activity_main.fragment_album

import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.catalog.album.viewmodel.AlbumViewModelFactory
import com.android.vinylstore.ui.catalog.artist.viewmodel.ArtistViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FragmentAlbumModule {
    @Provides
    fun provideAlbumViewModelFactory(
        vinylsRepository: VinylsRepository,
        @Named("arg_album_name_FragmentAlbumModule") albumName: String,
        @Named("arg_artist_name_FragmentAlbumModule") artistName: String
    ): AlbumViewModelFactory {
        return AlbumViewModelFactory(vinylsRepository, albumName, artistName)
    }
}