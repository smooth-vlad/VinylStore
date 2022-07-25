package com.android.vinylstore.di.app

import android.content.Context
import androidx.room.Room
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.data.db.database.VinylsStoreDatabase
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.repository.VinylsRepository
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
    fun provideVinylsRepository(albumsApiService: AlbumsApiService, database: VinylsStoreDatabase) : VinylsRepository {
        return VinylsRepository(albumsApiService, database)
    }

    @Singleton
    @Provides
    fun provideVinylsStoreDatabase(applicationContext: Context): VinylsStoreDatabase {
        return Room.databaseBuilder(
            applicationContext,
            VinylsStoreDatabase::class.java, "vinyls_store_db"
        ).build()
    }
}