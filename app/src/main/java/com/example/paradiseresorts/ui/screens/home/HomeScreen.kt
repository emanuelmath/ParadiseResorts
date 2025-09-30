/*Este archivo contiene los siguientes elementos:
* HomeScreen: Es un punto de entrada de la aplicación que contiene las top y bottom bars así como
* la navegación dinámica de cada una de las pantallas. Esto con la finalidad de hacer una navegación
* interna y privada de modo que las bars se dibujen una única vez al ingresar a la pantalla Home.
*
* HomeContentScreen: Este es el composable que estructra la pantalla de home.
* */

package com.example.paradiseresorts.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

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
                ServicesScreen(servicesViewModel, reservationViewModel, userSessionViewModel)
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

@Composable
fun HomeContentScreen(
    userSessionViewModel: UserSessionViewModel,
    homeContentViewModel: HomeContentViewModel = viewModel(factory = HomeContentViewModelFactory())
) {
    val dui = userSessionViewModel.dui
    val uiState = homeContentViewModel.uiState
    var showDialog by remember { mutableStateOf(false) }
    var amountInput by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Cargar datos al iniciar
    LaunchedEffect(dui) {
        dui?.let {
            homeContentViewModel.obtainCurrenUser(it)
            homeContentViewModel.obtainCurrentReservationsOfUser(it)
            homeContentViewModel.obtainServices(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 🔹 Bienvenida
            item {
                Text(
                    text = "Bienvenido ${uiState.currentUser?.name ?: ""}",
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6A0DAD), // púrpura
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Balance del usuario
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 160.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8E21C))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Tu balance", color = Color.Black, fontWeight = FontWeight.Bold)

                        val formattedBalance = remember(uiState.currentUser?.balance) {
                            NumberFormat.getCurrencyInstance(Locale.US)
                                .format(uiState.currentUser?.balance ?: 0.0)
                        }

                        Text(
                            text = formattedBalance,
                            fontSize = 64.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    val card = dui?.let { homeContentViewModel.getCardForUser(it) }
                                    if (card != null) {
                                        showDialog = true
                                    } else {
                                        snackbarHostState.showSnackbar("No tienes tarjeta asociada")
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "Recargar",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Recargar saldo", color = Color.White)
                        }
                    }
                }
            }

            // Reservas actuales
            item {
                Text(
                    text = "Tus reservas actuales",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF001F54) // azul oscuro
                )
            }
            if (uiState.reservationsOfUser.isNullOrEmpty()) {
                item {
                    Text("No tienes reservas activas", color = Color.Gray)
                }
            } else {
                items(uiState.reservationsOfUser!!) { reservation ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF001F54))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(reservation.dui, color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Expira: ${reservation.expirationDate}", color = Color.White)
                        }
                    }
                }
            }

            // Servicios
            item {
                Text(
                    text = "Tus servicios",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            if (uiState.servicesOfUser.isNullOrEmpty()) {
                item {
                    Text("No tienes servicios activos", color = Color.Gray)
                }
            } else {
                items(uiState.servicesOfUser!!) { service ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF5851DB))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(service.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Dialog de recarga de saldo
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Recargar saldo") },
                text = {
                    Column {
                        Text("¿Cuánto deseas transferir de tu tarjeta?")
                        OutlinedTextField(
                            value = amountInput,
                            onValueChange = { amountInput = it },
                            label = { Text("Monto") }
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        val amount = amountInput.toDoubleOrNull() ?: 0.0
                        dui?.let {
                            homeContentViewModel.rechargeBalance(it, amount) { success, message ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(message)
                                }
                                if (success) {
                                    showDialog = false
                                    amountInput = ""
                                }
                            }
                        }
                    }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}


