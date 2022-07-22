package com.android.vinylstore.data.di.activity_main

import com.android.vinylstore.data.di.activity_main.fragment_search_artists.FragmentSearchArtistsComponent
import com.android.vinylstore.data.di.activity_main.fragment_top_artists.FragmentTopArtistsComponent
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.main.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityMainModule {
    @Provides
    fun provideMainViewModelFactory(vinylsRepository: VinylsRepository): MainViewModelFactory {
        return MainViewModelFactory(vinylsRepository)
    }
}