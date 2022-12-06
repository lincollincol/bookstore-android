package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asEntity
import com.linc.data.model.asExternalModel
import com.linc.database.dao.BooksDao
import com.linc.database.entity.book.BookEntity
import com.linc.model.Book
import com.linc.network.api.BooksApiService
import com.linc.network.model.book.BookApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val booksDao: BooksDao,
    private val booksApiService: BooksApiService,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchBooks() = withContext(dispatcher) {
        try {
            delay(5000)
            booksApiService.getNewBooks()
                .items
                .map(BookApiModel::asEntity)
                .also { booksDao.insertBooks(it) }
        } catch (ignored: UnknownHostException) {}
    }

    suspend fun getBooks(): List<Book> = withContext(dispatcher) {
        try {
            val fetchedBooks = booksApiService.getNewBooks()
                .items
                .map(BookApiModel::asEntity)
            booksDao.insertBooks(fetchedBooks)
        } catch (ignored: UnknownHostException) { }
        return@withContext booksDao.getBooks().map(BookEntity::asExternalModel)
    }

    fun getBooksStream(): Flow<List<Book>> {
        return booksDao.getBooksStream()
            .map { it.map(BookEntity::asExternalModel) }
            .flowOn(Dispatchers.IO)
    }

    fun getBooksStream(categories: List<String>): Flow<List<Book>> {
        return booksDao.getBooksStream(categories)
            .map { it.map(BookEntity::asExternalModel) }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getBook(id: String): Book? = withContext(dispatcher) {
        return@withContext booksDao.getBook(id)?.asExternalModel()
    }

}