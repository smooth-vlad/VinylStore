package com.android.vinylstore.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.*
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.lastfm_api.classes.Artist
import com.android.vinylstore.data.lastfm_api.responses.TopArtistResponse
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.data.repository.data_source.ArtistsPagingSource
import com.android.vinylstore.ui.RefreshingViewModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val vinylsRepository: VinylsRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    val items: Flow<PagingData<Artist>> = Pager(
        config = PagingConfig(ArtistsPagingSource.FETCHING_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { vinylsRepository.artistsPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)
}