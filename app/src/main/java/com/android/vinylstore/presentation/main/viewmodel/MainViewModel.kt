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
import com.android.vinylstore.presentation.RefreshingViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val api: AlbumsApiService) : RefreshingViewModel() {

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _artists: MutableLiveData<List<Artist>> = MutableLiveData()
    val artists: LiveData<List<Artist>> = _artists

    companion object {
        const val REQUEST_TOP_ARTISTS_TAG = "requestTopArtists"
    }

    private fun requestTopArtists() {
        val call = api.getTopArtists(BuildConfig.LASTFM_API_KEY, limit = 100)

        startLoading(REQUEST_TOP_ARTISTS_TAG)
        call.enqueue(object : Callback<TopArtistResponse> {
            override fun onResponse(
                call: Call<TopArtistResponse?>?,
                response: Response<TopArtistResponse?>
            ) {
                endLoading(REQUEST_TOP_ARTISTS_TAG)
                onTopArtistsResponse(response)
            }

            override fun onFailure(call: Call<TopArtistResponse?>?, throwable: Throwable) {
                endLoading(REQUEST_TOP_ARTISTS_TAG)
                _error.value = throwable.localizedMessage
            }
        })
    }

    private fun onTopArtistsResponse(response: Response<TopArtistResponse?>) {
        response.body()?.let {
            _artists.postValue(it.artists.artist)
        }
    }

    override fun onRefresh() {
        requestTopArtists()
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