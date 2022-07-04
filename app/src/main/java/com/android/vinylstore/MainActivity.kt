package com.android.vinylstore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.adapters.ArtistItemAdapter
import com.android.vinylstore.databinding.ActivityMainBinding
import com.android.vinylstore.lastfm_api.AlbumsApiService
import com.android.vinylstore.lastfm_api.TopArtistResponse
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
        private const val BASE_URL = "https://ws.audioscrobbler.com/"
        private var retrofit: Retrofit? = null
        private const val API_KEY = "35a05fb4101e2310860399151c60f746"
        // http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=%22arctic%20m%22&api_key=35a05fb4101e2310860399151c60f746&format=json
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vinylRecyclerView = binding.vinylRecyclerView

        connectAndGetApiData()
    }

    fun connectAndGetApiData() {
        if (retrofit == null) {

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val albumsApiService = retrofit!!.create(AlbumsApiService::class.java)

        val call = albumsApiService.getTopArtists(apiKey = API_KEY, page = 1)

        Log.d("MainActivity:Response", call.request().url().toString())

        call.enqueue(object : Callback<TopArtistResponse> {
            override fun onResponse(call: Call<TopArtistResponse?>?, response: Response<TopArtistResponse?>) {
//                val artists: List<Artist>? = response.body()?.artistMatches?.artist
//                recyclerView.setAdapter(
//                    MoviesAdapter(
//                        movies,
//                        R.layout.list_item_movie,
//                        applicationContext
//                    )
//                )
                response.body()?.let {
                    vinylRecyclerView.adapter = ArtistItemAdapter(it.artists.artist)
                }
                val a = response.body()?.artists?.artist?.get(0)
                Log.d("MainActivity:Response", "Number of artists found: " + a?.name)
            }

            override fun onFailure(call: Call<TopArtistResponse?>?, throwable: Throwable) {
                Log.e("MainActivity:Response", throwable.toString())
            }
        })
    }
}