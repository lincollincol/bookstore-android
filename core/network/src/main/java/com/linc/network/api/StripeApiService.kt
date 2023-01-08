package com.linc.network.api

import com.linc.network.BuildConfig
import com.linc.network.model.stripe.customer.CustomerApiModel
import com.linc.network.model.stripe.intent.PaymentIntentApiModel
import com.linc.network.model.stripe.key.EphemeralKeyApiModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

private const val SECRET = BuildConfig.STRIPE_SECRET_KEY

interface StripeApiService {

    @Headers(
        "Authorization: Bearer $SECRET",
        "Stripe-Version: 2022-08-01"
    )
    @POST("v1/customers")
    suspend fun createCustomer(
        @Query("name") name: String
    ) : CustomerApiModel

    @Headers(
        "Authorization: Bearer $SECRET",
        "Stripe-Version: 2022-08-01"
    )
    @POST("v1/ephemeral_keys")
    suspend fun createEphemeralKey(
        @Query("customer") customerId: String
    ): EphemeralKeyApiModel

    @Headers(
        "Authorization: Bearer $SECRET"
    )
    @POST("v1/payment_intents")
    suspend fun createPaymentIntent(
        @Query("customer") customerId: String,
        @Query("amount") amount: Int,
        @Query("currency") currency: String,
        @Query("description") description: String,
        @Query("automatic_payment_methods[enabled]") autoPaymentMethodsEnable: Boolean,
    ): PaymentIntentApiModel

}