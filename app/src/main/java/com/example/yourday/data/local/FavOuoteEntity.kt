package com.example.yourday.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_quotes")
data class FavQuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val text: String,
    val author: String,

)