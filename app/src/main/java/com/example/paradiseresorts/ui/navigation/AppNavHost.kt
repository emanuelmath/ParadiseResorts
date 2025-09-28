//Esta es la definición de la navegación ente las pantallas de la aplicación.
package com.example.paradiseresorts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.paradiseresorts.ui.components.AppColors
import com.example.paradiseresorts.ui.screens.feedback.FeedbackScreen
import com.example.paradiseresorts.ui.screens.home.HomeScreen
import com.example.paradiseresorts.ui.screens.information.InformationScreen
import com.example.paradiseresorts.ui.screens.profile.ProfileScreen
import com.example.paradiseresorts.ui.screens.reservation.ReservationScreen
import com.example.paradiseresorts.ui.screens.services.ServicesScreen
import com.example.paradiseresorts.ui.screens.splash.SplashScreen
import com.example.paradiseresorts.ui.screens.start.StartScreen
import com.example.paradiseresorts.ui.screens.session.LoginScreen
import com.example.paradiseresorts.ui.screens.session.LoginViewModel
import com.example.paradiseresorts.ui.screens.session.RegisterScreen
import com.example.paradiseresorts.ui.screens.session.RegisterViewModel
import com.example.paradiseresorts.ui.screens.splash.SplashViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    splashViewModel: SplashViewModel
) {
    val scope = rememberCoroutineScope()
    val appColors = AppColors()

    //Handler de navegación
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // -- SECCIÓN DE PANTALLAS DE ACCESO PÚBLICO

        //Pantalla de carga:
        composable(route = Screen.Splash.route) {
            SplashScreen(
                splashViewModel = splashViewModel,
                onSessionActive = {
                    navController.navigate(route = Screen.Home.route) {
                        popUpTo(id = 0) {inclusive = true}
                    }
                },
                onSessionRequired = {
                    navController.navigate(route = Screen.Start.route) {
                        popUpTo(id = 0) {inclusive = true}
                    }
                },
                navController = navController,
                splashTime = 3000L
            )
        }

        //Pantalla de inicio:
        composable(route = Screen.Start.route) {
            StartScreen(
                onLoginClick = {
                    navController.navigate(route = Screen.Login.route)
                },
                onRegisterClick = {
                    navController.navigate(route = Screen.Register.route)
                }
            )
        }

        //Pantalla de login:
        composable(route = Screen.Login.route) {
            LoginScreen(
                loginViewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(route = Screen.Home.route) {
                        popUpTo(id = 0) {inclusive = true}
                    }
                },
                onBackClick = {
                    navController.navigate(route = Screen.Start.route) {
                        popUpTo(id = 0) {inclusive = true}
                    }
                },
                onRegisterClick = {
                    navController.navigate(route = Screen.Register.route)
                }
            )
        }

        //Pantalla de registro:
        composable(route = Screen.Register.route) {
            RegisterScreen(
                registerViewModel = registerViewModel,
                onRegisterSuccess = {
                    /*Agregar lógica de registro exitoso*/
                },
                onBackClick = {
                    navController.navigate(route = Screen.Start.route) {
                        popUpTo(id = 0) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(route = Screen.Login.route)
                }
            )
        }

        // -- SECCIÓN DE PANTALLAS PRIVADAS

        //Pantalla de Home:
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController
            )
        }

        //Pantalla de Perfil de usuario:
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onLogoutClick = {
                    navController.navigate(route = Screen.Start.route) {
                        popUpTo(id = 0) { inclusive = true }
                    }
                }
            )
        }

        //Pantalla de Reservaciones / Hacer reservaciones:
        composable(route = Screen.Reservation.route) {
            ReservationScreen()
        }

        //Pantalla de Información de hoteles:
        composable(route = Screen.Information.route) {
            InformationScreen()
        }

        //Pantalla de Feedback y valoraciones de usuarios:
        composable(route = Screen.Feedback.route) {
            FeedbackScreen()
        }

        //Pantalla de servicios extras:
        composable(route = Screen.Services.route) {
            ServicesScreen()
        }
    }
}