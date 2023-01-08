package com.linc.data.model

import com.linc.database.entity.payment.CustomerEntity
import com.linc.database.entity.payment.EphemeralKeyEntity
import com.linc.database.entity.payment.PaymentIntentEntity
import com.linc.network.model.stripe.customer.CustomerApiModel
import com.linc.network.model.stripe.intent.PaymentIntentApiModel
import com.linc.network.model.stripe.key.EphemeralKeyApiModel

fun EphemeralKeyApiModel.asEntity() = EphemeralKeyEntity(
    keyId = id,
    secret = secret,
    expires = expires * 1000L
)

fun CustomerApiModel.asEntity(userId: String) = CustomerEntity(
    customerId = id,
    userId = userId
)

fun PaymentIntentApiModel.asEntity(orderId: String) = PaymentIntentEntity(
    paymentId = id,
    orderId = orderId,
    clientSecret = client_secret,
)