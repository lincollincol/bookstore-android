package com.linc.navigation

import android.content.Intent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.*
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.animation.materialSharedAxisZIn
import soup.compose.material.motion.animation.materialSharedAxisZOut

const val DEEPLINK_URI = "app://bookstore"

private const val HOST_SLIDE_DISTANCE = 75
private const val MEDIUM_AXIS_Z_SLIDE_DISTANCE = 750

@OptIn(ExperimentalAnimationApi::class)
val defaultHostEnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?
        = { materialSharedAxisXIn(false, HOST_SLIDE_DISTANCE) }

@OptIn(ExperimentalAnimationApi::class)
val defaultHostExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?
        = { materialSharedAxisXOut(false, HOST_SLIDE_DISTANCE) }

@OptIn(ExperimentalAnimationApi::class)
val defaultAxisZEnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?
        = { materialSharedAxisZIn(true, MEDIUM_AXIS_Z_SLIDE_DISTANCE) }

@OptIn(ExperimentalAnimationApi::class)
val defaultAxisZExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?
        = { materialSharedAxisZOut(false, MEDIUM_AXIS_Z_SLIDE_DISTANCE) }


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RouteNavigator.observeNavigation(onStateChange: (NavigationState) -> Unit) {
    val navState by navigationState.collectAsStateWithLifecycle()
    onStateChange(navState)
    onNavigated(navState)
}

fun NavHostController.navigate(intent: Intent) = context.startActivity(intent)

fun NavBackStackEntry?.currentRouteEquals(route: String?): Boolean =
    route?.let { this?.destination?.route?.startsWith(it, ignoreCase = true) } ?: false

fun NavBackStackEntry?.currentRouteIsOneOf(vararg routes: String): Boolean =
    routes.find{ currentRouteEquals(it) } != null