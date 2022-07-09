package com.android.vinylstore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.vinylstore.activity.MainActivity.Companion.lastFmApi
import android.view.MenuItem
import com.android.vinylstore.R
import com.android.vinylstore.databinding.ActivityAlbumBinding
import com.android.vinylstore.databinding.AlbumItemBinding
import com.android.vinylstore.lastfm_api.classes.Tag
import com.android.vinylstore.lastfm_api.responses.AlbumInfoResponse
import com.android.vinylstore.lastfm_api.responses.ArtistInfoResponse
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumActivity : AppCompatActivity() {
    private var _binding: ActivityAlbumBinding? = null
    private val binding get() = _binding!!

    private lateinit var albumName: String
    private lateinit var artistName: String

    companion object {
        const val ALBUM_NAME = "AlbumActivity.ALBUM_NAME"
        const val ARTIST_NAME = "AlbumActivity.ARTIST_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        albumName = intent.extras?.getString(ALBUM_NAME).toString()
        artistName = intent.extras?.getString(ARTIST_NAME).toString()

        setSupportActionBar(binding.albumScreenToolbar)
        title = "$artistName - $albumName"

        requestAlbumInfo()
    }

    private fun requestAlbumInfo() {
        val call = lastFmApi.albumsApiService.getInfoAlbum(lastFmApi.apiKey, artistName, albumName)

        Log.d("AlbumActivity", call.request().url().toString())

        call.enqueue(object : Callback<AlbumInfoResponse> {
            override fun onResponse(
                call: Call<AlbumInfoResponse?>?,
                response: Response<AlbumInfoResponse?>
            ) {
                onAlbumInfoResponse(response)
            }

            override fun onFailure(call: Call<AlbumInfoResponse?>?, throwable: Throwable) {
                Log.e("ArtistActivity", throwable.toString())
            }
        })
    }

    private fun onAlbumInfoResponse(response: Response<AlbumInfoResponse?>) {
        response.body()?.let {
            binding.albumNameTv.text = it.album.name
            binding.playsValueTv.text = formatNumber(it.album.playcount.toString())
            binding.releaseValueTv.text = formatNumber(it.album.listeners.toString())
            inflateTags(it.album.tags.tag)
        }
    }

    private fun inflateTags(tags: List<Tag>) {
        val chipGroup = binding.tagsCg
        for (tag in tags) {
            val chip = Chip(this)
            chip.text = tag.name
            chipGroup.addView(chip)
        }
    }

    // Example: 2612351 -> 2 612 351
    private fun formatNumber(src: String): String {
        val result = StringBuilder()
        src.reversed().forEachIndexed { index, c ->
            if (index > 0 && index % 3 == 0)
                result.append(' ')
            result.append(c)
        }
        return result.reversed().toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}