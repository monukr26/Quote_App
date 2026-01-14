package com.example.yourday.data.repo

import android.net.http.HttpException
import com.example.yourday.data.remote.QuotesApiService
import com.example.yourday.model.Quote
import com.example.yourday.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class QuoteRepoImpl(
    private val apiService: QuotesApiService
): QuoteRepository {

    override fun getRandomQuote(): Flow<Resource<Quote>> = flow {
        emit(Resource.Loading())

        try {
            val response = apiService.getRandomQuote()
            val data = response.firstOrNull()

            if(data != null) {
                emit(Resource.Success(Quote(data.quote, data.author)))
            } else {
                emit(Resource.Error("No quotes found"))

            }

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))


        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))

        }
    }

}