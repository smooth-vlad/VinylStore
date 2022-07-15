package com.android.vinylstore.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * RefreshingViewModel - VM for cases when you use refreshing technique on your activity/fragment
 *
 * USAGE:
 * Override onRefresh() with the code you want to execute when VM is prompted to refresh it's data
 * In VM set _isDataLoading to 'true' when you are loading data and 'false' when data is successfully loaded
 */
abstract class RefreshingViewModel : ViewModel() {
    /**
     * @see isDataLoading
     */
    protected var _isDataLoading = MutableLiveData(false)

    /**
     * Used to indicate whether some data is loading or not
     *
     * should be false only if there is no data loading at all
     * should be true if there is a loading in a process
     *
     * You can't refresh while this variable is true
     */
    val isDataLoading: LiveData<Boolean> = _isDataLoading

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
    abstract fun onRefresh()
}