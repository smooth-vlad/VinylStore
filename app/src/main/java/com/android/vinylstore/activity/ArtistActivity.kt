package com.android.vinylstore.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.R
import com.android.vinylstore.activity.MainActivity.Companion.lastFmApi
import com.android.vinylstore.adapters.AlbumItemAdapter
import com.android.vinylstore.databinding.ActivityArtistBinding
import com.android.vinylstore.lastfm_api.interfaces.AlbumsApiService
import com.android.vinylstore.lastfm_api.responses.TopAlbumsResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistActivity : AppCompatActivity() {
    private var _binding: ActivityArtistBinding? = null
    val binding get() = _binding!!

    private var artistName: String? = null

    private lateinit var artistImageIv: ImageView
    private lateinit var albumsRv: RecyclerView

    companion object {
        const val ARTIST_NAME = "ArtistActivity.ARTIST_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        artistImageIv = binding.artistImageIv
        artistName = intent.extras?.getString(ARTIST_NAME).toString()

        albumsRv = binding.albumsRv

        setSupportActionBar(findViewById(R.id.my_toolbar))
        title = artistName

        connectAndGetTopAlbums()
    }

    private fun connectAndGetTopAlbums() {
        val albumsApiService = lastFmApi.retrofit.create(AlbumsApiService::class.java)

        val call = albumsApiService.getTopAlbums(apiKey = lastFmApi.apiKey, artist = artistName!!)

        Log.d("ArtistActivity", call.request().url().toString())

        call.enqueue(object : Callback<TopAlbumsResponse> {
            override fun onResponse(
                call: Call<TopAlbumsResponse?>?,
                response: Response<TopAlbumsResponse?>
            ) {
                response.body()?.let {
                    albumsRv.adapter = AlbumItemAdapter(it.topAlbums.album)
                    Log.d("ArtistActivity", "Number of albums found: " + it.topAlbums.attr.total)
                    val topAlbum = it.topAlbums.album[0]
                    val url = topAlbum.image.last().path
                    Picasso.with(this@ArtistActivity)
                        .load(url)
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.ic_baseline_image_not_supported)
                        .into(artistImageIv)
                }
            }

            override fun onFailure(call: Call<TopAlbumsResponse?>?, throwable: Throwable) {
                Log.e("ArtistActivity", throwable.toString())
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}