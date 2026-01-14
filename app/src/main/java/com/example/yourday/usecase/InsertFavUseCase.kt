package com.example.yourday.usecase

import com.example.yourday.data.repo.FavRepository
import com.example.yourday.model.FavQuote

class InsertFavUseCase(private val repository: FavRepository) {
    suspend operator fun invoke(quote: FavQuote) = repository.insertFavQuote(quote)

}