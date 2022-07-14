package com.android.vinylstore.presentation.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.lastfm_api.AlbumsApiService
import com.android.vinylstore.lastfm_api.classes.Artist
import com.android.vinylstore.lastfm_api.responses.TopArtistResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val api: AlbumsApiService) : ViewModel() {
    private var _isDataLoading = MutableLiveData(false)
    val isDataLoading: LiveData<Boolean> = _isDataLoading

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _artists: MutableLiveData<List<Artist>> = MutableLiveData()
    val artists: LiveData<List<Artist>> = _artists

    private fun requestTopArtists() {
        val call = api.getTopArtists(BuildConfig.LASTFM_API_KEY, limit = 100)

        _isDataLoading.value = true
        call.enqueue(object : Callback<TopArtistResponse> {
            override fun onResponse(
                call: Call<TopArtistResponse?>?,
                response: Response<TopArtistResponse?>
            ) {
                onTopArtistsResponse(response)
            }

            override fun onFailure(call: Call<TopArtistResponse?>?, throwable: Throwable) {
                _error.value = throwable.localizedMessage
                _isDataLoading.value = false
            }
        })
    }

    private fun onTopArtistsResponse(response: Response<TopArtistResponse?>) {
        _isDataLoading.value = false
        response.body()?.let {
            _artists.postValue(it.artists.artist)
        }
    }

    // Returns false if refreshing failed
    fun refresh(): Boolean {
        return if (isDataLoading.value == true) {
            false
        } else {
            requestTopArtists()
            true
        }
    }
}

class MainViewModelFactory(private val api: AlbumsApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(api) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}