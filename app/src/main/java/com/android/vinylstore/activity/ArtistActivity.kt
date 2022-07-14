package com.android.vinylstore.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.BuildConfig
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

    private var topAlbumsResponseSuccess = false
    private var artistInfoResponseSuccess = false

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

        binding.activityArtistShimmer.startShimmer()

        tagsChipGroup = binding.tagsCg

        setSupportActionBar(findViewById(R.id.my_toolbar))
        title = artistName

        requestTopAlbums()
        requestArtistInfo()
    }

    private fun requestTopAlbums() {
        val call = lastFmApi.albumsApiService.getTopAlbums(apiKey = BuildConfig.LASTFM_API_KEY, artist = artistName!!)

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
                Toast.makeText(this@ArtistActivity, getString(R.string.connection_failure), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun requestArtistInfo() {
        val call = lastFmApi.albumsApiService.getInfoArtist(apiKey = BuildConfig.LASTFM_API_KEY, artist = artistName)

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
                Toast.makeText(this@ArtistActivity, getString(R.string.connection_failure), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onTopAlbumsResponse(response: Response<TopAlbumsResponse?>) {
        response.body()?.let {
            topAlbumsResponseSuccess = true
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

            if (artistInfoResponseSuccess && topAlbumsResponseSuccess) {
                hidePlaceHolderUi()
                showMainLayout()
            }
        }
    }

    private fun onArtistInfoResponse(response: Response<ArtistInfoResponse?>) {
        response.body()?.let {
            artistInfoResponseSuccess = true
            inflateTags(it.artist.tags.tag)
            if (artistInfoResponseSuccess && topAlbumsResponseSuccess) {
                hidePlaceHolderUi()
                showMainLayout()
            }
        }
    }

    private fun inflateTags(tags: List<Tag>) {
        for (tag in tags) {
            val chip = Chip(this)
            chip.text = tag.name
            tagsChipGroup.addView(chip)
        }
    }

    private fun showMainLayout() {
        binding.activityArtistLayout.visibility = View.VISIBLE
    }

    private fun hidePlaceHolderUi() {
        binding.activityArtistShimmer.apply {
            this.stopShimmer()
            this.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}