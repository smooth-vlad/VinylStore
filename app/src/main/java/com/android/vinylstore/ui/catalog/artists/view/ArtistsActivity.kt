package com.android.vinylstore.ui.catalog.artists.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MenuItem.OnActionExpandListener
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.android.vinylstore.R
import com.android.vinylstore.Root
import com.android.vinylstore.databinding.ActivityArtistsBinding
import com.android.vinylstore.ui.catalog.artists.search_artists_fragment.view.SearchArtistsFragment
import com.android.vinylstore.ui.catalog.artists.top_artists_fragment.view.TopArtistsFragment
import com.android.vinylstore.ui.catalog.artists.viewmodel.ArtistsViewModel


class ArtistsActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ArtistsActivity"
        private const val TOP_ARTISTS_FRAGMENT_INDEX = 0
        private const val SEARCH_ARTISTS_FRAGMENT_INDEX = 1
    }

    private lateinit var binding: ActivityArtistsBinding

    lateinit var viewModel: ArtistsViewModel

    private var search: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArtistsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appComponent = Root.getAppComponent(this)
        val activityMainComponent = appComponent.getActivityMainComponent()
        viewModel = ViewModelProvider(
            this,
            activityMainComponent.getMainViewModelFactory()
        )[ArtistsViewModel::class.java]

        viewModel.isInSearchMode.observe(this) {
            onToggleSearchMode(it)
        }

        if (supportFragmentManager.fragments.isEmpty()) { // add top artists fragment
            supportFragmentManager.commit {
                add<TopArtistsFragment>(R.id.artists_list_container)
            }
        }

        setSupportActionBar(binding.myToolbarMain)
    }

    private fun onToggleSearchMode(isInSearchMode: Boolean?) {
        if (isInSearchMode == true) {
            supportFragmentManager.commit {
                setFragmentVisibility(TOP_ARTISTS_FRAGMENT_INDEX, View.INVISIBLE)
                if (supportFragmentManager.fragments.size == 1) { // add search fragment
                    add<SearchArtistsFragment>(R.id.artists_list_container)
                } else {
                    setFragmentVisibility(SEARCH_ARTISTS_FRAGMENT_INDEX, View.VISIBLE)
                }
            }
        } else {
            supportFragmentManager.commit {
                setFragmentVisibility(SEARCH_ARTISTS_FRAGMENT_INDEX, View.INVISIBLE)
                setFragmentVisibility(TOP_ARTISTS_FRAGMENT_INDEX, View.VISIBLE)
            }
        }
    }

    private fun FragmentTransaction.setFragmentVisibility(
        fragmentIndex: Int,
        newVisibility: Int
    ): FragmentTransaction {
        val fragment = supportFragmentManager.fragments.getOrNull(fragmentIndex)
        fragment?.let {
            return if (newVisibility == View.VISIBLE) {
                show(fragment)
            } else {
                hide(fragment)
            }
        }
        return this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.artists_activity_menu, menu)
        search = menu.findItem(R.id.action_search)
        search!!.setOnActionExpandListener(SearchViewExpandListener(viewModel))

        if (viewModel.isInSearchMode.value == true)
            search!!.expandActionView()

        (search!!.actionView as androidx.appcompat.widget.SearchView).apply {
            isSubmitButtonEnabled = true

            this.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean = false

                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG, query.toString())
                    query?.let { onSearchSubmit(query) }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun onSearchSubmit(query: String) {
        val searchArtistsFragment =
            supportFragmentManager.fragments[SEARCH_ARTISTS_FRAGMENT_INDEX] as SearchArtistsFragment
        searchArtistsFragment.viewModel.initSearch(query)
    }

    class SearchViewExpandListener(private val viewModel: ArtistsViewModel) : OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            Log.d(TAG, "expand")
            viewModel.enterSearchMode()
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            Log.d(TAG, "collapse")
            viewModel.exitSearchMode()
            return true
        }

    }
}