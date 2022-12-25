package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asExternalModel
import com.linc.database.dao.OrdersDao
import com.linc.database.dao.BooksDao
import com.linc.database.entity.book.BookAndOrder
import com.linc.database.entity.order.OrderEntity
import com.linc.model.BookOrder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    private val booksDao: BooksDao,
    private val ordersDao: OrdersDao,
    @Dispatcher(AppDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {

    fun getBookOrderStream(bookId: String): Flow<BookOrder?> {
        return ordersDao.getBookOrderStream(bookId)
//            .map { it?.asExternalModel() }
            .map(BookAndOrder::asExternalModel)
            .flowOn(dispatcher)
    }

//    suspend fun getOrders(): List<BookOrder> {
//        return ordersDao.getBookOrders().map(BookAndOrder::asExternalModel)
//    }

    suspend fun orderBook(bookId: String) = withContext(dispatcher) {
        ordersDao.insertOrder(
            OrderEntity(
                UUID.randomUUID().toString(),
                bookId,
                1
            )
        )
    }

}