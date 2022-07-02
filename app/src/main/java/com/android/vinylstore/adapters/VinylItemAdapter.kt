package com.android.vinylstore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.R
import com.android.vinylstore.model.Vinyl

class VinylItemAdapter(private val dataSet: Array<Vinyl>) : RecyclerView.Adapter<VinylItemAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vinylNameTextView: TextView
        val vinylAuthorTextView: TextView
        val vinylImageView: ImageView

        init {
            vinylNameTextView = view.findViewById(R.id.vinyl_name_text_view)
            vinylAuthorTextView = view.findViewById(R.id.vinyl_author_name_text_view)
            vinylImageView = view.findViewById(R.id.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vinyl_item, parent, false)
        return ViewHolder(view)
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