package com.linc.cart.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.linc.cart.CartRoute

const val cartRouteGraph: String = "cart_route_graph"
const val cartRoute: String = "cart_route"

fun NavController.navigateToCart(navOptions: NavOptions? = null) {
    this.navigate(cartRoute, navOptions)
}

fun NavGraphBuilder.cartScreen() {
    composable(cartRoute) {
        CartRoute()
    }
}

fun NavGraphBuilder.cartGraph(
    nestedGraphs: NavGraphBuilder.() -> Unit = {}
) {
    navigation(
        route = cartRouteGraph,
        startDestination = cartRoute
    ) {
        cartScreen()
        nestedGraphs()
    }
}
