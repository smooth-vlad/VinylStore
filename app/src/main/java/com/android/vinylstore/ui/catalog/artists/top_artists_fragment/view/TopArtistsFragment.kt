package com.android.vinylstore.ui.catalog.artists.top_artists_fragment.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.vinylstore.Root
import com.android.vinylstore.databinding.FragmentTopArtistsBinding
import com.android.vinylstore.ui.catalog.artists.adapter.ArtistItemAdapter
import com.android.vinylstore.ui.catalog.artists.top_artists_fragment.viewmodel.TopArtistsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopArtistsFragment : Fragment() {

    private lateinit var binding: FragmentTopArtistsBinding

    lateinit var viewModel: TopArtistsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = Root.getAppComponent(requireContext())
        val activityMainComponent = appComponent.getActivityMainComponent()
        viewModel = ViewModelProvider(
            this,
            activityMainComponent.getTopArtistsViewModelFactory()
        )[TopArtistsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopArtistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = viewModel.items
        val artistItemAdapter = ArtistItemAdapter()
        binding.topArtistsRecyclerView.adapter = artistItemAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    artistItemAdapter.submitData(it)
                }
            }
        }

        artistItemAdapter.addOnPagesUpdatedListener(fun() {
            Log.d(TAG, "OnPagesUpdated")
            binding.topArtistsSwipeRefreshLayout.apply {
                if (isRefreshing)
                    isRefreshing = false
            }
            if (binding.topArtistsShimmer.isShimmerVisible)
                hideShimmer()
        })

        binding.topArtistsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.topArtistsSwipeRefreshLayout.setOnRefreshListener {
            artistItemAdapter.refresh()
            lifecycleScope.launch {
                delay(750)
                binding.topArtistsSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun hideShimmer() {
        binding.topArtistsShimmer.apply {
            this.stopShimmer()
            this.visibility = View.GONE
        }
    }

    companion object {
        const val TAG = "TopArtistsFragment"
    }
}