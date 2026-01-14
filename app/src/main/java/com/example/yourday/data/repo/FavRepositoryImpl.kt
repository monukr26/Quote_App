package com.example.yourday.data.repo

import com.example.yourday.data.local.FavQuoteDao
import com.example.yourday.data.local.FavQuoteEntity
import com.example.yourday.model.FavQuote
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavRepositoryImpl @Inject constructor (
    private val dao: FavQuoteDao
): FavRepository {

    override suspend fun insertFavQuote(quote: FavQuote) {
        dao.insertFavQuote(FavQuoteEntity(text = quote.text, author = quote.author))
    }

    override suspend fun deleteFavQuote(quote: FavQuote) {
        dao.deleteFavQuote(FavQuoteEntity(id=quote.id, text = quote.text, author = quote.author))
    }

    override fun getAllFavQuotes(): Flow<List<FavQuote>> {
        return dao.getAllFavQuotes().map { list ->
            list.map { entity ->
                FavQuote(id=entity.id, text = entity.text, author = entity.author)
            }
        }
    }

}