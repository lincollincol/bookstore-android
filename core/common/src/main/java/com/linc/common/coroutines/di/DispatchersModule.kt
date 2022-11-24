package com.linc.common.coroutines.di

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(AppDispatchers.IO)
    fun provideIODispatcher() : CoroutineDispatcher = Dispatchers.IO
}
