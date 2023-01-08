package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asExternalModel
import com.linc.database.dao.OrdersDao
import com.linc.database.dao.BooksDao
import com.linc.database.entity.book.OrderAndBook
import com.linc.database.entity.order.OrderEntity
import com.linc.database.entity.order.UpdateOrderActive
import com.linc.model.BookOrder
import com.linc.model.Order
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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

    fun getActiveBookOrderStream(bookId: String): Flow<Order?> =
        ordersDao.getOrderByTarget(bookId, isActive = true)
            .map { it?.asExternalModel() }
            .flowOn(dispatcher)

    fun getActiveBookOrdersStream(): Flow<List<BookOrder>> =
        ordersDao.getOrderAndBooks(isActive = true)
            .map { it.map(OrderAndBook::asExternalModel) }
            .flowOn(dispatcher)

    fun getInactiveBookOrdersStream(): Flow<List<BookOrder>> =
        ordersDao.getOrderAndBooks(isActive = false)
            .map { it.map(OrderAndBook::asExternalModel) }
            .flowOn(dispatcher)

    suspend fun orderBook(
        bookId: String,
        count: Int
    ) = withContext(dispatcher) {
        // TODO: refactor entity
        ordersDao.insertOrder(
            OrderEntity(
                UUID.randomUUID().toString(),
                bookId,
                count,
                true,
                System.currentTimeMillis()
            )
        )
    }

    suspend fun makeOrderActive(orderId: String, isActive: Boolean) = withContext(dispatcher) {
        val updateEntity = UpdateOrderActive(
            orderId = orderId,
            isActive = isActive,
            lastModifiedMillis = System.currentTimeMillis()
        )
        ordersDao.updateOrderActive(updateEntity)
    }

    suspend fun makeOrdersActive(orderIds: List<String>, isActive: Boolean) = withContext(dispatcher) {
        orderIds.map { async { makeOrderActive(it, isActive) } }.awaitAll()
    }

    suspend fun deleteOrder(orderId: String) = withContext(dispatcher) {
        ordersDao.deleteOrder(orderId)
    }

}