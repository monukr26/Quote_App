package com.example.yourday.usecase

import com.example.yourday.data.repo.QuoteRepository

class GetRandomQuotesCase(
    private val repository: QuoteRepository
) {
    operator fun invoke() = repository.getRandomQuote()

}