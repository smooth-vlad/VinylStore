package com.android.vinylstore.ui.main.search_artists_fragment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
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

class SearchArtistsViewModel(
    private val vinylsRepository: VinylsRepository
) :
    ViewModel() {

    companion object {
        private const val TAG = "SearchArtistsViewModel"
    }

    init {
        Log.d(TAG, "init")
    }

    private val _items = MutableLiveData<Flow<PagingData<Artist>>?>()
    val items get() = _items

    private var _artistsSearchFlow: Flow<PagingData<Artist>>? = null

    fun initSearch(query: String) {
        _artistsSearchFlow = Pager(
            config = PagingConfig(TopArtistsPagingSource.FETCHING_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { vinylsRepository.searchArtistsPagingSource(query) }
        )
            .flow
            .cachedIn(viewModelScope)
        _items.value = _artistsSearchFlow
    }
}

@Suppress("UNCHECKED_CAST")
class SearchArtistsViewModelFactory(
    private val vinylsRepository: VinylsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SearchArtistsViewModel(vinylsRepository) as T
    }
}