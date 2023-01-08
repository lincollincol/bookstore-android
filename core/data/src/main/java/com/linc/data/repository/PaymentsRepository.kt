package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.data.model.asEntity
import com.linc.database.dao.OrdersDao
import com.linc.database.dao.PaymentsDao
import com.linc.database.dao.UsersDao
import com.linc.database.entity.payment.CustomerEntity
import com.linc.database.entity.payment.EphemeralKeyEntity
import com.linc.database.entity.payment.isExpired
import com.linc.network.api.StripeApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

class PaymentsRepository @Inject constructor(
    private val stripeApiService: StripeApiService,
    private val paymentsDao: PaymentsDao,
    private val usersDao: UsersDao,
    private val ordersDao: OrdersDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun createCustomer() = withContext(ioDispatcher) {
        val user = usersDao.getUser()
        if(paymentsDao.getCustomer() != null || user == null) {
            return@withContext
        }
        val customer = stripeApiService.createCustomer(user.name)
        with(paymentsDao) {
            deleteAllCustomers()
            insertCustomer(customer.asEntity(user.userId))
        }
    }

    suspend fun refreshCustomerEphemeralKey() {
        val customer = paymentsDao.getCustomer() ?: return
        val localeKey = paymentsDao.getEphemeralKey()
        if(localeKey != null && !localeKey.isExpired) {
            return
        }
        val key = stripeApiService.createEphemeralKey(customer.customerId).asEntity()
        with(paymentsDao) {
            deleteAllEphemeralKeys()
            insertEphemeralKey(key)
        }
    }

    suspend fun createOrdersPayment(orderIds: List<String>) = withContext(ioDispatcher) {
        val ordersGroupId = orderIds.joinToString()
        val localPaymentIntent = paymentsDao.getPaymentIntentByOrderId(ordersGroupId)
        val customer = paymentsDao.getCustomer()
        if(localPaymentIntent != null || customer == null) {
            return@withContext
        }
        refreshCustomerEphemeralKey()
        val orders = orderIds.map { async { ordersDao.getOrderAndBookById(it) } }
            .awaitAll()
            .filterNotNull()
        val totalPrice = orders.sumOf { it.book.price * it.order.count * 100 }.roundToInt()
        val currency = orders.first { it.book.currency.isNotEmpty() }.book.currency.lowercase()
        val paymentIntent = stripeApiService.createPaymentIntent(
            customerId = customer.customerId,
            amount = totalPrice,
            currency = currency,
            description = ordersGroupId,
            autoPaymentMethodsEnable = true
        )
        paymentsDao.insertPaymentIntent(paymentIntent.asEntity(ordersGroupId))
    }

    suspend fun getOrdersPaymentClientSecret(
        orderIds: List<String>
    ): String? = withContext(ioDispatcher) {
        return@withContext paymentsDao.getPaymentIntentByOrderId(orderIds.joinToString())?.clientSecret
    }

    suspend fun deleteOrdersPaymentIntent(orderIds: List<String>) = withContext(ioDispatcher) {
        paymentsDao.deleteOrderPaymentIntent(orderIds.joinToString())
    }
}