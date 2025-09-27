package com.example.paradiseresorts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.paradiseresorts.ui.navigation.AppNavHost
import com.example.paradiseresorts.ui.screens.session.LoginViewModel
import com.example.paradiseresorts.ui.screens.session.LoginViewModelFactory
import com.example.paradiseresorts.ui.screens.session.RegisterViewModel
import com.example.paradiseresorts.ui.screens.session.RegisterViewModelFactory
import com.example.paradiseresorts.ui.theme.ParadiseResortsTheme

class MainActivity : ComponentActivity() { lateinit var loginViewModel: LoginViewModel
    private set

    lateinit var registerViewModel: RegisterViewModel
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

        enableEdgeToEdge()
        setContent {
            ParadiseResortsTheme {
                ParadiseResortsApplication(loginViewModel, registerViewModel)
            }
        }
    }
}

@Composable
fun ParadiseResortsApplication(loginViewModel: LoginViewModel, registerViewModel: RegisterViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    AppNavHost(navController, loginViewModel, registerViewModel)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ParadiseResortsTheme {
        Greeting("Android")
    }
}