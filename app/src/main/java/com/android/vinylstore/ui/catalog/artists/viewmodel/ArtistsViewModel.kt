package com.android.vinylstore.ui.catalog.artists.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.vinylstore.data.repository.VinylsRepository

class ArtistsViewModel(private val vinylsRepository: VinylsRepository) :
    ViewModel() {

    companion object {
        private const val TAG = "ArtistsViewModel"
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
class ArtistsViewModelFactory(private val vinylsRepository: VinylsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return ArtistsViewModel(vinylsRepository) as T
    }
}