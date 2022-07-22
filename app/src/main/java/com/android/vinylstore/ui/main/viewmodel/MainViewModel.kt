package com.android.vinylstore.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.*
import com.android.vinylstore.data.lastfm_api.classes.Artist
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.data.repository.data_source.TopArtistsPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class MainViewModel(private val vinylsRepository: VinylsRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _isInSearchMode = MutableLiveData(false)
    val isInSearchMode: LiveData<Boolean> = _isInSearchMode

    init {
        Log.d(TAG, "init")
    }

    /**
     * Move to the fragment for searching
     */
    fun enterSearchMode() {
        _isInSearchMode.value = true
    }

    /**
     * Move back to the fragment with top artists
     */
    fun exitSearchMode() {
        _isInSearchMode.value = false
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val vinylsRepository: VinylsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MainViewModel(vinylsRepository) as T
    }
}