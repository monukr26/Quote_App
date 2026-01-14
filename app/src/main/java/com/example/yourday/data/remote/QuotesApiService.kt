package com.example.yourday.data.remote


import retrofit2.http.GET

interface QuotesApiService {
    @GET("random")
    suspend fun getRandomQuote(): List<QuotesResponse>
}