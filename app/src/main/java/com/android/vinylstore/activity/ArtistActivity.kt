package com.android.vinylstore.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.R
import com.android.vinylstore.activity.MainActivity.Companion.lastFmApi
import com.android.vinylstore.adapters.AlbumItemAdapter
import com.android.vinylstore.databinding.ActivityArtistBinding
import com.android.vinylstore.lastfm_api.classes.Tag
import com.android.vinylstore.lastfm_api.responses.ArtistInfoResponse
import com.android.vinylstore.lastfm_api.responses.TopAlbumsResponse
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArtistActivity : AppCompatActivity() {
    private var _binding: ActivityArtistBinding? = null
    val binding get() = _binding!!

    private lateinit var artistName: String

    private lateinit var artistImageIv: ImageView
    private lateinit var albumsRv: RecyclerView
    private lateinit var tagsChipGroup: ChipGroup

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

        tagsChipGroup = binding.tagsCg

        setSupportActionBar(findViewById(R.id.my_toolbar))
        title = artistName

        requestTopAlbums()
        requestArtistInfo()
    }

    private fun requestTopAlbums() {
        val call = lastFmApi.albumsApiService.getTopAlbums(apiKey = lastFmApi.apiKey, artist = artistName!!)

        Log.d("ArtistActivity", call.request().url().toString())

        call.enqueue(object : Callback<TopAlbumsResponse> {
            override fun onResponse(
                call: Call<TopAlbumsResponse?>?,
                response: Response<TopAlbumsResponse?>
            ) {
                onTopAlbumsResponse(response)
            }

            override fun onFailure(call: Call<TopAlbumsResponse?>?, throwable: Throwable) {
                Log.e("ArtistActivity", throwable.toString())
            }
        })
    }

    private fun requestArtistInfo() {
        val call = lastFmApi.albumsApiService.getInfoArtist(apiKey = lastFmApi.apiKey, artist = artistName)

        Log.d("ArtistActivity", call.request().url().toString())

        call.enqueue(object : Callback<ArtistInfoResponse> {
            override fun onResponse(
                call: Call<ArtistInfoResponse?>?,
                response: Response<ArtistInfoResponse?>
            ) {
                onArtistInfoResponse(response)
            }

            override fun onFailure(call: Call<ArtistInfoResponse?>?, throwable: Throwable) {
                Log.e("ArtistActivity", throwable.toString())
            }
        })
    }

    private fun onTopAlbumsResponse(response: Response<TopAlbumsResponse?>) {
        response.body()?.let {
            albumsRv.adapter = AlbumItemAdapter(it.topAlbums.album.filterNot { album -> album.name == "(null)" })
            Log.d("ArtistActivity", "Number of albums found: " + it.topAlbums.attr.total)
            val topAlbum = it.topAlbums.album[0]
            val url = topAlbum.image.last().path
            if (url.isNotEmpty()) {
                Picasso.with(this@ArtistActivity)
                    .load(url)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.ic_baseline_image_not_supported)
                    .into(artistImageIv)
            } else {
                binding.artistImageIv.setImageResource(R.drawable.image_placeholder)
            }
        }
    }

    private fun onArtistInfoResponse(response: Response<ArtistInfoResponse?>) {
        response.body()?.let {
            inflateTags(it.artist.tags.tag)
        }
    }

    private fun inflateTags(tags: List<Tag>) {
        for (tag in tags) {
            val chip = Chip(this)
            chip.text = tag.name
            tagsChipGroup.addView(chip)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}