package com.linc.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.data.repository.OrdersRepository
import com.linc.ui.state.UiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository
) : ViewModel(), UiStateHolder<CartUiState> {

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

}