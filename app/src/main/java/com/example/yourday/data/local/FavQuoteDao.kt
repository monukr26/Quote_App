package com.example.yourday.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface FavQuoteDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavQuote(quote: FavQuoteEntity)

    @Delete
    suspend fun deleteFavQuote(quote: FavQuoteEntity)

    @Query("SELECT * FROM fav_quotes ORDER BY id DESC")
    fun getAllFavQuotes(): Flow<List<FavQuoteEntity>>

}