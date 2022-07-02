package com.android.vinylstore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.R
import com.android.vinylstore.databinding.VinylItemBinding
import com.android.vinylstore.model.Vinyl

class VinylItemAdapter(private val dataSet: Array<Vinyl>) : RecyclerView.Adapter<VinylItemAdapter.ViewHolder>() {
    class ViewHolder(private val binding: VinylItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val vinylNameTextView: TextView = binding.vinylNameTextView
        val vinylAuthorTextView: TextView = binding.vinylAuthorNameTextView
        val vinylImageView: ImageView = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VinylItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vinylNameTextView.text = dataSet[position].title
        holder.vinylAuthorTextView.text = dataSet[position].author
        holder.vinylImageView.setImageResource(dataSet[position].imageSourceId)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}