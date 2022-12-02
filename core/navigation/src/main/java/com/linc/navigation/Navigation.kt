package com.linc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun RouteNavigator.observeNavigation(onStateChange: (NavigationState) -> Unit) {
    val navState by navigationState.collectAsStateWithLifecycle()
    onStateChange(navState)
    onNavigated(navState)
}