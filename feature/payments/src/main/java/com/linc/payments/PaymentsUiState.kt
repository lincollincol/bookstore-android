package com.linc.payments

import com.linc.ui.state.UiState

 class CardsUiState(
) : UiState

data class NewCardUiState(
    val number: String,
    val monthYear: String,
    val cvc: String,
) : UiState