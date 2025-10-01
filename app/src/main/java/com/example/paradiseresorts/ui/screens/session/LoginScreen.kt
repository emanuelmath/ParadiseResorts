//Este archivo contiene la estructura y funcionalidad de la pantalla de inicio de sesión.
package com.example.paradiseresorts.ui.screens.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paradiseresorts.ui.components.AppColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.paradiseresorts.ui.theme.LocalAppColors
import com.example.paradiseresorts.ui.viewmodels.UserSessionViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    userSessionViewModel: UserSessionViewModel,
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val appColors = LocalAppColors.current
    val uiState = loginViewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //Verifica si isLoggedIn es true para llevar a cabo la navegación:
    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            uiState.dui?.let { dui ->
                userSessionViewModel.updateDui(dui) // 👈 sincroniza el estado global
            }
            onLoginSuccess()
        }
    }

    LaunchedEffect(key1 = uiState.errorMessage) {
        uiState.errorMessage?.let { msg ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = msg,
                    actionLabel = "Ok",
                    duration = SnackbarDuration.Short
                )
            }
            loginViewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding)
                .background(brush = Brush.linearGradient(colors = appColors.backgroundGradient))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Icono de retroceso",
                            tint = Color.White,
                            modifier = Modifier
                                .size(size = 64.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Inicia sesión",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 42.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Default
                        )
                    )
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    Text(
                        text = "con una cuenta existente",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Default
                        )
                    )
                }
                Spacer(modifier = Modifier.height(height = 36.dp))

                //Formulario de inicio de sesión:
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 360.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 24.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.height(height = 16.dp))

                        OutlinedTextField(
                            value = uiState.emailOrDUI,
                            onValueChange = loginViewModel::onEmailOrDUIChange,
                            label = { Text(text = "Correo Electrónico o DUI") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(height = 16.dp))

                        OutlinedTextField(
                            value = uiState.password,
                            onValueChange = loginViewModel::onPasswordChange,
                            label = { Text(text = "Contraseña") },
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(height = 16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { loginViewModel.startSession() },
                                enabled = !uiState.isLoading
                            ) {
                                if (uiState.isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(size = 20.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Text(text = "Iniciar sesión")
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(height = 16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "¿No tienes una cuenta?",
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(height = 8.dp))
                            TextButton(onClick = onRegisterClick) {
                                Text(
                                    text = "CREA UNA CUENTA",
                                    color = appColors.blueColor,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    //LoginScreen()
}
