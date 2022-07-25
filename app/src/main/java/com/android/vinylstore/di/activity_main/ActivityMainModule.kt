package com.android.vinylstore.di.activity_main

import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.cart.viewmodel.CartViewModelFactory
import com.android.vinylstore.ui.catalog.artists.search_artists_fragment.viewmodel.SearchArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.viewmodel.ArtistsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityMainModule {
    @Provides
    fun provideMainViewModelFactory(vinylsRepository: VinylsRepository): ArtistsViewModelFactory {
        return ArtistsViewModelFactory(vinylsRepository)
    }

    @Provides
    fun provideTopArtistsViewModelFactoryFactory(vinylsRepository: VinylsRepository): TopArtistsViewModelFactory {
        return TopArtistsViewModelFactory(vinylsRepository)
    }

    @Provides
    fun provideSearchArtistsViewModelFactory(vinylsRepository: VinylsRepository): SearchArtistsViewModelFactory {
        return SearchArtistsViewModelFactory(vinylsRepository)
    }

    @Provides
    fun provideCartViewModelFactory(vinylsRepository: VinylsRepository): CartViewModelFactory {
        return CartViewModelFactory(vinylsRepository)
    }
}