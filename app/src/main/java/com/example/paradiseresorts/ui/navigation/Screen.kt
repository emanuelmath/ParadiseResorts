package com.example.paradiseresorts.ui.navigation

sealed class Screen(val route: String) {
    object Login: Screen("Login")
}