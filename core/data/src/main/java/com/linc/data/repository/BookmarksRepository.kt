package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asExternalModel
import com.linc.database.dao.BookmarksDao
import com.linc.database.entity.book.BookmarkAndBook
import com.linc.database.entity.bookmark.BookmarkEntity
import com.linc.model.BookBookmark
import com.linc.model.Bookmark
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

    fun getBookBookmarkStream(bookId: String): Flow<Bookmark?> =
        bookmarksDao.getBookmarkByTarget(bookId)
            .map { it?.asExternalModel() }
            .flowOn(dispatcher)

    fun getBookBookmarks(): Flow<List<BookBookmark>> =
        bookmarksDao.getBookBookmarks()
            .map { it.map(BookmarkAndBook::asExternalModel) }
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