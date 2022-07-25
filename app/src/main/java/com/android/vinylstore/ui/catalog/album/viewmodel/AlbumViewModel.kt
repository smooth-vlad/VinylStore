package com.android.vinylstore.ui.catalog.album.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.lastfm_api.classes.Tag
import com.android.vinylstore.data.lastfm_api.classes.Track
import com.android.vinylstore.data.lastfm_api.responses.AlbumInfoResponse
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.ui.RefreshingViewModel
import com.android.vinylstore.ui.catalog.artist.viewmodel.ArtistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumViewModel(
    private val vinylsRepository: VinylsRepository,
    private val albumName: String,
    private val artistName: String
) : RefreshingViewModel() {

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _tags: MutableLiveData<List<Tag>> = MutableLiveData()
    val tags: LiveData<List<Tag>> = _tags

    private var _tracks: MutableLiveData<List<Track>> = MutableLiveData()
    val tracks: LiveData<List<Track>> = _tracks

    private var _playsCount: MutableLiveData<Int> = MutableLiveData()
    val playsCount: LiveData<Int> = _playsCount

    private var _likesCount: MutableLiveData<Int> = MutableLiveData()
    val likesCount: LiveData<Int> = _likesCount

    private var _artistImagePath: MutableLiveData<String> = MutableLiveData()
    val artistImagePath: LiveData<String> = _artistImagePath

    companion object {
        const val REQUEST_ALBUM_INFO_TAG = "requestAlbumInfo"
    }

    override fun onRefresh() {
        requestAlbumInfo()
    }

    private fun requestAlbumInfo() {
        viewModelScope.launch {
            startLoading(REQUEST_ALBUM_INFO_TAG)
            try {
                withContext(Dispatchers.Default) {
                    val response = vinylsRepository.getAlbumInfo(albumName, artistName)
                    withContext(Dispatchers.Main) {
                        onAlbumInfoResponse(response)
                    }
                }
            } catch (throwable: Throwable) {
                _error.postValue(throwable.toString())
            } finally {
                endLoading(REQUEST_ALBUM_INFO_TAG)
            }
        }
    }

    private fun onAlbumInfoResponse(response: AlbumInfoResponse) {
        _playsCount.postValue(response.album.playcount)
        _likesCount.postValue(response.album.listeners)

        _artistImagePath.postValue(response.album.image.last().path)

        response.album.tags?.let {
            _tags.postValue(it.tag)
        }

        // if the album doesn't have any tracks, it's probably a single
        // so create one track and name it by the album's name
        if (response.album.tracks == null) {
            // UNSAFE TRACK CREATION:
            // the only filled fields: albumName, artistName, trackRank
            // do not use in your tracks layout created dummy fields: duration, url, artist's url, artist's mbid, streamable
            val single =
                Track(
                    Track.Streamable("", ""),
                    0,
                    "",
                    response.album.name,
                    Track.Attribute(1),
                    Track.Artist("", response.album.artist, "")
                )
            _tracks.postValue(listOf(single))
        } else {
            _tracks.postValue(response.album.tracks!!.track)
        }
    }

}

class AlbumViewModelFactory(
    private val vinylsRepository: VinylsRepository,
    private val albumName: String,
    private val artistName: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(AlbumViewModel::class.java))
            return AlbumViewModel(vinylsRepository, albumName, artistName) as T
        else
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}