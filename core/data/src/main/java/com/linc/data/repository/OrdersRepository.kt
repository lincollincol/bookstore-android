package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asExternalModel
import com.linc.database.dao.OrdersDao
import com.linc.database.dao.BooksDao
import com.linc.database.entity.book.OrderAndBook
import com.linc.database.entity.order.OrderEntity
import com.linc.model.BookOrder
import com.linc.model.Order
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.List

class OrdersRepository @Inject constructor(
    private val booksDao: BooksDao,
    private val ordersDao: OrdersDao,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    fun getBookOrderStream(bookId: String): Flow<Order?> =
        ordersDao.getOrderByTarget(bookId)
            .map { it?.asExternalModel() }
            .flowOn(dispatcher)

    fun getBookOrdersStream(): Flow<List<BookOrder>> =
        ordersDao.getOrderAndBooks()
            .map { it.map(OrderAndBook::asExternalModel) }
            .flowOn(dispatcher)

    suspend fun orderBook(
        bookId: String,
        count: Int
    ) = withContext(dispatcher) {
        ordersDao.insertOrder(
            OrderEntity(
                UUID.randomUUID().toString(),
                bookId,
                count
            )
        )
    }

    suspend fun deleteOrder(orderId: String, ) = withContext(dispatcher) {
        ordersDao.deleteOrder(orderId)
    }

}