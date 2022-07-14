package com.android.vinylstore.di

import com.android.vinylstore.lastfm_api.LastFmApi
import dagger.Component
import javax.inject.Inject

@Component
interface AppComponent {
    fun getLastFmApi(): LastFmApi
}