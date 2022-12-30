package com.linc.bookstore.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.linc.books.navigation.navigateToBooks
import com.linc.bookstore.navigation.BookstoreNavHost
import com.linc.bookstore.navigation.MenuDestinations
import com.linc.cart.navigation.navigateToCart
import com.linc.ui.components.SimpleIcon
import com.linc.navigation.currentRouteIsOneOf
import com.linc.preferences.navigation.navigateToPreferences

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BookstoreApp(

) {
    val navController = rememberNavController()
    val menuDestinations: List<MenuDestinations> = MenuDestinations.values().asList()
    val backStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = backStackEntry.currentRouteIsOneOf(*MenuDestinations.routes()),
                enter = slideInVertically(animationSpec = tween(200)) { it },
                exit = slideOutVertically(animationSpec = tween(100)) { it }
            ) {
                BookstoreNavigationBar(
                    destinations = menuDestinations,
                    currentDestination = backStackEntry?.destination,
                    onNavigateToDestination = {
                        val topLevelNavOptions = navOptions {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                        when (it) {
                            MenuDestinations.Books -> navController.navigateToBooks(topLevelNavOptions)
                            MenuDestinations.Cart -> navController.navigateToCart(topLevelNavOptions)
                            MenuDestinations.Preferences -> navController.navigateToPreferences(topLevelNavOptions)
                        }
                    }
                )
            }
        }
    ) { paddings ->
        BookstoreNavHost(
            navController = navController,
            modifier = Modifier
                .padding(paddings)
                .consumedWindowInsets(paddings)
        )
    }

}

@Composable
internal fun BookstoreNavigationBar(
    destinations: List<MenuDestinations>,
    onNavigateToDestination: (MenuDestinations) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
    ) {
        destinations.forEach {
            NavigationBarItem(
                selected = it == MenuDestinations.fromRoute(currentDestination?.route),
                icon = { SimpleIcon(icon = it.icon()) },
                label = { Text(it.title()) },
                onClick = { onNavigateToDestination(it) }
            )
        }
    }
}