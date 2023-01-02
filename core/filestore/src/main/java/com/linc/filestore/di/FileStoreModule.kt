package com.linc.filestore.di

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FileStoreModule {
    @Provides
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager = context.assets
    @Provides
    fun provideGson(builder: GsonBuilder): Gson = builder.create()
    @Provides
    fun provideGsonBuilder(): GsonBuilder = GsonBuilder()
}