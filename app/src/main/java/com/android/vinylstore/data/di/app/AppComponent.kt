package com.android.vinylstore.data.di.app

import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.main.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getAlbumsApiService(): AlbumsApiService
    fun getVinylsRepository(): VinylsRepository
    fun inject(activity: MainActivity)
}