package com.example.yourday.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavQuoteEntity::class], version = 1, exportSchema = false)
abstract class QuoteDatabase: RoomDatabase() {
    abstract val favQuoteDao: FavQuoteDao
}
