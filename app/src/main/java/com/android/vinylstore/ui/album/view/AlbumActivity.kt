package com.android.vinylstore.ui.album.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.vinylstore.R
import com.android.vinylstore.Root
import com.android.vinylstore.ui.adapters.TrackItemAdapter
import com.android.vinylstore.ui.adapters.VinylPagerAdapter
import com.android.vinylstore.databinding.ActivityAlbumBinding
import com.android.vinylstore.data.lastfm_api.classes.Tag
import com.android.vinylstore.ui.album.transformer.VinylPageTransformer
import com.android.vinylstore.ui.album.viewmodel.AlbumViewModel
import com.android.vinylstore.ui.album.viewmodel.AlbumViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.chip.Chip
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class AlbumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlbumBinding

    private lateinit var viewModel: AlbumViewModel

    companion object {
        const val ALBUM_NAME = "AlbumActivity.ALBUM_NAME"
        const val ARTIST_NAME = "AlbumActivity.ARTIST_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val albumName = intent.extras?.getString(ALBUM_NAME).toString()
        val artistName = intent.extras?.getString(ARTIST_NAME).toString()

        binding = ActivityAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.albumNameTv.text = albumName

        viewModel = ViewModelProvider(
            this,
            AlbumViewModelFactory(
                Root.getAppComponent(this).getAlbumsApiService(),
                albumName,
                artistName
            )
        )[AlbumViewModel::class.java]

        viewModel.error.observe(this) {
            Log.e("AlbumActivity", it)
            Toast.makeText(
                this@AlbumActivity,
                getString(R.string.connection_failure),
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.tags.observe(this) {
            inflateTags(it)
            removeShimmer(binding.tagsShimmer, binding.tagsCg)
        }
        viewModel.playsCount.observe(this) {
            binding.playsValueTv.text = formatNumber(it.toString())
            removeShimmer(binding.playsValueShimmer, binding.playsValueTv)
        }
        viewModel.likesCount.observe(this) {
            binding.likesValueTv.text = formatNumber(it.toString())
            removeShimmer(binding.likesValueShimmer, binding.likesValueTv)
        }
        viewModel.artistImagePath.observe(this) {
            if (it.isNotEmpty()) {
                Picasso.with(this@AlbumActivity)
                    .load(it)
                    .into(binding.albumIv, object : Callback {
                        override fun onSuccess() {
                            removeShimmer(binding.vinylShowcaseShimmer, binding.vinylShowcase)
                        }

                        override fun onError() {
                            binding.albumIv.setImageResource(R.drawable.image_placeholder)
                            removeShimmer(binding.vinylShowcaseShimmer, binding.vinylShowcase)
                        }
                    })
            } else {
                binding.albumIv.setImageResource(R.drawable.image_placeholder)
            }
        }
        viewModel.tracks.observe(this) {
            binding.tracksRv.adapter = TrackItemAdapter(it)
            removeShimmer(binding.tracksShimmer, binding.tracksRv)
        }
        viewModel.isDataLoading.observe(this) {
            if (!it && binding.albumSwipeRefresh.isRefreshing)
                binding.albumSwipeRefresh.isRefreshing = false
        }

        val vinylViewPagerAdapter = VinylPagerAdapter(this)
        binding.vinylVp.setPageTransformer(
            VinylPageTransformer(resources.displayMetrics.density)
        )
        binding.vinylVp.adapter = vinylViewPagerAdapter

        binding.albumIv.setImageResource(R.drawable.image_placeholder)

        binding.tracksRv.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.tracksRv.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.albumSwipeRefresh.setOnRefreshListener {
            if (!viewModel.refresh()) {
                Toast.makeText(
                    this,
                    getString(R.string.refresh_on_loading_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        setSupportActionBar(binding.albumScreenToolbar)
        title = "$artistName - $albumName"

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

    private fun removeShimmer(shimmer: ShimmerFrameLayout, viewToShow: View) {
        if (shimmer.visibility != View.GONE) {
            shimmer.apply {
                stopShimmer()
                visibility = View.GONE
            }
        }
        viewToShow.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}