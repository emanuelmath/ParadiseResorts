// Esta clase contiene la declaración de la ruta de cada pantalla para utilizarla en navegación.
package com.example.paradiseresorts.ui.navigation

sealed class Screen(val route: String) {
    //Pantallas de acceso público.
    object Splash: Screen(route = "splash")
    object Start: Screen(route = "start")
    object Login: Screen(route = "login")
    object Register: Screen(route = "register")

    //Pantallas privadas.
    object Home: Screen(route = "home")
    object Profile: Screen(route = "profile")
    object Services: Screen(route = "services")
    object Information: Screen(route = "information")
    object Reservation: Screen(route = "reservation")
    object Feedback: Screen(route = "feedback")
}