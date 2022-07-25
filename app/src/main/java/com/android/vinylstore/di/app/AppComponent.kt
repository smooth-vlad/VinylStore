package com.android.vinylstore.di.app

import android.content.Context
import com.android.vinylstore.di.activity_main.ActivityMainComponent
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.repository.VinylsRepository
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent.Factory
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getAlbumsApiService(): AlbumsApiService
    fun getVinylsRepository(): VinylsRepository
    fun getActivityMainComponent(): ActivityMainComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}