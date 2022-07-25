package com.android.vinylstore.di.activity_main.fragment_artist

import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.cart.viewmodel.CartViewModelFactory
import com.android.vinylstore.ui.catalog.artist.viewmodel.ArtistViewModelFactory
import com.android.vinylstore.ui.catalog.artists.search_artists_fragment.viewmodel.SearchArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.viewmodel.ArtistsViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FragmentArtistModule {
    @Provides
    fun provideArtistViewModelFactory(vinylsRepository: VinylsRepository, @Named("arg_artist_name") artistName: String): ArtistViewModelFactory {
        return ArtistViewModelFactory(vinylsRepository, artistName)
    }
}