package com.android.vinylstore.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.adapters.ArtistItemAdapter
import com.android.vinylstore.databinding.ActivityMainBinding
import com.android.vinylstore.lastfm_api.LastFmApi
import com.android.vinylstore.lastfm_api.interfaces.AlbumsApiService
import com.android.vinylstore.lastfm_api.responses.TopArtistResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

        connectAndGetApiData()
    }

    private fun connectAndGetApiData() {

        val albumsApiService = lastFmApi.retrofit.create(AlbumsApiService::class.java)

        val call = albumsApiService.getTopArtists(apiKey = lastFmApi.apiKey)

        Log.d("MainActivity", call.request().url().toString())

        call.enqueue(object : Callback<TopArtistResponse> {
            override fun onResponse(call: Call<TopArtistResponse?>?, response: Response<TopArtistResponse?>) {
                response.body()?.let {
                    vinylRecyclerView.adapter = ArtistItemAdapter(it.artists.artist)
                    Log.d("MainActivity", "Number of artists found: " + it.artists.attr.total)
                }
            }

            override fun onFailure(call: Call<TopArtistResponse?>?, throwable: Throwable) {
                Log.e("MainActivity", throwable.toString())
            }
        })
    }
}