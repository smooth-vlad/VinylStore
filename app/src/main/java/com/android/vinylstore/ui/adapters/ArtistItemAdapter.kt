package com.android.vinylstore.ui.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.ui.artist.view.ArtistActivity
import com.android.vinylstore.databinding.ArtistItemBinding
import com.android.vinylstore.data.lastfm_api.classes.Artist

class ArtistItemAdapter :
    PagingDataAdapter<Artist, ArtistItemAdapter.ViewHolder>(ARTIST_DIFF_CALLBACK) {

    class ViewHolder(private val binding: ArtistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val artistNameTextView: TextView = binding.artistNameTv
        val view: View get() = binding.root

        fun bind(artist: Artist) {
            binding.apply {
                artistNameTextView.text = artist.name.trim()

                itemView.setOnClickListener {
                    val intent = Intent(it.context, ArtistActivity::class.java)
                    val bundle = Bundle().apply {
                        putString(ArtistActivity.ARTIST_NAME, artist.name)
                    }
                    intent.putExtras(bundle)
                    ContextCompat.startActivity(it.context, intent, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }


    companion object {
        private val ARTIST_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Artist>() {
            override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                return oldItem == newItem
            }

        }
    }

}