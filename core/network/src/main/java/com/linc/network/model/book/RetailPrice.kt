package com.linc.network.model.book

data class RetailPrice(
    val amount: Double?,
    val amountInMicros: Double?,
    val currencyCode: String
) {
    val productPrice: Int get() = (amount ?: amountInMicros ?: 0.0).toInt()
}