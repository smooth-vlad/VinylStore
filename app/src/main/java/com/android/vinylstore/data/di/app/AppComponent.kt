package com.android.vinylstore.data.di.app

import com.android.vinylstore.data.di.activity_main.ActivityMainComponent
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.main.top_artists_fragment.view.TopArtistsFragment
import com.android.vinylstore.ui.main.view.MainActivity
import com.android.vinylstore.ui.main.viewmodel.MainViewModelFactory
import dagger.Component
import dagger.Subcomponent.Factory
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getAlbumsApiService(): AlbumsApiService
    fun getVinylsRepository(): VinylsRepository
    fun getActivityMainComponent(): ActivityMainComponent
}