package com.example.yourday.data.repo

import com.example.yourday.model.FavQuote
import kotlinx.coroutines.flow.Flow

interface FavRepository {

    suspend fun insertFavQuote(quote: FavQuote)
    suspend fun deleteFavQuote(quote: FavQuote)
    fun getAllFavQuotes(): Flow<List<FavQuote>>
}


