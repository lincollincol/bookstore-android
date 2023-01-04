package com.linc.data.repository

import com.linc.network.api.StripeApiService
import javax.inject.Inject

class CardsRepository @Inject constructor(
    private val stripeApiService: StripeApiService
) {
}