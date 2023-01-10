package com.linc.network.di

import com.linc.network.api.BooksApiService
import com.linc.network.api.StripeApiService
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
        @ApiInstance(Type.BOOKS) retrofit: Retrofit
    ): BooksApiService = retrofit.create(BooksApiService::class.java)

    @Provides
    fun provideStripeApiService(
        @ApiInstance(Type.STRIPE) retrofit: Retrofit
    ): StripeApiService = retrofit.create(StripeApiService::class.java)

}