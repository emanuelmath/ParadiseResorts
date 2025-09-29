/*Este archivo contiene los siguientes elementos:
* HomeScreen: Es un punto de entrada de la aplicación que contiene las top y bottom bars así como
* la navegación dinámica de cada una de las pantallas. Esto con la finalidad de hacer una navegación
* interna y privada de modo que las bars se dibujen una única vez al ingresar a la pantalla Home.
*
* HomeContentScreen: Este es el composable que estructra la pantalla de home.
* */

package com.example.paradiseresorts.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paradiseresorts.ui.components.actionbars.TopBar
import com.example.paradiseresorts.ui.components.actionbars.BottomBar
import com.example.paradiseresorts.ui.navigation.Screen
import com.example.paradiseresorts.ui.navigation.HomeRoute
import com.example.paradiseresorts.ui.screens.feedback.FeedbackScreen
import com.example.paradiseresorts.ui.screens.feedback.FeedbackViewModel
import com.example.paradiseresorts.ui.screens.reservation.ReservationScreen
import com.example.paradiseresorts.ui.screens.services.ServicesScreen
import com.example.paradiseresorts.ui.screens.information.InformationScreen
import com.example.paradiseresorts.ui.screens.information.InformationViewModel
import com.example.paradiseresorts.ui.screens.reservation.ReservationViewModel
import com.example.paradiseresorts.ui.screens.services.ServicesViewModel
import com.example.paradiseresorts.ui.viewmodels.UserSessionViewModel

// Composable que maneja la navegación interna de la aplicación:
@Composable
fun HomeScreen(
    navController: NavHostController,
    reservationViewModel: ReservationViewModel,
    servicesViewModel: ServicesViewModel,
    homeContentViewModel: HomeContentViewModel,
    informationViewModel: InformationViewModel,
    feedbackViewModel: FeedbackViewModel,
    userSessionViewModel: UserSessionViewModel
) {
    val homeNavController = rememberNavController()
    val uiState = homeContentViewModel.uiState
    val dui = userSessionViewModel.dui

    Scaffold(
        topBar = {
            TopBar(onUserClick = {
                navController.navigate(Screen.Profile.route)
            })
        },
        bottomBar = {
            //Definición de rutas internas provenientes de ui.nativation.HomeRoute
            BottomBar(
                onHomeClick = { homeNavController.navigate(HomeRoute.HomeContent.route) },
                onServicesClick = { homeNavController.navigate(HomeRoute.Services.route) },
                onReservationsClick = { homeNavController.navigate(HomeRoute.Reservations.route) },
                onInformationClick = { homeNavController.navigate(HomeRoute.Information.route) },
                onFeedbackClick = { homeNavController.navigate(HomeRoute.Feedback.route) }
            )
        }
    ) { innerPadding -> //Contenedor de navegación dinámica entre pantallas:
        NavHost(
            navController = homeNavController,
            startDestination = HomeRoute.HomeContent.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HomeRoute.HomeContent.route) {
                HomeContentScreen(userSessionViewModel, homeContentViewModel)
            }
            composable(HomeRoute.Services.route) {
                ServicesScreen(servicesViewModel)
            }
            composable(HomeRoute.Reservations.route) {
                ReservationScreen(reservationViewModel)
            }
            composable(HomeRoute.Information.route) {
                InformationScreen(informationViewModel)
            }
            composable(HomeRoute.Feedback.route) {
                FeedbackScreen(feedbackViewModel)
            }
        }
    }
}

// Composable del diseño de la pantalla Home:
@Composable
fun HomeContentScreen(
    userSessionViewModel: UserSessionViewModel,
    homeContentViewModel: HomeContentViewModel
) {
    Log.d("DUIUSVM", "DUI: ${userSessionViewModel.dui}")
    val dui = userSessionViewModel.dui!!
    val uiState = homeContentViewModel.uiState

    LaunchedEffect(dui) {
        homeContentViewModel.obtainCurrenUser(dui)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Bienvenido a pantalla HOME, ${uiState.currentUser?.name}",
            style = MaterialTheme.typography.headlineMedium
        )
        //Elementos de relleno para comprobar la pantalla scrollable
        repeat(40) { index ->
            Text("Elemento #$index", modifier = Modifier.padding(4.dp))
        }
    }
}
