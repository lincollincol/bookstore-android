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
internal object StripeModule {

    @ApiInstance(Type.STRIPE)
    @Provides
    @Singleton
    fun provideRetrofit(
        @ApiInstance(Type.STRIPE) client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.STRIPE_BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .build()

    @ApiInstance(Type.STRIPE)
    @Provides
    fun provideOkHttpClient(
        @ApiInstance(Type.STRIPE) authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(authInterceptor)
        .build()

    @ApiInstance(Type.STRIPE)
    @Provides
    fun provideAuthInterceptor(
        @ApiInstance(Type.STRIPE) token: String
    ): AuthInterceptor = AuthInterceptor(token, isBearer = true)

    @ApiInstance(Type.STRIPE)
    @Provides
    fun provideApiKey(): String = BuildConfig.STRIPE_SECRET_KEY

}