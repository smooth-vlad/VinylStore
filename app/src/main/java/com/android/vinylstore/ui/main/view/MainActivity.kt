package com.android.vinylstore.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.R
import com.android.vinylstore.Root
import com.android.vinylstore.ui.main.viewmodel.MainViewModel
import com.android.vinylstore.ui.adapters.ArtistItemAdapter
import com.android.vinylstore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel

    private var search: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Root.getAppComponent(this).inject(this)

        val items = viewModel.items
        val artistItemAdapter = ArtistItemAdapter()

        binding.vinylRecyclerView.adapter = artistItemAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    artistItemAdapter.submitData(it)
                }
            }
        }

//        viewModel.artists.observe(this) {
//            it.let {
//                val adapter = ArtistItemAdapter(it)
//                Log.d("MainActivity", "Number of artists: " + it.size)
//                binding.vinylRecyclerView.adapter = adapter
//
//                hidePlaceHolderUi()
//                unlockSearchMenuItem()
//            }
//        }

//        viewModel.error.observe(this) {
//            Log.e("MainActivity", it)
//            Toast.makeText(
//                this@MainActivity,
//                getString(R.string.connection_failure),
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        viewModel.isDataLoading.observe(this) {
            if (!it && binding.artistsSwipeRefresh.isRefreshing)
                binding.artistsSwipeRefresh.isRefreshing = false
        }

        binding.vinylRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        binding.artistsSwipeRefresh.setOnRefreshListener {
            if (!viewModel.refresh()) {
                Toast.makeText(
                    this,
                    getString(R.string.refresh_on_loading_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.activityMainShimmer.startShimmer()

        setSupportActionBar(binding.myToolbarMain)

        viewModel.refresh()
    }

    private fun unlockSearchMenuItem() {
        search?.isEnabled = true
    }

    private fun hidePlaceHolderUi() {
        binding.activityMainShimmer.apply {
            this.stopShimmer()
            this.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        search = menu.findItem(R.id.action_search)
            .apply {
                isEnabled = false
                (this.actionView as androidx.appcompat.widget.SearchView)
//                    .apply {
//                        this.setOnQueryTextListener(object :
//                            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//                            override fun onQueryTextChange(newText: String?): Boolean {
//                                val adapter =
//                                    (binding.vinylRecyclerView.adapter as ArtistItemAdapter)
//                                adapter.filter.filter(newText)
//                                Log.d("MainActivity", "queryTextChange")
//                                return true
//                            }
//
//                            override fun onQueryTextSubmit(query: String?): Boolean {
//                                return true
//                            }
//                        })
//                    }
            }
        return super.onCreateOptionsMenu(menu)
    }
}