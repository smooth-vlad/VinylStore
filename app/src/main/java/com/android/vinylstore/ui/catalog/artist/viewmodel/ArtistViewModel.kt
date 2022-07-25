package com.android.vinylstore.ui.catalog.artist.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.data.lastfm_api.classes.Album
import com.android.vinylstore.data.lastfm_api.classes.Tag
import com.android.vinylstore.data.lastfm_api.responses.ArtistInfoResponse
import com.android.vinylstore.data.lastfm_api.responses.TopAlbumsResponse
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.RefreshingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistViewModel(
    private val vinylsRepository: VinylsRepository,
    private val artistName: String
) :
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
        viewModelScope.launch {
            startLoading(REQUEST_TOP_ALBUMS_TAG)
            try {
                withContext(Dispatchers.Default) {
                    val response = vinylsRepository.getTopAlbumsForArtist(artistName)
                    withContext(Dispatchers.Main) {
                        onTopAlbumsResponse(response)
                    }
                }
            } catch (throwable: Throwable) {
                _error.postValue(throwable.toString())
            } finally {
                endLoading(REQUEST_TOP_ALBUMS_TAG)
            }
        }
    }

    private fun requestArtistInfo() {
        viewModelScope.launch {
            startLoading(REQUEST_ARTIST_INFO_TAG)
            try {
                withContext(Dispatchers.Default) {
                    val response = vinylsRepository.getArtistInfo(artistName)
                    withContext(Dispatchers.Main) {
                        onArtistInfoResponse(response)
                    }
                }
            } catch (throwable: Throwable) {
                _error.postValue(throwable.toString())
            } finally {
                endLoading(REQUEST_ARTIST_INFO_TAG)
            }
        }
    }

    private fun onTopAlbumsResponse(response: TopAlbumsResponse) {
        _topAlbums.postValue(response.topAlbums.album)

        val topAlbum = response.topAlbums.album[0]
        _artistImagePath.postValue(topAlbum.image.last().path)
    }

    private fun onArtistInfoResponse(response: ArtistInfoResponse) {
        _tags.postValue(response.artist.tags.tag)
    }
}

class ArtistViewModelFactory(
    private val vinylsRepository: VinylsRepository,
    private val artistName: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ArtistViewModel::class.java))
            return ArtistViewModel(vinylsRepository, artistName) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}