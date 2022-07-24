package com.android.vinylstore.ui.catalog.album.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.vinylstore.databinding.FragmentVinylTypeBinding

private const val IMAGE = "image"

class VinylTypeFragment : Fragment() {
    private var _binding: FragmentVinylTypeBinding? = null
    private val binding get() = _binding!!

    private var vinylImageId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vinylImageId = it.getInt(IMAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVinylTypeBinding.inflate(inflater, container, false)

        vinylImageId?.let {
            binding.vinylIv.setImageResource(it)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(imageId: Int) =
            VinylTypeFragment().apply {
                arguments = Bundle().apply {
                    putInt(IMAGE, imageId)
                }
            }
    }
}