package com.linc.cart

import androidx.compose.ui.util.fastSumBy
import com.linc.data.model.asExternalModel
import com.linc.model.BookOrder
import com.linc.ui.extensions.PRICE_WITH_CURRENCY_FORMAT
import com.linc.ui.state.UiState
import com.stripe.android.model.ConfirmPaymentIntentParams

data class CartUiState(
    val paymentClientSecret: String? = null,
    val orders: List<OrderItemUiState> = emptyList(),
) : UiState

data class OrderItemUiState(
    val bookId: String,
    val orderId: String,
    val bookTitle: String,
    val bookImageUrl: String,
    val count: Int,
    val price: Double,
    val currency: String,
) : UiState

val OrderItemUiState.totalPrice: Double get() = price * count

val OrderItemUiState.formattedTotalPrice: String get() =
    String.format(PRICE_WITH_CURRENCY_FORMAT, totalPrice, currency)

val CartUiState.totalPrice: Double get() = orders.sumOf { it.totalPrice }

val CartUiState.ordersCount: Int get() = orders.count()

val CartUiState.ordersCurrency: String get() = orders
    .map(OrderItemUiState::currency).firstOrNull(String::isNotEmpty).orEmpty()

val CartUiState.formattedTotalPrice: String get() =
    String.format(PRICE_WITH_CURRENCY_FORMAT, totalPrice, ordersCurrency)

fun BookOrder.toUiState() = OrderItemUiState(
    orderId = order.id,
    bookId = book.id,
    count = order.count,
    bookTitle = book.title,
    bookImageUrl = book.hiqImageUrl,
    price = book.price,
    currency = book.currency
)