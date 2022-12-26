package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.di.BooksPagingSourceFactory
import com.linc.data.model.asExternalModel
import com.linc.database.dao.BookmarksDao
import com.linc.database.dao.BooksDao
import com.linc.database.dao.SubjectDao
import com.linc.database.entity.bookmark.BookmarkEntity
import com.linc.model.Bookmark
import com.linc.network.api.BooksApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class BookmarksRepository @Inject constructor(
    private val bookmarksDao: BookmarksDao,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    fun getBookmarkedBookStream(bookId: String): Flow<Bookmark?> =
        bookmarksDao.getBookmarkByTarget(bookId)
            .map { it?.asExternalModel() }
            .flowOn(dispatcher)

    suspend fun bookmarkBook(bookId: String) {
        bookmarksDao.insertBookmark(
            BookmarkEntity(UUID.randomUUID().toString(), bookId)
        )
    }

    suspend fun deleteBookmark(bookmarkId: String) {
        bookmarksDao.deleteBookmark(bookmarkId)
    }

    suspend fun deleteBookBookmark(bookId: String) {
        bookmarksDao.deleteBookmarkByTarget(bookId)
    }

}