package com.android.vinylstore.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * RefreshingViewModel - VM for cases when you use refreshing technique on your activity/fragment
 *
 * USAGE:
 * Override onRefresh() with the code you want to execute when VM is prompted to refresh it's data
 * In VM call 'startLoading()' when you are loading data and 'endLoading()' when the loading ended
 */
abstract class RefreshingViewModel : ViewModel() {

    /**
     * @see isDataLoading
     */
    private var _isDataLoading = MutableLiveData(false)

    /**
     * Used to indicate whether some data is loading or not
     *
     * is false only if there is no data loading at all
     * is true if there is a loading in a process
     *
     * You can't refresh while this variable is true
     */
    val isDataLoading: LiveData<Boolean> = _isDataLoading

    /**
     * Update isDataLoading based on loading tasks set
     *
     * @see isDataLoading
     */
    private fun updateIsDataLoading() {
        val res = _dataLoadingsSet.isNotEmpty()
        if (_isDataLoading.value != res) _isDataLoading.value = res
    }

    /**
     * Allows to track all loading processes by adding and removing it's corresponding tags in this Set
     */
    private val _dataLoadingsSet = mutableSetOf<String>()

    /**
     * Adds loading task with 'tag' to loading tasks set
     *
     * @param tag any unique string representation of your loading task
     */
    protected fun startLoading(tag: String) {
        _dataLoadingsSet.add(tag)
        updateIsDataLoading()
    }

    /**
     * Remove loading task with 'tag' from loading tasks set
     *
     * @param tag same string representation you used to start your loading task by using 'startLoading()'
     */
    protected fun endLoading(tag: String) {
        _dataLoadingsSet.remove(tag)
        updateIsDataLoading()
    }

    /**
     * Call this when you need to refresh data
     *
     * @return false if already refreshing
     */
    fun refresh(): Boolean {
        return if (isDataLoading.value == true) {
            false
        } else {
            onRefresh()
            true
        }
    }

    /**
     * Called when refreshing is starting
     *
     * Override this with the code you want to execute when VM is prompted to refresh it's data
     */
    protected abstract fun onRefresh()
}