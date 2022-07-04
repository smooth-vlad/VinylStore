package com.android.vinylstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.R
import com.android.vinylstore.databinding.ArtistItemBinding
import com.android.vinylstore.lastfm_api.Artist
import com.squareup.picasso.Picasso

class ArtistItemAdapter(private val dataSet: List<Artist>) : RecyclerView.Adapter<ArtistItemAdapter.ViewHolder>() {
    class ViewHolder(val binding: ArtistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val artistNameTextView: TextView = binding.artistNameTv
        val artistImageView: ImageView = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.artistNameTextView.text = dataSet[position].name.trim()
        val imageUrl = dataSet[position].image.last().path
        Picasso.with(holder.binding.root.context)
            .load(imageUrl)
            .placeholder(androidx.appcompat.resources.R.drawable.abc_vector_test)
            .error(androidx.appcompat.resources.R.drawable.abc_vector_test)
            .into(holder.artistImageView)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}