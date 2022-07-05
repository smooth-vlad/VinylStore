package com.android.vinylstore.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.activity.ArtistActivity
import com.android.vinylstore.databinding.ArtistItemBinding
import com.android.vinylstore.lastfm_api.classes.Artist
import com.squareup.picasso.Picasso

class ArtistItemAdapter(private val dataSet: List<Artist>) : RecyclerView.Adapter<ArtistItemAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ArtistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val artistNameTextView: TextView = binding.artistNameTv
        val artistImageView: ImageView = binding.imageView

        val view: View get() = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.artistNameTextView.text = dataSet[position].name.trim()
        val imageUrl = dataSet[position].image.last().path
        Picasso.with(holder.view.context)
            .load(imageUrl)
            .placeholder(androidx.appcompat.resources.R.drawable.abc_vector_test)
            .error(androidx.appcompat.resources.R.drawable.abc_vector_test)
            .into(holder.artistImageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ArtistActivity::class.java)
            val bundle = Bundle().apply {
                putString(ArtistActivity.ARTIST_NAME, dataSet[position].name)
            }
            intent.putExtras(bundle)
            ContextCompat.startActivity(it.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}