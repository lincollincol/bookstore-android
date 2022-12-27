package com.linc.cart

import androidx.compose.ui.util.fastSumBy
import com.linc.data.model.asExternalModel
import com.linc.model.BookOrder
import com.linc.ui.extensions.PRICE_WITH_CURRENCY_FORMAT
import com.linc.ui.state.UiState

data class CartUiState(
    val orders: List<OrderItemUiState> = emptyList(),
) : UiState

data class OrderItemUiState(
    val id: String,
    val bookTitle: String,
    val bookImageUrl: String,
    val count: Int,
    val price: Double,
    val currency: String,
) : UiState

val OrderItemUiState.totalPrice: Double get() = price * count

val OrderItemUiState.formattedPrice: String get() =
    String.format(PRICE_WITH_CURRENCY_FORMAT, price, currency)

val OrderItemUiState.formattedTotalPrice: String get() =
    String.format(PRICE_WITH_CURRENCY_FORMAT, totalPrice, currency)

val CartUiState.totalPrice: Double get() = orders.sumOf { it.totalPrice }

val CartUiState.ordersCount: Int get() = orders.count()

val CartUiState.ordersCurrency: String get() = orders
    .map(OrderItemUiState::currency).firstOrNull(String::isNotEmpty).orEmpty()

val CartUiState.formattedTotalPrice: String get() =
    String.format(PRICE_WITH_CURRENCY_FORMAT, totalPrice, ordersCurrency)

fun BookOrder.toUiState() = OrderItemUiState(
    id = order.id,
    count = order.count,
    bookTitle = book.title,
    bookImageUrl = book.hiqImageUrl,
    price = book.price,
    currency = book.currency
)