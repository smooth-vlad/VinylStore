package com.android.vinylstore.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.android.vinylstore.R
import com.android.vinylstore.activity.MainActivity.Companion.lastFmApi
import com.android.vinylstore.adapters.TrackItemAdapter
import com.android.vinylstore.adapters.VinylPagerAdapter
import com.android.vinylstore.databinding.ActivityAlbumBinding
import com.android.vinylstore.lastfm_api.classes.Artist
import com.android.vinylstore.lastfm_api.classes.Tag
import com.android.vinylstore.lastfm_api.classes.Track
import com.android.vinylstore.lastfm_api.responses.AlbumInfoResponse
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


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

        val vinylViewPagerAdapter = VinylPagerAdapter(this)
        binding.vinylVp.setPageTransformer(
            StackedPageTransformer(
                binding.vinylVp,
                resources.displayMetrics.density
            )
        )
        binding.vinylVp.adapter = vinylViewPagerAdapter

        binding.albumIv.setImageResource(R.drawable.image_placeholder)

        binding.tracksRv.layoutManager = object : LinearLayoutManager(this){ override fun canScrollVertically(): Boolean { return false } }
        binding.tracksRv.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

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
                Log.e("AlbumActivity", throwable.toString())
            }
        })
    }

    private fun onAlbumInfoResponse(response: Response<AlbumInfoResponse?>) {
        response.body()?.let {
            binding.albumNameTv.text = it.album.name
            binding.playsValueTv.text = formatNumber(it.album.playcount.toString())
            binding.releaseValueTv.text = formatNumber(it.album.listeners.toString())

            val url = it.album.image.last().path
            if (url.isNotEmpty()) {
                Picasso.with(this)
                    .load(url)
                    .placeholder(R.drawable.image_placeholder)
                    .into(binding.albumIv)
            } else {
                binding.albumIv.setImageResource(R.drawable.image_placeholder)
            }

            it.album.tags?.let { tags ->
                inflateTags(tags.tag)
            }

            if (it.album.tracks == null) {
                // UNSAFE TRACK CREATION:
                // the only filled fields: albumName, artistName, trackRank
                // do not use in your tracks layout created dummy fields: duration, url, artist's url, artist's mbid, streamable
                val single =
                    Track(
                        Track.Streamable("", ""),
                        0,
                        "",
                        it.album.name,
                        Track.Attribute(1),
                        Track.Artist("", it.album.artist, "")
                    )
                binding.tracksRv.adapter = TrackItemAdapter(listOf(single))
            } else {
                binding.tracksRv.adapter = TrackItemAdapter(it.album.tracks!!.track)
            }

            hidePlaceHolderUi()
            showMainLayout()
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

    private fun showMainLayout() {
        binding.activityAlbumLayout.visibility = View.VISIBLE
    }

    private fun hidePlaceHolderUi() {
        binding.activityAlbumShimmer.apply {
            this.stopShimmer()
            this.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    class StackedPageTransformer(private val viewPager: View, private val density: Float) :
        ViewPager2.PageTransformer {

        // vinyl image is 200 dp wide, so 100 is a half
        private val halfVinylImage = 100F * density
        private val halfVinylImageOffset get() = halfVinylImage / viewPager.width

        override fun transformPage(page: View, position: Float) {
            if (position <= -1f) {    // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.alpha = 0F;
                page.translationZ = 0.0f

            } else if (position <= 0f) {    // [-1,0]
                if (position <= -halfVinylImageOffset) {
                    page.alpha = 0F;
                    page.translationX = 0F;
                    page.translationZ = 0.0f
                } else {
                    page.alpha = 1F;
                    page.translationX = 0F;
                    page.translationZ = 0.0f
                }
            } else if (position < 1) {    // (0,1]
                page.translationX = -position * page.width;
                page.alpha = 1f;
                page.translationZ = -0.01f
            } else {    // (1,+Infinity]
                // This page is way off-screen to the right.
                page.alpha = 0F;
                page.translationZ = 0.0f
            }
        }
    }
}