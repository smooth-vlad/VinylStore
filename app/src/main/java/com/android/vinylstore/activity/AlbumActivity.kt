package com.android.vinylstore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.android.vinylstore.R
import com.android.vinylstore.databinding.ActivityAlbumBinding
import com.android.vinylstore.databinding.AlbumItemBinding

class AlbumActivity : AppCompatActivity() {
    private var _binding: ActivityAlbumBinding? = null
    private val binding get() = _binding!!

    private var albumName: String? = null
    private var artistName: String? = null

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}