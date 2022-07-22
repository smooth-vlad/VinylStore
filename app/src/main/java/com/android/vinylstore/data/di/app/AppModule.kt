package com.android.vinylstore.data.di.app

import com.android.vinylstore.BuildConfig
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.main.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideAlbumsApiService(): AlbumsApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.LASTFM_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(AlbumsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideVinylsRepository(albumsApiService: AlbumsApiService) : VinylsRepository {
        return VinylsRepository(albumsApiService)
    }
}