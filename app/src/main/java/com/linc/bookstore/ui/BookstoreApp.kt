package com.linc.bookstore.ui

import android.view.Menu
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
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
    var currentDestination: MenuDestinations by remember {
        mutableStateOf(MenuDestinations.BOOKS)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BookstoreNavigationBar(
                destinations = menuDestinations,
                currentDestination = currentDestination,
                onNavigateToDestination = {
                    currentDestination = it
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

                    when (currentDestination) {
                        MenuDestinations.BOOKS -> navController.navigateToBooks(topLevelNavOptions)
                        MenuDestinations.CART -> navController.navigateToCart(topLevelNavOptions)
                    }
                }
            )
        }
    ) { paddings ->
        BookstoreNavHost(
            navController = navController,
            modifier = Modifier.padding(paddings)
                .consumedWindowInsets(paddings)
        )
        paddings.calculateTopPadding()
    }

}

@Composable
internal fun BookstoreNavigationBar(
    destinations: List<MenuDestinations>,
    onNavigateToDestination: (MenuDestinations) -> Unit,
    currentDestination: MenuDestinations,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
    ) {
        destinations.forEach {
            NavigationBarItem(
                selected = it == currentDestination,
                icon = { SimpleIcon(icon = it.icon) },
                label = { Text(stringResource(it.iconTextId)) },
                onClick = { onNavigateToDestination(it) }
            )
        }
    }
}