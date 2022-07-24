package com.android.vinylstore.ui.catalog.artist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.lastfm_api.classes.Album
import com.android.vinylstore.data.lastfm_api.classes.Tag
import com.android.vinylstore.data.lastfm_api.responses.ArtistInfoResponse
import com.android.vinylstore.data.lastfm_api.responses.TopAlbumsResponse
import com.android.vinylstore.ui.RefreshingViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistViewModel(private val api: AlbumsApiService, private val artistName: String) :
    RefreshingViewModel() {
    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _tags: MutableLiveData<List<Tag>> = MutableLiveData()
    val tags: LiveData<List<Tag>> = _tags

    private var _topAlbums: MutableLiveData<List<Album>> = MutableLiveData()
    val topAlbums: LiveData<List<Album>> = _topAlbums

    private var _artistImagePath: MutableLiveData<String> = MutableLiveData()
    val artistImagePath: LiveData<String> = _artistImagePath

    companion object {
        const val REQUEST_ARTIST_INFO_TAG = "requestArtistInfo"
        const val REQUEST_TOP_ALBUMS_TAG = "requestTopAlbums"
    }

    override fun onRefresh() {
        requestTopAlbums()
        requestArtistInfo()
    }

    private fun requestTopAlbums() {
        val call = api.getTopAlbums(apiKey = BuildConfig.LASTFM_API_KEY, artist = artistName!!)

        startLoading(REQUEST_TOP_ALBUMS_TAG)
        call.enqueue(object : Callback<TopAlbumsResponse> {
            override fun onResponse(
                call: Call<TopAlbumsResponse?>?,
                response: Response<TopAlbumsResponse?>
            ) {
                endLoading(REQUEST_TOP_ALBUMS_TAG)
                onTopAlbumsResponse(response)
            }

            override fun onFailure(call: Call<TopAlbumsResponse?>?, throwable: Throwable) {
                endLoading(REQUEST_TOP_ALBUMS_TAG)
                _error.postValue("requestTopAlbums():  ${throwable.localizedMessage}")
            }
        })
    }

    private fun requestArtistInfo() {
        val call = api.getInfoArtist(apiKey = BuildConfig.LASTFM_API_KEY, artist = artistName)

        startLoading(REQUEST_ARTIST_INFO_TAG)
        call.enqueue(object : Callback<ArtistInfoResponse> {
            override fun onResponse(
                call: Call<ArtistInfoResponse?>?,
                response: Response<ArtistInfoResponse?>
            ) {
                endLoading(REQUEST_ARTIST_INFO_TAG)
                onArtistInfoResponse(response)
            }

            override fun onFailure(call: Call<ArtistInfoResponse?>?, throwable: Throwable) {
                endLoading(REQUEST_ARTIST_INFO_TAG)
                _error.postValue("requestArtistInfo():  ${throwable.localizedMessage}")
            }
        })
    }

    private fun onTopAlbumsResponse(response: Response<TopAlbumsResponse?>) {
        response.body()?.let {
            _topAlbums.postValue(it.topAlbums.album)

            val topAlbum = it.topAlbums.album[0]
            _artistImagePath.postValue(topAlbum.image.last().path)
        }
    }

    private fun onArtistInfoResponse(response: Response<ArtistInfoResponse?>) {
        response.body()?.let {
            _tags.postValue(it.artist.tags.tag)
        }
    }
}

class ArtistViewModelFactory(private val api: AlbumsApiService, private val artistName: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ArtistViewModel::class.java))
            return ArtistViewModel(api, artistName) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}