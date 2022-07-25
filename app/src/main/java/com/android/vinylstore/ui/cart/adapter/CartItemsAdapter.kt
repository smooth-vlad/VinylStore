package com.android.vinylstore.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.vinylstore.data.db.data_entity.CartItem
import com.android.vinylstore.databinding.CartItemBinding

class CartItemsAdapter(private val items: List<CartItem>) : RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {
    class ViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.albumTitleTextView.text = item.albumName
        holder.binding.authorTextView.text = item.artistName
        holder.binding.priceTextView.text = "12.89 $"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}