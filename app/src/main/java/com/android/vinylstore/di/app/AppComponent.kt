package com.android.vinylstore.di.app

import com.android.vinylstore.lastfm_api.AlbumsApiService
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun getAlbumsApiService(): AlbumsApiService
}