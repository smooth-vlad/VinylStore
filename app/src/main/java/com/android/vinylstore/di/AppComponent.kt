package com.android.vinylstore.di

import com.android.vinylstore.lastfm_api.AlbumsApiService
import dagger.Component
import javax.inject.Inject

@Component(modules = [AppModule::class])
interface AppComponent {
    fun getAlbumsApiService(): AlbumsApiService
}