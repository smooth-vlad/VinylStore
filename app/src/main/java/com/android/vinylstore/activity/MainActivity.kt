package com.android.vinylstore.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.R
import com.android.vinylstore.adapters.ArtistItemAdapter
import com.android.vinylstore.databinding.ActivityMainBinding
import com.android.vinylstore.lastfm_api.LastFmApi
import com.android.vinylstore.lastfm_api.responses.TopArtistResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var vinylRecyclerView: RecyclerView

    companion object {
        val lastFmApi = LastFmApi("35a05fb4101e2310860399151c60f746")
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

        setSupportActionBar(binding.myToolbarMain)

        requestTopArtists()

        // start Album Activity (for optimizing dev process purposes only)

        val intent = Intent(this, AlbumActivity::class.java)
        val bundle = Bundle().apply {
            putString(AlbumActivity.ALBUM_NAME, "Gimme Love")
            putString(AlbumActivity.ARTIST_NAME, "Joji")
        }
        intent.putExtras(bundle)
        ContextCompat.startActivity(this, intent, null)
    }

    private fun requestTopArtists() {
        val call = lastFmApi.albumsApiService.getTopArtists(apiKey = lastFmApi.apiKey)

        Log.d("MainActivity", call.request().url().toString())

        call.enqueue(object : Callback<TopArtistResponse> {
            override fun onResponse(
                call: Call<TopArtistResponse?>?,
                response: Response<TopArtistResponse?>
            ) {
                onTopArtistsResponse(response)
            }

            override fun onFailure(call: Call<TopArtistResponse?>?, throwable: Throwable) {
                Log.e("MainActivity", throwable.toString())
            }
        })
    }

    private fun onTopArtistsResponse(response: Response<TopArtistResponse?>) {
        response.body()?.let {
            val adapter = ArtistItemAdapter(it.artists.artist)
            vinylRecyclerView.adapter = adapter
            Log.d("MainActivity", "Number of artists found: " + it.artists.attr.total)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        val search = menu!!.findItem(R.id.action_search)
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