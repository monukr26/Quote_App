package com.example.yourday.di

import com.example.yourday.data.remote.QuotesApiService
import com.example.yourday.data.repo.QuoteRepoImpl
import com.example.yourday.data.repo.QuoteRepository
import com.example.yourday.usecase.GetRandomQuotesCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loading = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loading)
            .build()
    }

    @Provides
    @Singleton
    fun provideQuoteApi(client: OkHttpClient): QuotesApiService =
        Retrofit.Builder()
            .baseUrl("https://zenquotes.io/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApiService::class.java)

    @Provides
    @Singleton
    fun provideQuoteRepository(apiService: QuotesApiService): QuoteRepository =
        QuoteRepoImpl(apiService)


    @Provides
    @Singleton
    fun provideRandomQuotesUseCase(repo: QuoteRepository) =
        GetRandomQuotesCase(repo)
}


