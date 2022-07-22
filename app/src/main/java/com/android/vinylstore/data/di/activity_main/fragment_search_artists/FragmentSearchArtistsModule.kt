package com.android.vinylstore.data.di.activity_main.fragment_search_artists

import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.main.search_artists_fragment.viewmodel.SearchArtistsViewModelFactory
import com.android.vinylstore.ui.main.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FragmentSearchArtistsModule {
    @Provides
    fun provideSearchArtistsViewModelFactory(
        vinylsRepository: VinylsRepository
    ): SearchArtistsViewModelFactory {
        return SearchArtistsViewModelFactory(vinylsRepository)
    }
}