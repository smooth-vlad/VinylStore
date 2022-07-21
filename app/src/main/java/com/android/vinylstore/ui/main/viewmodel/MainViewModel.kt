package com.android.vinylstore.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.*
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.lastfm_api.classes.Artist
import com.android.vinylstore.data.lastfm_api.responses.TopArtistResponse
import com.android.vinylstore.data.repository.VinylsRepository
import com.android.vinylstore.data.repository.data_source.ArtistsPagingSource
import com.android.vinylstore.ui.RefreshingViewModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(private val vinylsRepository: VinylsRepository) : RefreshingViewModel() {

//    private var _error: MutableLiveData<String> = MutableLiveData()
//    val error: LiveData<String> = _error
//
//    private var _artists: MutableLiveData<List<Artist>> = MutableLiveData()
//    val artists: LiveData<List<Artist>> = _artists

    companion object {
        const val REQUEST_TOP_ARTISTS_TAG = "requestTopArtists"
    }

    val items: Flow<PagingData<Artist>> = Pager(
        config = PagingConfig(ArtistsPagingSource.FETCHING_SIZE, enablePlaceholders = false),
        pagingSourceFactory = {vinylsRepository.artistsPagingSource()}
    )
        .flow
        .cachedIn(viewModelScope)

//    private fun requestTopArtists(page: Int, createNewList: Boolean = false) {
//        val call = api.getTopArtists(BuildConfig.LASTFM_API_KEY, page = page)
//        Log.d("ABC", "${call.request().url()}")
//
//        startLoading(REQUEST_TOP_ARTISTS_TAG)
//        call.enqueue(object : Callback<TopArtistResponse> {
//            override fun onResponse(
//                call: Call<TopArtistResponse?>?,
//                response: Response<TopArtistResponse?>
//            ) {
//                endLoading(REQUEST_TOP_ARTISTS_TAG)
//                onTopArtistsResponse(response, createNewList)
//            }
//
//            override fun onFailure(call: Call<TopArtistResponse?>?, throwable: Throwable) {
//                endLoading(REQUEST_TOP_ARTISTS_TAG)
//                _error.value = throwable.localizedMessage
//            }
//        })
//    }

//    private fun onTopArtistsResponse(
//        response: Response<TopArtistResponse?>,
//        createNewList: Boolean
//    ) {
//        response.body()?.let { src ->
//            val newData = src.artists.artist.let {
//                 it.slice(it.size - (requestLimit) until it.size)
//            }
//            if (artists.value == null || createNewList) {
//                _artists.value = newData
//            } else {
//                _artists.value = _artists.value!! + newData
//            }
//        }
//    }

    override fun onRefresh() {
//        requestTopArtists(1, true)
    }
}