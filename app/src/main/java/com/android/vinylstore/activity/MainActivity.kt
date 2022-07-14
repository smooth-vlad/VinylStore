package com.android.vinylstore.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.BuildConfig
import com.android.vinylstore.R
import com.android.vinylstore.Root
import com.android.vinylstore.adapters.ArtistItemAdapter
import com.android.vinylstore.databinding.ActivityMainBinding
import com.android.vinylstore.di.AppComponent
import com.android.vinylstore.lastfm_api.LastFmApi
import com.android.vinylstore.lastfm_api.responses.TopArtistResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var vinylRecyclerView: RecyclerView
    private lateinit var search: MenuItem

    private var _isWaitingForResponse = false
    var isWaitingForResponse
        get() = _isWaitingForResponse
        set(value) {
            _isWaitingForResponse = value
            if (!value && binding.artistsSwipeRefresh.isRefreshing)
                binding.artistsSwipeRefresh.isRefreshing = false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vinylRecyclerView = binding.vinylRecyclerView
        vinylRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.artistsSwipeRefresh.setOnRefreshListener {
            if (isWaitingForResponse) {
                Toast.makeText(
                    this,
                    getString(R.string.refresh_on_loading_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            } else
                requestTopArtists()
        }

        binding.activityMainShimmer.startShimmer()

        setSupportActionBar(binding.myToolbarMain)

        requestTopArtists()

        // start Album Activity (for optimizing dev process purposes only)

//        val intent = Intent(this, AlbumActivity::class.java)
//        val bundle = Bundle().apply {
//            putString(AlbumActivity.ALBUM_NAME, "Gimme Love")
//            putString(AlbumActivity.ARTIST_NAME, "Joji")
//        }
//        intent.putExtras(bundle)
//        ContextCompat.startActivity(this, intent, null)
    }

    private fun requestTopArtists() {
        val call = Root.getAppComponent(this).getLastFmApi().albumsApiService.getTopArtists(BuildConfig.LASTFM_API_KEY, limit = 100)

        Log.d("MainActivity", call.request().url().toString())

        isWaitingForResponse = true
        call.enqueue(object : Callback<TopArtistResponse> {
            override fun onResponse(
                call: Call<TopArtistResponse?>?,
                response: Response<TopArtistResponse?>
            ) {
                isWaitingForResponse = false
                onTopArtistsResponse(response)
            }

            override fun onFailure(call: Call<TopArtistResponse?>?, throwable: Throwable) {
                Log.e("MainActivity", throwable.toString())
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.connection_failure),
                    Toast.LENGTH_SHORT
                ).show()
                isWaitingForResponse = false
            }
        })
    }

    private fun onTopArtistsResponse(response: Response<TopArtistResponse?>) {
        response.body()?.let {
            hidePlaceHolderUi()
            unlockSearchMenuItem()
            val adapter = ArtistItemAdapter(it.artists.artist)
            vinylRecyclerView.adapter = adapter
            Log.d("MainActivity", "Number of artists found: " + it.artists.attr.total)
        }
    }

    private fun unlockSearchMenuItem() {
        search.isEnabled = true
    }

    private fun hidePlaceHolderUi() {
        binding.activityMainShimmer.apply {
            this.stopShimmer()
            this.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        search = menu!!.findItem(R.id.action_search)
        search.isEnabled = false
        val searchView = search.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val adapter = (vinylRecyclerView.adapter as ArtistItemAdapter)
                adapter.filter.filter(newText)
                Log.d("MainActivity", "queryTextChange")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}