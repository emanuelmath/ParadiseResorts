package com.example.paradiseresorts.ui.navigation

sealed class HomeRoute(val route: String) {
    object HomeContent : HomeRoute("home_content")
    object Services : HomeRoute("services")
    object Reservations : HomeRoute("reservations")
    object Information : HomeRoute("information")
    object Feedback : HomeRoute("feedback")
}
