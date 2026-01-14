package com.example.yourday.usecase

import com.example.yourday.data.repo.FavRepository

class GetFavUseCase(private val repository: FavRepository) {
    operator fun invoke() = repository.getAllFavQuotes()
}

