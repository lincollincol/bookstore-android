package com.linc.database.di

import android.content.Context
import com.linc.database.BookstoreDatabase
import com.linc.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : BookstoreDatabase = BookstoreDatabase.create(context)

    @Singleton
    @Provides
    fun provideBookOrdersDao(
        database: BookstoreDatabase
    ): OrdersDao = database.ordersDao

    @Singleton
    @Provides
    fun provideBooksDao(
        database: BookstoreDatabase
    ): BooksDao = database.booksDao

    @Singleton
    @Provides
    fun provideSubjectDao(
        database: BookstoreDatabase
    ): SubjectDao = database.subjectDao

    @Singleton
    @Provides
    fun provideBookmarksDao(
        database: BookstoreDatabase
    ): BookmarksDao = database.bookmarksDao

    @Singleton
    @Provides
    fun provideLocaleDao(
        database: BookstoreDatabase
    ): LocaleDao = database.localeDao

}