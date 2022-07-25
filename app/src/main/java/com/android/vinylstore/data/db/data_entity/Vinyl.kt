package com.android.vinylstore.data.db.data_entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vinyls")
data class Vinyl(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    // TODO load images into internal storage on app start and save links to them here
    @ColumnInfo(name = "image_id") val imageId: Int,
)
