package com.linc.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linc.data.repository.OrdersRepository
import com.linc.model.BookOrder
import com.linc.navigation.DefaultRouteNavigator
import com.linc.navigation.RouteNavigator
import com.linc.ui.state.UiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    defaultRouteNavigator: DefaultRouteNavigator,
    ordersRepository: OrdersRepository
) : ViewModel(), UiStateHolder<PaymentsUiState>, RouteNavigator by defaultRouteNavigator {
    override val uiState: StateFlow<PaymentsUiState> = ordersRepository.getInactiveBookOrdersStream()
        .map {
            PaymentsUiState(orders = it.map(BookOrder::toUiState))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PaymentsUiState()
        )
}