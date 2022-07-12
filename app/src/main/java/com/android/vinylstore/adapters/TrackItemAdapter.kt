package com.android.vinylstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.databinding.TrackItemBinding
import com.android.vinylstore.lastfm_api.classes.Track

class TrackItemAdapter(private val dataSet: List<Track>) : RecyclerView.Adapter<TrackItemAdapter.ViewHolder>() {
    class ViewHolder(private val binding: TrackItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val indexTextView = binding.indexTv
        val trackNameTextView = binding.trackNameTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TrackItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dataSet.size == 1)
            holder.indexTextView.text = "Single"
        else
            holder.indexTextView.text = "${position + 1}."

        holder.trackNameTextView.text = dataSet[position].name
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}

