package com.android.vinylstore.ui.main.search_artists_fragment.view

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
import com.android.vinylstore.databinding.FragmentSearchArtistsBinding
import com.android.vinylstore.ui.adapters.ArtistItemAdapter
import com.android.vinylstore.ui.main.search_artists_fragment.viewmodel.SearchArtistsViewModel
import com.android.vinylstore.ui.main.top_artists_fragment.view.TopArtistsFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchArtistsFragment : Fragment() {
    companion object {
        const val TAG = "SearchArtistsFragment"
    }

    private lateinit var binding: FragmentSearchArtistsBinding

    lateinit var viewModel: SearchArtistsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = Root.getAppComponent(requireContext())
        val activityMainComponent = appComponent.getActivityMainComponent()
        viewModel = ViewModelProvider(
            this,
            activityMainComponent.getSearchArtistsViewModelFactory()
        )[SearchArtistsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchArtistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val artistItemAdapter = ArtistItemAdapter()
        binding.searchArtistsRecyclerView.adapter = artistItemAdapter

        viewModel.items.observe(viewLifecycleOwner) { flow ->
            if (artistItemAdapter.itemCount == 0) {
                startShimmer()
            }
            flow?.let {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        it.collectLatest {
                            Log.d(TAG, "flow collecting")
                            artistItemAdapter.submitData(it)
                        }
                    }
                }
            }
        }

        artistItemAdapter.addOnPagesUpdatedListener(fun() {
            Log.d(TAG, "OnPagesUpdated")
            binding.searchArtistsSwipeRefreshLayout.apply {
                if (isRefreshing)
                    isRefreshing = false
            }
            if (binding.searchArtistsShimmer.isShimmerVisible)
                hideShimmer()
        })

        binding.searchArtistsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.searchArtistsSwipeRefreshLayout.setOnRefreshListener {
            artistItemAdapter.refresh()
            lifecycleScope.launch {
                delay(750)
                binding.searchArtistsSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun startShimmer() {
        binding.searchArtistsShimmer.apply {
            if (!isShimmerStarted) {
                startShimmer()
                visibility = View.VISIBLE
            }
        }
    }

    private fun hideShimmer() {
        binding.searchArtistsShimmer.apply {
            this.stopShimmer()
            this.visibility = View.GONE
        }
    }
}