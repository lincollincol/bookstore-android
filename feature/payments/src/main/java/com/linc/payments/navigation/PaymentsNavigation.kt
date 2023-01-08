package com.linc.payments.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.linc.navigation.defaultAxisZEnterTransition
import com.linc.navigation.defaultAxisZExitTransition
import com.linc.payments.PaymentsRoute
import soup.compose.material.motion.navigation.composable

const val paymentsRoute: String = "payments_route"

fun NavController.navigateToPayments(navOptions: NavOptions? = null) {
    navigate(paymentsRoute, navOptions)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.paymentsScreen(
    navigateBack: () -> Unit
) {
    composable(
        route = paymentsRoute,
        enterTransition = defaultAxisZEnterTransition,
        exitTransition = defaultAxisZExitTransition
    ) {
        PaymentsRoute(navigateBack = navigateBack)
    }
}