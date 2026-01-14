package com.example.yourday.usecase

import com.example.yourday.data.repo.FavRepository
import com.example.yourday.model.FavQuote

class DeleteFavUseCase(private val repository: FavRepository) {
    suspend operator fun invoke(quote: FavQuote) {
        repository.deleteFavQuote(quote)
    }
}

