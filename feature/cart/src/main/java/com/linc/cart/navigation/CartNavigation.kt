package com.linc.cart.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.linc.cart.CartRoute

const val cartRouteGraph: String = "cart_route_graph"
const val cartRoute: String = "cart_route"

fun NavController.navigateToCart(
    navOptions: NavOptions? = null
) {
    navigate(cartRoute, navOptions)
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
