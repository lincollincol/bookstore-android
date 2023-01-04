package com.linc.network.di

import com.linc.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

@Module
@InstallIn(SingletonComponent::class)
internal object FlavorModule {

    @Provides
    fun provideBaseUrl(): HttpUrl = BuildConfig.PROD_BOOKS_BASE_URL.toHttpUrl()

}