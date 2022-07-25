package com.android.vinylstore.data.repository.data_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.data.lastfm_api.AlbumsApiService
import com.android.vinylstore.data.lastfm_api.classes.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val STARTING_KEY = 1

class SearchArtistsPagingSource(private val albumsApiService: AlbumsApiService, private val query: String) :
    PagingSource<Int, Artist>() {

    companion object {
        const val FETCHING_SIZE = 50
        private const val TAG = "SearchArtistsPagingSource"
    }

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        return ensureValidKey(anchorPosition / state.config.pageSize)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val start = params.key ?: STARTING_KEY

        val call = albumsApiService.searchArtists(
            BuildConfig.LASTFM_API_KEY,
            query,
            page = start,
            limit = FETCHING_SIZE
        )
        Log.d(TAG, "call url: ${call.request().url()}")

        try {
            val response = withContext(Dispatchers.Default) { call.execute() }
            response.body()!!.results.let {
                Log.d(TAG, "response: prompted page: $start | got page: ${it.searchQuery.startPage}")
                Log.d(TAG, "response: real size: ${it.artistsMatches.artists.size} | attr size: ${it.itemsPerPage}")
                Log.d(TAG, "response: total results: ${it.totalResults}")

                return LoadResult.Page(
                    // take last 50 because this api does something crazy and return more elements than you prompted sometimes
                    // and these elements are taken from previous pages, so just cut them
                    data = it.artistsMatches.artists.takeLast(FETCHING_SIZE),
                    prevKey = when (start) {
                        STARTING_KEY -> null
                        else -> ensureValidKey(start - 1)
                    },
                    nextKey = if (it.artistsMatches.artists.size >= FETCHING_SIZE) start + 1 else null
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