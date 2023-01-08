package com.linc.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.cart.navigation.CartNavigationState
import com.linc.data.repository.OrdersRepository
import com.linc.data.repository.PaymentsRepository
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.state.UiStateHolder
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val ordersRepository: OrdersRepository,
    private val paymentsRepository: PaymentsRepository
) : ViewModel(), UiStateHolder<CartUiState>, RouteNavigator by defaultRouteNavigator {

    private var orderToPayIds: List<String> = listOf()

    private val paymentClientSecret = MutableStateFlow<String?>(null)

    override val uiState: StateFlow<CartUiState> = combine(
        paymentClientSecret,
        ordersRepository.getActiveBookOrdersStream()
    ) { paymentClientSecret, bookOrders ->
        CartUiState(
            paymentClientSecret = paymentClientSecret,
            orders = bookOrders.map { it.toUiState() }
        )
    }
        .catch { it.printStackTrace() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CartUiState()
        )

    fun selectOrder(orderId: String) {
        val bookId = rawUiState.orders.find { it.orderId == orderId }?.bookId ?: return
        navigateTo(CartNavigationState.BookDetails(bookId))
    }

    fun purchaseAllOrders() = createOrdersPayment(rawUiState.orders.map(OrderItemUiState::orderId))

    fun purchaseOrder(orderId: String) = createOrdersPayment(listOf(orderId))

    private fun createOrdersPayment(orderIds: List<String>) {
        viewModelScope.launch {
            try {
                val paymentSecret = with(paymentsRepository) {
                    createOrdersPayment(orderIds)
                    getOrdersPaymentClientSecret(orderIds)
                } ?: return@launch
                orderToPayIds = orderIds
                paymentClientSecret.update { paymentSecret }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun cancelOrder(orderId: String) {
        viewModelScope.launch {
            try {
                ordersRepository.deleteOrder(orderId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun handlePaymentResult(result: PaymentSheetResult) {
        when(result) {
            PaymentSheetResult.Completed -> completeOrderPayment()
            is PaymentSheetResult.Failed -> println("Payment: Failed(${result.error.message})")
            else -> { /* ignored */ }
        }
    }

    fun onPaymentConfirmed() {
        paymentClientSecret.update { null }
    }

    private fun completeOrderPayment() {
        viewModelScope.launch {
            try {
                ordersRepository.makeOrdersActive(orderToPayIds, false)
                paymentsRepository.deleteOrdersPaymentIntent(orderToPayIds)
                orderToPayIds = listOf()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}