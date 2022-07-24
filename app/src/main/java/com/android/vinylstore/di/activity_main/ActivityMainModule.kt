package com.android.vinylstore.di.activity_main

import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.catalog.artists.search_artists_fragment.viewmodel.SearchArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import com.android.vinylstore.ui.catalog.artists.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityMainModule {
    @Provides
    fun provideMainViewModelFactory(vinylsRepository: VinylsRepository): MainViewModelFactory {
        return MainViewModelFactory(vinylsRepository)
    }

    @Provides
    fun provideTopArtistsViewModelFactoryFactory(vinylsRepository: VinylsRepository): TopArtistsViewModelFactory {
        return TopArtistsViewModelFactory(vinylsRepository)
    }

    @Provides
    fun provideSearchArtistsViewModelFactory(vinylsRepository: VinylsRepository): SearchArtistsViewModelFactory {
        return SearchArtistsViewModelFactory(vinylsRepository)
    }
}