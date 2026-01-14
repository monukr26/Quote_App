package com.example.yourday.di

import android.content.Context
import androidx.room.Room
import com.example.yourday.data.local.QuoteDatabase
import com.example.yourday.data.repo.FavRepository
import com.example.yourday.data.repo.FavRepositoryImpl
import com.example.yourday.usecase.DeleteFavUseCase
import com.example.yourday.usecase.GetFavUseCase
import com.example.yourday.usecase.InsertFavUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuoteDatabase =
        Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            "quotes_db"
        ).fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideFavRepository(db: QuoteDatabase): FavRepository =
        FavRepositoryImpl(db.favQuoteDao)

    @Provides
    @Singleton
    fun provideInsertFavUseCase(repo: FavRepository) = InsertFavUseCase(repo)

    @Provides
    @Singleton
    fun provideGetFavUseCase(repo: FavRepository) = GetFavUseCase(repo)

    @Provides
    @Singleton
    fun provideDeleteFavUseCase(repo: FavRepository) = DeleteFavUseCase(repo)


}