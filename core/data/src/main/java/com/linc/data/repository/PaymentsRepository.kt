package com.linc.data.repository

import com.linc.common.coroutines.AppDispatchers
import com.linc.common.coroutines.Dispatcher
import com.linc.database.dao.PaymentsDao
import com.linc.database.dao.UsersDao
import com.linc.database.entity.payment.CustomerEntity
import com.linc.network.api.StripeApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaymentsRepository @Inject constructor(
    private val stripeApiService: StripeApiService,
    private val paymentsDao: PaymentsDao,
    private val usersDao: UsersDao,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun createCustomer() = withContext(ioDispatcher) {
        val user = usersDao.getUser() ?: return@withContext
        val customer = stripeApiService.createCustomer(user.name)
        paymentsDao.insertCustomer(
            CustomerEntity(
                customerId = customer.id,
                userId = user.userId
            )
        )
    }

    /*suspend fun refreshEphemeralKeys(customerId: String) {
        val request = Request.Builder()
            .url("https://api.stripe.com/v1/ephemeral_keys?customer=$customerId")
            .addHeader("Authorization", "Bearer ${Constants.SECRET_KEY}")
            .addHeader("Stripe-Version", "2022-08-01")
            .post(EMPTY_REQUEST)
            .build()
        val response = okHttpClient.newCall(request).execute()
        val ephemeralKey = JSONObject(response.body?.string())
        val secret = ephemeralKey.getString("secret")
        val expires = ephemeralKey.getLong("expires")
        authPreferences.run {
            putEphemeralSecret(secret)
            putEphemeralSecretExpiration(expires)
        }
    }

    suspend fun createPayment(
        customerId: String,
        amount: Int,
        currency: String = "eur",
        automaticPaymentMethod: Boolean = true
    ): String {
        val keyExpired = authPreferences.getEphemeralSecretExpiration() < System.currentTimeMillis() / 1000
        if(keyExpired) {
            refreshEphemeralKeys(customerId)
        }
        val request = Request.Builder()
            .url("https://api.stripe.com/v1/payment_intents?customer=$customerId&amount=$amount&currency=$currency&automatic_payment_methods[enabled]=$automaticPaymentMethod")
            .addHeader("Authorization", "Bearer ${Constants.SECRET_KEY}")
            .post(EMPTY_REQUEST)
            .build()
        val response = okHttpClient.newCall(request).execute()
        val paymentSecret = JSONObject(response.body?.string()).getString("client_secret")
        return paymentSecret
    }*/

}