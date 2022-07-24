package com.android.vinylstore.ui.catalog.album.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.vinylstore.R
import com.android.vinylstore.ui.catalog.album.view.VinylTypeFragment

class VinylPagerAdapter(
    private var fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(fragmentActivity) {

    private val vinylImages = listOf(
        R.drawable.vinyl_black,
        R.drawable.vinyl_blue,
        R.drawable.vinyl_glow,
        R.drawable.vinyl_green,
        R.drawable.vinyl_pink,
        R.drawable.vinyl_purple,
        R.drawable.vinyl_red,
        R.drawable.vinyl_white,
        R.drawable.vinyl_yellow
    )

    override fun getItemCount(): Int {
        return vinylImages.size
    }

    override fun createFragment(position: Int): Fragment {
        return VinylTypeFragment.newInstance(vinylImages[position])
    }
}