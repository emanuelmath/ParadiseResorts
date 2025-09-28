package com.example.paradiseresorts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.paradiseresorts.ui.navigation.AppNavHost
import com.example.paradiseresorts.ui.screens.feedback.FeedbackViewModel
import com.example.paradiseresorts.ui.screens.feedback.FeedbackViewModelFactory
import com.example.paradiseresorts.ui.screens.home.HomeContentViewModel
import com.example.paradiseresorts.ui.screens.home.HomeContentViewModelFactory
import com.example.paradiseresorts.ui.screens.information.InformationViewModel
import com.example.paradiseresorts.ui.screens.information.InformationViewModelFactory
import com.example.paradiseresorts.ui.screens.profile.ProfileViewModel
import com.example.paradiseresorts.ui.screens.profile.ProfileViewModelFactory
import com.example.paradiseresorts.ui.screens.reservation.ReservationViewModel
import com.example.paradiseresorts.ui.screens.reservation.ReservationViewModelFactory
import com.example.paradiseresorts.ui.screens.services.ServicesViewModel
import com.example.paradiseresorts.ui.screens.services.ServicesViewModelFactory
import com.example.paradiseresorts.ui.screens.session.LoginViewModel
import com.example.paradiseresorts.ui.screens.session.LoginViewModelFactory
import com.example.paradiseresorts.ui.screens.session.RegisterViewModel
import com.example.paradiseresorts.ui.screens.session.RegisterViewModelFactory
import com.example.paradiseresorts.ui.screens.splash.SplashViewModel
import com.example.paradiseresorts.ui.screens.splash.SplashViewModelFactory
import com.example.paradiseresorts.ui.theme.ParadiseResortsTheme

class MainActivity : ComponentActivity() {

    lateinit var loginViewModel: LoginViewModel
    private set
    lateinit var registerViewModel: RegisterViewModel
        private set
    lateinit var splashViewModel: SplashViewModel
        private set
    lateinit var profileViewModel: ProfileViewModel
        private set
    lateinit var reservationViewModel: ReservationViewModel
        private set
    lateinit var servicesViewModel: ServicesViewModel
        private set
    lateinit var homeContentViewModel: HomeContentViewModel
        private set
    lateinit var informationViewModel: InformationViewModel
        private set
    lateinit var feedbackViewModel: FeedbackViewModel
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización de los ViewModels
        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory()
        )[LoginViewModel::class.java]

        registerViewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory()
        )[RegisterViewModel::class.java]

        splashViewModel = ViewModelProvider(
            this,
            SplashViewModelFactory()
        )[SplashViewModel::class.java]

        profileViewModel = ViewModelProvider(
            this,
            ProfileViewModelFactory()
        )[ProfileViewModel::class.java]

        reservationViewModel = ViewModelProvider(
            this,
            ReservationViewModelFactory()
        )[ReservationViewModel::class.java]

        servicesViewModel = ViewModelProvider(
            this,
            ServicesViewModelFactory()
        )[ServicesViewModel::class.java]

        homeContentViewModel = ViewModelProvider(
            this,
            HomeContentViewModelFactory()
        )[HomeContentViewModel::class.java]

        informationViewModel = ViewModelProvider(
            this,
            InformationViewModelFactory()
        )[InformationViewModel::class.java]

        feedbackViewModel = ViewModelProvider(
            this,
            FeedbackViewModelFactory()
        )[FeedbackViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            ParadiseResortsTheme {
                ParadiseResortsApplication(
                    loginViewModel,
                    registerViewModel,
                    splashViewModel,
                    profileViewModel,
                    reservationViewModel,
                    servicesViewModel,
                    homeContentViewModel,
                    informationViewModel,
                    feedbackViewModel
                )
            }
        }
    }
}

@Composable
fun ParadiseResortsApplication(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    splashViewModel: SplashViewModel,
    profileViewModel: ProfileViewModel,
    reservationViewModel: ReservationViewModel,
    servicesViewModel: ServicesViewModel,
    homeContentViewModel: HomeContentViewModel,
    informationViewModel: InformationViewModel,
    feedbackViewModel: FeedbackViewModel
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    AppNavHost(
        navController,
        loginViewModel,
        registerViewModel,
        splashViewModel,
        profileViewModel,
        reservationViewModel,
        servicesViewModel,
        homeContentViewModel,
        informationViewModel,
        feedbackViewModel
    )
}