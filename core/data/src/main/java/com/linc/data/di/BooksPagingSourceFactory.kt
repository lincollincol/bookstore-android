package com.linc.data.di

import com.linc.data.paging.BooksPagingSource
import dagger.assisted.AssistedFactory

@AssistedFactory
interface BooksPagingSourceFactory {
    fun create(query: String): BooksPagingSource
}