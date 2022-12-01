package com.linc.bookstore.ui

import android.view.Menu
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.linc.books.navigation.navigateToBooks
import com.linc.bookstore.navigation.BookstoreNavHost
import com.linc.bookstore.navigation.MenuDestinations
import com.linc.cart.navigation.navigateToCart
import com.linc.designsystem.component.SimpleIcon
import com.linc.designsystem.icon.IconWrapper

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BookstoreApp() {
    val navController = rememberNavController()
    val menuDestinations: List<MenuDestinations> = MenuDestinations.values().asList()
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = MenuDestinations.isTopLevelDestination(backStackEntry?.destination?.route),
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
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
                            MenuDestinations.BOOKS -> navController.navigateToBooks(topLevelNavOptions)
                            MenuDestinations.CART -> navController.navigateToCart(topLevelNavOptions)
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
                icon = { SimpleIcon(icon = it.icon) },
                label = { Text(stringResource(it.iconTextId)) },
                onClick = { onNavigateToDestination(it) }
            )
        }
    }
}