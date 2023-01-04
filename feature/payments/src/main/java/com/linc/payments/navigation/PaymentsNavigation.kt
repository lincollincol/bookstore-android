package com.linc.payments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.linc.payments.CardsRoute

const val paymentsRoute: String = "payments_route"

fun NavController.navigateToPayments(navOptions: NavOptions? = null) {
    navigate(paymentsRoute, navOptions)
}

fun NavGraphBuilder.paymentsScreen() {
    composable(paymentsRoute) {
        CardsRoute()
    }
}