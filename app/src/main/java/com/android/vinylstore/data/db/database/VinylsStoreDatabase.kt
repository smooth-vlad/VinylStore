package com.android.vinylstore.data.db.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.vinylstore.data.db.dao.CartItemsDao
import com.android.vinylstore.data.db.data_entity.CartItem

@Database(
    entities = [CartItem::class], version = 1
)
abstract class VinylsStoreDatabase : RoomDatabase() {
    abstract fun cartItemsDao(): CartItemsDao
}