package com.android.vinylstore.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.vinylstore.data.db.data_entity.CartItem

@Dao
interface CartItemsDao {
    @Query("SELECT * FROM cart_items")
    fun getAll() : LiveData<List<CartItem>>

    @Insert
    fun insert(vararg cartItem: CartItem)

    @Delete
    fun delete(cartItem: CartItem)

    @Query("DELETE FROM cart_items")
    fun clear()
}