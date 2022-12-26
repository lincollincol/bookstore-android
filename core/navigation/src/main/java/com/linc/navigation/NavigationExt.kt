package com.linc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.*


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RouteNavigator.observeNavigation(onStateChange: (NavigationState) -> Unit) {
    val navState by navigationState.collectAsStateWithLifecycle()
    onStateChange(navState)
    onNavigated(navState)
}

fun NavBackStackEntry?.currentRouteEquals(route: String?): Boolean =
    route?.let { this?.destination?.route?.startsWith(it, ignoreCase = true) } ?: false

fun NavBackStackEntry?.currentRouteIsOneOf(vararg routes: String): Boolean =
    routes.find{ currentRouteEquals(it) } != null