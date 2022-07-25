package com.android.vinylstore.data.db.data_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "album_name") val albumName: String,
    @ColumnInfo(name = "artist_name") val artistName: String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "vinyl_id") val vinylId: Int?,
)
