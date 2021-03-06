package com.android.vinylstore.ui.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.vinylstore.R
import com.android.vinylstore.ui.album.view.AlbumActivity
import com.android.vinylstore.databinding.AlbumItemBinding
import com.android.vinylstore.data.lastfm_api.classes.Album
import com.squareup.picasso.Picasso

class AlbumItemAdapter(private val dataSet: List<Album>) : RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder(private val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val albumImageView = binding.albumIv
        val albumNameTextView = binding.albumNameTv

        val view: View get() = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val vh = ViewHolder(binding)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).albumNameTextView.text = dataSet[position].name
        val imageUrl = dataSet[position].image.last().path
        if (imageUrl.isNotEmpty()) {
            Picasso.with(holder.view.context)
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.ic_baseline_image_not_supported)
                .into(holder.albumImageView)
        } else {
            holder.albumImageView.setImageResource(R.drawable.image_placeholder)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, AlbumActivity::class.java)
            val bundle = Bundle().apply {
                putString(AlbumActivity.ALBUM_NAME, dataSet[position].name)
                putString(AlbumActivity.ARTIST_NAME, dataSet[position].artist.name)
            }
            intent.putExtras(bundle)
            ContextCompat.startActivity(it.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}