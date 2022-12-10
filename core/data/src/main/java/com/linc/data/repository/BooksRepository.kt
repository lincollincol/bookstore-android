package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asEntity
import com.linc.data.model.asExternalModel
import com.linc.database.dao.BooksDao
import com.linc.database.dao.SubjectDao
import com.linc.database.entity.book.BookEntity
import com.linc.database.entity.subject.SubjectBookCrossRef
import com.linc.database.entity.subject.SubjectWithBooks
import com.linc.model.Book
import com.linc.model.SubjectBooks
import com.linc.network.api.BooksApiService
import com.linc.network.model.book.BookApiModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.net.UnknownHostException
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val booksDao: BooksDao,
    private val subjectDao: SubjectDao,
    private val booksApiService: BooksApiService,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    suspend fun fetchBooks() = withContext(dispatcher) {
        try {
//            booksApiService.getBooks()
//                .items
//                .map(BookApiModel::asEntity)
//                .also { booksDao.insertBooks(it) }
        } catch (ignored: UnknownHostException) {}
    }

    suspend fun fetchBooksBySubjects() : Unit = withContext(dispatcher) {
        try {
            subjectDao.getPrimarySubjects()
                .map { subject ->
                    async {
                        booksApiService.getBooks(
                            query = subject.name,
                            maxResults = 10
                        )
                            .items
                            .map(BookApiModel::asEntity)
                            .also { booksDao.insertBooks(it) }
                            .map { SubjectBookCrossRef(it.bookId, subject.subjectId) }
                            .also { subjectDao.insertSubjectBookCrossRefs(it) }
                    }
                }
                .awaitAll()
        } catch (uhe: UnknownHostException) {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getPrimarySubjectsBooksStream(): Flow<List<SubjectBooks>> {
        return flow {
            val subjects = subjectDao.getPrimarySubjects().map { it.name }
            subjectDao.getSubjectBooksStream(subjects)
                .map { it.map(SubjectWithBooks::asExternalModel) }
                .collect(this)
        }
    }

    fun getBooksStream(): Flow<List<Book>> {
        return booksDao.getBooksStream()
            .map { it.map(BookEntity::asExternalModel) }
            .flowOn(dispatcher)
    }

    fun getBooksStream(categories: List<String>): Flow<List<Book>> {
        return booksDao.getBooksStream(categories)
            .map { it.map(BookEntity::asExternalModel) }
            .flowOn(dispatcher)
    }


    suspend fun getBook(id: String): Book? = withContext(dispatcher) {
        return@withContext booksDao.getBook(id)?.asExternalModel()
    }

}