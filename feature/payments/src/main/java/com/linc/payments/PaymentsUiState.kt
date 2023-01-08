package com.linc.payments

import com.linc.common.coroutines.extensions.DD_MM_YYYY
import com.linc.common.coroutines.extensions.format
import com.linc.model.BookOrder
import com.linc.ui.extensions.PRICE_WITH_CURRENCY_FORMAT
import com.linc.ui.state.UiState

data class PaymentsUiState(
    val orders: List<OrderItemUiState> = emptyList()
) : UiState

data class OrderItemUiState(
    val bookId: String,
    val orderId: String,
    val bookTitle: String,
    val bookImageUrl: String,
    val count: Int,
    val price: Double,
    val currency: String,
    val lastModifiedMillis: Long
) : UiState

val OrderItemUiState.totalPrice: Double get() = price * count

val OrderItemUiState.formattedTotalPrice: String get() =
    String.format(PRICE_WITH_CURRENCY_FORMAT, totalPrice, currency)

val OrderItemUiState.formattedLastModifiedDate: String get() =
    lastModifiedMillis.format(DD_MM_YYYY)

fun BookOrder.toUiState() = OrderItemUiState(
    orderId = order.id,
    bookId = book.id,
    count = order.count,
    bookTitle = book.title,
    bookImageUrl = book.hiqImageUrl,
    price = book.price,
    currency = book.currency,
    lastModifiedMillis = order.lastModifiedMillis
)