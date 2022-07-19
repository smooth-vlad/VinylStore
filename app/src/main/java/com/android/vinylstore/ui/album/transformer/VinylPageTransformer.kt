package com.android.vinylstore.ui.album.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class VinylPageTransformer(private val density: Float) :
    ViewPager2.PageTransformer {

//    private val viewPager =

    // vinyl image is 200 dp wide, so 100 is a half
    private val halfVinylImage = 100F * density
    private fun getHalfVinylImageOffset(page: View): Float {
        return halfVinylImage / page.width
    }

    override fun transformPage(page: View, position: Float) {
        if (position <= -1f) {    // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.alpha = 0F;
            page.translationZ = 0.0f

        } else if (position <= 0f) {    // [-1,0]
            if (position <= -getHalfVinylImageOffset(page)) {
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