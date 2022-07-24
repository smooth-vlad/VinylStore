package com.android.vinylstore.ui.catalog.artists.top_artists_fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.vinylstore.data.lastfm_api.classes.Artist
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.data.repository.data_source.TopArtistsPagingSource
import kotlinx.coroutines.flow.Flow

class TopArtistsViewModel(private val vinylsRepository: VinylsRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "TopArtistsViewModel"
    }

    init {
        Log.d(TAG, "init")
    }

    val items: Flow<PagingData<Artist>> = Pager(
        config = PagingConfig(TopArtistsPagingSource.FETCHING_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { vinylsRepository.topArtistsPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)
}

@Suppress("UNCHECKED_CAST")
class TopArtistsViewModelFactory(private val vinylsRepository: VinylsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return TopArtistsViewModel(vinylsRepository) as T
    }
}