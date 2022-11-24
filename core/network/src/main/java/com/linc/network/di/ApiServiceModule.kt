package com.linc.network.di

import com.linc.network.api.BooksApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object ApiServiceModule {

    @Provides
    fun provideBooksApiService(
        retrofit: Retrofit
    ): BooksApiService = retrofit.create(BooksApiService::class.java)

}