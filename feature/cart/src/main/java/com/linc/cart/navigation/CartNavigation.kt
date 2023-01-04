package com.linc.cart.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.*
import com.linc.cart.CartRoute
import com.linc.navigation.*
import soup.compose.material.motion.navigation.composable

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

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.cartScreen(
    navigateToBookDetails: (String) -> Unit
) {
    composable(
        route = cartRoute,
        deepLinks = listOf(navDeepLink { uriPattern = "$DEEPLINK_URI/cart" }),
        enterTransition = defaultHostEnterTransition,
        exitTransition = defaultHostExitTransition
    ) {
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
