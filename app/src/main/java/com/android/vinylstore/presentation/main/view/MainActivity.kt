package com.android.vinylstore.presentation.main.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.android.vinylstore.R
import com.android.vinylstore.Root
import com.android.vinylstore.presentation.main.viewmodel.MainViewModel
import com.android.vinylstore.presentation.main.viewmodel.MainViewModelFactory
import com.android.vinylstore.adapters.ArtistItemAdapter
import com.android.vinylstore.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private var search: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(Root.getAppComponent(this).getAlbumsApiService())
        )[MainViewModel::class.java]

        viewModel.artists.observe(this) {
            it.let {
                val adapter = ArtistItemAdapter(it)
                Log.d("MainActivity", "Number of artists found: " + it.size)
                binding.vinylRecyclerView.adapter = adapter

                hidePlaceHolderUi()
                unlockSearchMenuItem()
            }
        }

        viewModel.error.observe(this) {
            Log.e("MainActivity", it)
            Toast.makeText(
                this@MainActivity,
                getString(R.string.connection_failure),
                Toast.LENGTH_SHORT
            ).show()
        }

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
                    .apply {
                        this.setOnQueryTextListener(object :
                            androidx.appcompat.widget.SearchView.OnQueryTextListener {
                            override fun onQueryTextChange(newText: String?): Boolean {
                                val adapter =
                                    (binding.vinylRecyclerView.adapter as ArtistItemAdapter)
                                adapter.filter.filter(newText)
                                Log.d("MainActivity", "queryTextChange")
                                return true
                            }

                            override fun onQueryTextSubmit(query: String?): Boolean {
                                return true
                            }
                        })
                    }
            }
        return super.onCreateOptionsMenu(menu)
    }
}