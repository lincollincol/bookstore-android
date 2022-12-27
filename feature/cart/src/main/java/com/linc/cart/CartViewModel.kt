package com.linc.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.cart.navigation.CartNavigationState
import com.linc.data.repository.OrdersRepository
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.state.UiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    private val ordersRepository: OrdersRepository
) : ViewModel(), UiStateHolder<CartUiState>, RouteNavigator by defaultRouteNavigator {

    override val uiState: StateFlow<CartUiState> = ordersRepository.getBookOrdersStream()
        .catch { it.printStackTrace() }
        .map { bookOrders ->
            CartUiState(
                orders = bookOrders.map { it.toUiState() }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CartUiState()
        )

    fun selectOrder(orderId: String) {
        val bookId = rawUiState.orders.find { it.orderId == orderId }?.bookId ?: return
        navigateTo(CartNavigationState.BookDetails(bookId))
    }

    fun purchaseOrder(orderId: String) {

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

    fun purchaseAllOrders() {

    }

}