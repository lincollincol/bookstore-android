package com.linc.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.di.BooksPagingSourceFactory
import com.linc.data.model.asEntity
import com.linc.data.model.asExternalModel
import com.linc.data.paging.BooksPagingSource
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
    private val booksPagingSourceFactory: BooksPagingSourceFactory,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    companion object {
        const val MAX_BOOKS_PER_PAGE = 10
    }

    fun getSubjectBooksStream(subjectId: String) = flow {
        val subjectName = subjectDao.getSubject(subjectId)?.name.orEmpty()
        Pager(
            config = PagingConfig(pageSize = MAX_BOOKS_PER_PAGE),
            pagingSourceFactory = { booksPagingSourceFactory.create(subjectName) }
        )
            .flow
            .map { it.map(BookApiModel::asExternalModel) }
            .flowOn(dispatcher)
            .collect(this)
    }

    suspend fun fetchBooksBySubjects() : Unit = withContext(dispatcher) {
        try {
            subjectDao.getPrimarySubjects()
                .map { subject ->
                    async {
                        booksApiService.getBooks(
                            query = subject.name,
                            maxResults = MAX_BOOKS_PER_PAGE
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