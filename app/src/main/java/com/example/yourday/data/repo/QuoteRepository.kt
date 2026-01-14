package com.example.yourday.data.repo

import com.example.yourday.data.remote.QuotesApiService
import com.example.yourday.model.Quote
import com.example.yourday.utils.Resource
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getRandomQuote(): Flow<Resource<Quote>>
}