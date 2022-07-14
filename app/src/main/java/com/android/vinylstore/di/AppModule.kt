package com.android.vinylstore.di

import com.android.vinylstore.BuildConfig
import com.android.vinylstore.lastfm_api.AlbumsApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {
    @Provides
    fun getAlbumsApiService(): AlbumsApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.LASTFM_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(AlbumsApiService::class.java)
    }
}