package com.android.vinylstore.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.presentation.artist.view.ArtistActivity
import com.android.vinylstore.databinding.ArtistItemBinding
import com.android.vinylstore.lastfm_api.classes.Artist
import java.util.*

class ArtistItemAdapter(private val dataSet: List<Artist>) :
    RecyclerView.Adapter<ArtistItemAdapter.ViewHolder>(), Filterable {

    private var filteredList = dataSet

    class ViewHolder(private val binding: ArtistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val artistNameTextView: TextView = binding.artistNameTv
        val view: View get() = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.artistNameTextView.text = filteredList[position].name.trim()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ArtistActivity::class.java)
            val bundle = Bundle().apply {
                putString(ArtistActivity.ARTIST_NAME, filteredList[position].name)
            }
            intent.putExtras(bundle)
            ContextCompat.startActivity(it.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredResults: List<Artist>? = null
                filteredResults = if (constraint!!.isEmpty()) {
                    dataSet
                } else {
                    getFilteredResults(
                        constraint.toString()
                            .lowercase(Locale.ROOT)
                    )
                }

                val results = FilterResults()
                results.values = filteredResults

                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results!!.values as List<Artist>
                notifyDataSetChanged()
            }
        }
    }

    private fun getFilteredResults(constraint: String): List<Artist> {
        val results: MutableList<Artist> = ArrayList()

        for (item in dataSet) {
            if (item.name.lowercase(Locale.ROOT).contains(constraint)) {
                results.add(item)
            }
        }
        return results
    }
}