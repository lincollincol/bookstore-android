package com.linc.network.di

import com.linc.network.BuildConfig
import com.linc.network.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object GoogleBooksModule {

    @ApiInstance(Type.BOOKS)
    @Provides
    @Singleton
    fun provideRetrofit(
        url: HttpUrl,
        @ApiInstance(Type.BOOKS) client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .build()

    @ApiInstance(Type.BOOKS)
    @Provides
    fun provideOkHttpClient(
        @ApiInstance(Type.BOOKS) authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(authInterceptor)
        .build()

    @ApiInstance(Type.BOOKS)
    @Provides
    fun provideAuthInterceptor(
        @ApiInstance(Type.BOOKS) token: String
    ): AuthInterceptor = AuthInterceptor(token, isBearer = false)

    @ApiInstance(Type.BOOKS)
    @Provides
    fun provideGoogleApiKey(): String = BuildConfig.GOOGLE_BOOKS_API_KEY

}