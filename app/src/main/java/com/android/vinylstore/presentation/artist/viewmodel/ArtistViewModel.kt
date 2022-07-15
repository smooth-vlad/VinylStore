package com.android.vinylstore.presentation.artist.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.R
import com.android.vinylstore.Root
import com.android.vinylstore.adapters.AlbumItemAdapter
import com.android.vinylstore.lastfm_api.AlbumsApiService
import com.android.vinylstore.lastfm_api.classes.Album
import com.android.vinylstore.lastfm_api.classes.Tag
import com.android.vinylstore.lastfm_api.responses.ArtistInfoResponse
import com.android.vinylstore.lastfm_api.responses.TopAlbumsResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistViewModel(private val api: AlbumsApiService, private val artistName: String) :
    ViewModel() {
    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _tags: MutableLiveData<List<Tag>> = MutableLiveData()
    val tags: LiveData<List<Tag>> = _tags

    private var _topAlbums: MutableLiveData<List<Album>> = MutableLiveData()
    val topAlbums: LiveData<List<Album>> = _topAlbums

    private var _artistImagePath: MutableLiveData<String> = MutableLiveData()
    val artistImagePath: LiveData<String> = _artistImagePath

    fun refresh() {
        requestTopAlbums()
        requestArtistInfo()
    }

    private fun requestTopAlbums() {
        val call = api.getTopAlbums(apiKey = BuildConfig.LASTFM_API_KEY, artist = artistName!!)

        call.enqueue(object : Callback<TopAlbumsResponse> {
            override fun onResponse(
                call: Call<TopAlbumsResponse?>?,
                response: Response<TopAlbumsResponse?>
            ) {
                onTopAlbumsResponse(response)
            }

            override fun onFailure(call: Call<TopAlbumsResponse?>?, throwable: Throwable) {
                _error.postValue("requestTopAlbums():  ${throwable.localizedMessage}")
            }
        })
    }

    private fun requestArtistInfo() {
        val call = api.getInfoArtist(apiKey = BuildConfig.LASTFM_API_KEY, artist = artistName)

        call.enqueue(object : Callback<ArtistInfoResponse> {
            override fun onResponse(
                call: Call<ArtistInfoResponse?>?,
                response: Response<ArtistInfoResponse?>
            ) {
                onArtistInfoResponse(response)
            }

            override fun onFailure(call: Call<ArtistInfoResponse?>?, throwable: Throwable) {
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