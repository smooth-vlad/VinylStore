package com.android.vinylstore.presentation.artist.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.vinylstore.R
import com.android.vinylstore.Root
import com.android.vinylstore.adapters.AlbumItemAdapter
import com.android.vinylstore.databinding.ActivityArtistBinding
import com.android.vinylstore.lastfm_api.classes.Tag
import com.android.vinylstore.presentation.artist.viewmodel.ArtistViewModel
import com.android.vinylstore.presentation.artist.viewmodel.ArtistViewModelFactory
import com.google.android.material.chip.Chip
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ArtistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtistBinding

    private lateinit var viewModel: ArtistViewModel

    private lateinit var artistName: String

    companion object {
        const val ARTIST_NAME = "ArtistActivity.ARTIST_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        artistName = intent.extras?.getString(ARTIST_NAME).toString()

        binding = ActivityArtistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ArtistViewModelFactory(Root.getAppComponent(this).getAlbumsApiService(), artistName)
        )[ArtistViewModel::class.java]

        viewModel.error.observe(this) {
            Log.e("ArtistActivity", it)
            Toast.makeText(
                this@ArtistActivity,
                getString(R.string.connection_failure),
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.tags.observe(this) {
            inflateTags(it)
            removeShimmerTags()
        }
        viewModel.topAlbums.observe(this) {
            binding.albumsRv.adapter =
                AlbumItemAdapter(it.filterNot { album -> album.name == "(null)" })
            removeShimmerAlbumsImage()
            Log.d("ArtistActivity", "Number of albums found: " + it.size)
        }
        viewModel.artistImagePath.observe(this) {
            if (it.isNotEmpty()) {
                Picasso.with(this@ArtistActivity)
                    .load(it)
                    .into(binding.artistImageIv, object : Callback {
                        override fun onSuccess() {
                            removeShimmerArtistImage()
                        }

                        override fun onError() {
                            binding.artistImageIv.setImageResource(R.drawable.image_placeholder)
                            removeShimmerArtistImage()
                        }
                    })
            } else {
                binding.artistImageIv.setImageResource(R.drawable.image_placeholder)
            }
        }
        viewModel.isDataLoading.observe(this) {
            if (!it && binding.artistSwipeRefresh.isRefreshing)
                binding.artistSwipeRefresh.isRefreshing = false
        }

        binding.artistSwipeRefresh.setOnRefreshListener {
            if (!viewModel.refresh()) {
                Toast.makeText(
                    this,
                    getString(R.string.refresh_on_loading_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        setSupportActionBar(findViewById(R.id.my_toolbar))
        title = artistName

        viewModel.refresh()
    }

    private fun inflateTags(tags: List<Tag>) {
        val chipGroup = binding.tagsCg
        chipGroup.removeAllViews()
        for (tag in tags) {
            val chip = Chip(this)
            chip.text = tag.name
            chipGroup.addView(chip)
        }
    }

    private fun removeShimmerArtistImage() {
        binding.artistImageShimmer.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.artistImageIv.visibility = View.VISIBLE
    }

    private fun removeShimmerTags() {
        binding.tagsShimmer.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.tagsCg.visibility = View.VISIBLE
    }

    private fun removeShimmerAlbumsImage() {
        binding.albumsShimmer.apply {
            stopShimmer()
            visibility = View.GONE
        }
        binding.albumsRv.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}