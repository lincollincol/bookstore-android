package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asExternalModel
import com.linc.database.dao.BookOrdersDao
import com.linc.database.dao.BooksDao
import com.linc.database.entity.BookOrderEntity
import com.linc.model.Book
import com.linc.model.BookOrder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    private val booksDao: BooksDao,
    private val bookOrdersDao: BookOrdersDao,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    suspend fun getOrders(): List<BookOrder> {
        return bookOrdersDao.getBookOrders().map(BookOrderEntity::asExternalModel)
    }

    suspend fun createOrder(bookId: String) = withContext(dispatcher) {
        val book = booksDao.getBook(bookId) ?: throw Exception("Book not found!")
        bookOrdersDao.insertBookOrder(
            BookOrderEntity(
                UUID.randomUUID().toString(),
                book,
                1
            )
        )
    }

}