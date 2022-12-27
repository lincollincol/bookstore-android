package com.linc.cart.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.linc.cart.CartRoute
import com.linc.navigation.NavigationState

const val cartRouteGraph: String = "cart_route_graph"
const val cartRoute: String = "cart_route"

sealed interface CartNavigationState : NavigationState {
    data class BookDetails(val bookId: String) : CartNavigationState
}

fun NavController.navigateToCart(
    navOptions: NavOptions? = null
) {
    navigate(cartRoute, navOptions)
}

fun NavGraphBuilder.cartScreen(
    navigateToBookDetails: (String) -> Unit
) {
    composable(cartRoute) {
        CartRoute(
            navigateToBookDetails = navigateToBookDetails
        )
    }
}

fun NavGraphBuilder.cartGraph(
    navigateToBookDetails: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit = {}
) {
    navigation(
        route = cartRouteGraph,
        startDestination = cartRoute
    ) {
        cartScreen(
            navigateToBookDetails = navigateToBookDetails
        )
        nestedGraphs()
    }
}
