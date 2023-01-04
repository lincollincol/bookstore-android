package com.linc.payments

import androidx.lifecycle.ViewModel
import com.linc.data.repository.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val cardsRepository: CardsRepository
) : ViewModel() {

    fun addCard() {

    }

}