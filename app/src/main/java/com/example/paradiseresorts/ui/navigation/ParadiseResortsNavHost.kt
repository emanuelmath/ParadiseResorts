package com.example.paradiseresorts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun ParadiseResortsNavHost(navHostController: NavHostController, startDestination: String) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(Screen.Login.route) {

        }
    }
}