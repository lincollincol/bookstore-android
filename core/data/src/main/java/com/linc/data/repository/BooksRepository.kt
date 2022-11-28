package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asExternalModel
import com.linc.model.Book
import com.linc.network.api.BooksApiService
import com.linc.network.model.BookApiModel2
import com.linc.network.model.book.BookApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val booksApiService: BooksApiService,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    suspend fun getBooks(): List<Book> = withContext(dispatcher) {
        return@withContext booksApiService.getNewBooks()
            .items
            .map(BookApiModel::asExternalModel)
    }

}