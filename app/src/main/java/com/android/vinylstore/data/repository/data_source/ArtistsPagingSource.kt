package com.android.vinylstore.data.repository.data_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.Root
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.lastfm_api.classes.Artist
import com.android.vinylstore.data.lastfm_api.responses.TopArtistResponse
import com.android.vinylstore.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.max
import javax.inject.Inject

private const val STARTING_KEY = 1

class ArtistsPagingSource(private val albumsApiService: AlbumsApiService) :
    PagingSource<Int, Artist>() {

    companion object {
        const val FETCHING_SIZE = 50
        private const val TAG = "ArtistsPagingSource"
    }

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        return ensureValidKey(anchorPosition / state.config.pageSize)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val start = params.key ?: STARTING_KEY

        val call = albumsApiService.getTopArtists(
            BuildConfig.LASTFM_API_KEY,
            page = start,
            limit = FETCHING_SIZE
        )
        Log.d(TAG, "call url: ${call.request().url()}")

        try {
            val response = withContext(Dispatchers.Default) { call.execute() }
            response.body().artists.let {
                Log.d(TAG, "response: prompted page: $start | got page: ${it.attr.page}")
                Log.d(TAG, "response: real size: ${it.artist.size} | attr size: ${it.attr.perPage}")

                return LoadResult.Page(
                    // take last 50 because this api does something crazy and return more elements than you prompted sometimes
                    // and these elements are taken from previous pages, so just cut them
                    data = it.artist.takeLast(FETCHING_SIZE),
                    prevKey = when (start) {
                        STARTING_KEY -> null
                        else -> ensureValidKey(start - 1)
                    },
                    nextKey = start + 1
                )
            }
        } catch (throwable: Throwable) {
            Log.d(TAG, "$throwable")
            return LoadResult.Error(throwable)
        }

    }

    /**
     * Makes sure the paging key is never less than [STARTING_KEY]
     */
    private fun ensureValidKey(key: Int) = key.coerceAtLeast(STARTING_KEY)
}