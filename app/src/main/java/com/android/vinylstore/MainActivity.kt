package com.android.vinylstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.adapters.VinylItemAdapter
import com.android.vinylstore.databinding.ActivityMainBinding
import com.android.vinylstore.model.Vinyl
import com.android.vinylstore.model.VinylsDataSetProvider

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var vinylRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vinylRecyclerView = binding.vinylRecyclerView
        vinylRecyclerView.adapter = VinylItemAdapter(VinylsDataSetProvider().get())
    }
}