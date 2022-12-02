package com.linc.navigation

interface NavigationState {
    object Idle : NavigationState
    object Back : NavigationState
}
