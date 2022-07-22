package com.android.vinylstore.data.di.activity_main.fragment_top_artists

import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.main.top_artists_fragment.viewmodel.TopArtistsViewModelFactory
import com.android.vinylstore.ui.main.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FragmentTopArtistsModule {
    @Provides
    fun provideTopArtistsViewModelFactory(vinylsRepository: VinylsRepository): TopArtistsViewModelFactory {
        return TopArtistsViewModelFactory(vinylsRepository)
    }
}