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

    suspend fun createSingleOrderPayment(orderId: String) = withContext(ioDispatcher) {
        val localPaymentIntent = paymentsDao.getPaymentIntentByOrderId(orderId)
        val customer = paymentsDao.getCustomer()
        if(localPaymentIntent != null || customer == null) {
            return@withContext
        }
        refreshCustomerEphemeralKey()
        val order = ordersDao.getOrderAndBookById(orderId) ?: return@withContext
        val paymentIntent = stripeApiService.createPaymentIntent(
            customerId = customer.customerId,
            amount = (order.book.price * 100).roundToInt(),
            currency = order.book.currency.lowercase(),
            description = orderId,
            autoPaymentMethodsEnable = true
        )
        paymentsDao.insertPaymentIntent(paymentIntent.asEntity(orderId))
    }

    suspend fun getOrderPaymentClientSecret(orderId: String): String? = withContext(ioDispatcher) {
        return@withContext paymentsDao.getPaymentIntentByOrderId(orderId)?.clientSecret
    }

    suspend fun deleteOrderPaymentIntent(orderId: String) = withContext(ioDispatcher) {
        paymentsDao.deleteOrderPaymentIntent(orderId)
    }
}