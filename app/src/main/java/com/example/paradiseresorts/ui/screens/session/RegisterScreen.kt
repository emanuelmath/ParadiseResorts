//Este es el archivo que continene la estructura y funcionalidad de la pantalla de registro.
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.paradiseresorts.ui.components.AppColors
import com.example.paradiseresorts.ui.theme.LocalAppColors
import kotlinx.coroutines.launch
import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarViewWeek
import com.example.paradiseresorts.ui.components.buttons.PrimaryButton
import com.example.paradiseresorts.ui.components.buttons.SecondaryButton
import com.example.paradiseresorts.ui.components.dialogs.AddCardDialog

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val uiState = registerViewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var currentStep by remember { mutableStateOf(0) }
    // 🔹 Quitamos el paso de tarjeta → total 8 pasos
    val stepsCount = 8
    val appColors = LocalAppColors.current

    // Mostrar errores como snackbar
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                registerViewModel.clearError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(brush = Brush.linearGradient(colors = appColors.backgroundGradient))
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // 🔹 AppBar simple
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text("Registrar", color = Color.White, fontSize = 42.sp, fontWeight = FontWeight.ExtraBold)
                    Text("una nueva cuenta", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(16.dp))

                // 🔹 Contenedor blanco centrado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .wrapContentHeight() // no se expande innecesariamente
                        .align(Alignment.CenterHorizontally)
                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text("Paso ${currentStep + 1} de $stepsCount", color = Color.Gray, fontSize = 14.sp)

                        Spacer(Modifier.height(16.dp))

                        // Contenido dinámico
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        ) {
                            when (currentStep) {
                                0 -> StepContent("DUI (9 dígitos)", uiState.dui, registerViewModel::onDuiChange, keyboardType = KeyboardType.Number)
                                1 -> StepContent("Dinos tu nombre", uiState.name, registerViewModel::onNameChange)
                                2 -> StepContent("Dinos tu apellido", uiState.lastName, registerViewModel::onLastNameChange)
                                3 -> DatePickerStep(uiState.dateOfBirth, registerViewModel::onDateOfBirthChange)
                                4 -> StepContent("Tu correo electrónico", uiState.email, registerViewModel::onEmailChange, keyboardType = KeyboardType.Email)
                                5 -> PasswordStep(uiState.password, uiState.confirmPassword, registerViewModel::onPasswordChange, registerViewModel::onConfirmPasswordChange)
                                6 -> StepContent("Tu número de teléfono", uiState.phoneNumber, registerViewModel::onPhoneNumberChange, keyboardType = KeyboardType.Number)
                                7 -> FinalStep(uiState.acceptTerms, registerViewModel::onAcceptTermsChange)
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        // 🔹 Botones custom
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SecondaryButton(
                                text = "Atrás",
                                onClick = { if (currentStep > 0) currentStep-- },
                                enabled = currentStep > 0
                            )
                            PrimaryButton(
                                text = if (currentStep == stepsCount - 1) "Registrarse" else "Continuar",
                                onClick = {
                                    registerViewModel.validateStep(currentStep) {
                                        if (currentStep < stepsCount - 1) {
                                            currentStep++
                                        } else {
                                            // 🔹 Registro final → redirigir a login
                                            registerViewModel.registerUser {
                                                onRegisterSuccess()
                                            }
                                        }
                                    }
                                },
                                enabled = !uiState.isLoading
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text("¿Ya tienes una cuenta?", color = Color.Gray)
                            TextButton(onClick = onLoginClick) {
                                Text("INICIA SESIÓN", color = appColors.blueColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}


/*
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val uiState = registerViewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var currentStep by remember { mutableStateOf(0) }
    val stepsCount = 9
    val appColors = LocalAppColors.current

    //SnackBar que muestra mensajes de errores o alertas en lugar de un Toast
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = "Ok",
                    duration = SnackbarDuration.Short)
                registerViewModel.clearError()
            }
        }
    }

    //Definición de pantalla con espacio para los SnackBars
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(brush = Brush.linearGradient(colors = appColors.backgroundGradient))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text("Registrar", color = Color.White, fontSize = 42.sp, fontWeight = FontWeight.ExtraBold)
                Text("una nueva cuenta", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(16.dp))

            // Formulario paso a paso:
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Text("Paso ${currentStep + 1} de $stepsCount", color = Color.Gray, fontSize = 14.sp)

                    Spacer(Modifier.height(16.dp))

                    //Contenedor dinámico para hacer el paso a paso:
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {

                        when (currentStep) {
                            0 -> StepContent(
                                question = "DUI (9 dígitos)",
                                value = uiState.dui,
                                onValueChange = registerViewModel::onDuiChange,
                                keyboardType = KeyboardType.Number
                            )
                            1 -> StepContent(
                                question = "Dinos tu nombre",
                                value = uiState.name,
                                onValueChange = registerViewModel::onNameChange
                            )
                            2 -> StepContent(
                                question = "Dinos tu apellido",
                                value = uiState.lastName,
                                onValueChange = registerViewModel::onLastNameChange
                            )
                            3 -> DatePickerStep(
                                dateOfBirth = uiState.dateOfBirth,
                                onDateSelected = registerViewModel::onDateOfBirthChange
                            )
                            4 -> StepContent(
                                question = "Tu correo electrónico",
                                value = uiState.email,
                                onValueChange = registerViewModel::onEmailChange,
                                keyboardType = KeyboardType.Email
                            )
                            5 -> PasswordStep(
                                password = uiState.password,
                                confirmPassword = uiState.confirmPassword,
                                onPasswordChange = registerViewModel::onPasswordChange,
                                onConfirmPasswordChange = registerViewModel::onConfirmPasswordChange
                            )
                            6 -> StepContent(
                                question = "Tu número de teléfono",
                                value = uiState.phoneNumber,
                                onValueChange = registerViewModel::onPhoneNumberChange,
                                keyboardType = KeyboardType.Number
                            )
                            7 -> AddCardStep { code, expirationDate, cvv ->
                                registerViewModel.onCardAdded(code, expirationDate, cvv)
                            }
                            8 -> FinalStep(
                                acceptTerms = uiState.acceptTerms,
                                onAcceptChange = registerViewModel::onAcceptTermsChange
                            )
                        }

                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = { if (currentStep > 0) currentStep-- }, enabled = currentStep > 0) {
                            Text("Atrás")
                        }
                        Button(
                            onClick = {
                                registerViewModel.validateStep(currentStep) {
                                    if (currentStep < stepsCount - 1) {
                                        currentStep++
                                    } else {
                                        registerViewModel.registerUser(onRegisterSuccess)
                                    }
                                }
                            },
                            enabled = !uiState.isLoading
                        ) {
                            Text(if (currentStep == stepsCount - 1) "Registrarse" else "Continuar")
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text("¿Ya tienes una cuenta?", color = Color.Gray)
                        TextButton(onClick = onLoginClick) {
                            Text("INICIA SESIÓN", color = appColors.blueColor, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
*/
// -- SECCIÓN DE COMPOSABLES EXTRAS PARA LA PANTALLA --

//Composable para llenar contenido del contenedor dinámico:
@Composable
fun StepContent(
    question: String,
    value: String,
    onValueChange: (String) -> Unit,
    password: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(question, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}

//Composable para asignar y validar el password:
@Composable
fun PasswordStep(
    password: String,
    confirmPassword: String,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Crea una contraseña", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Confirmar contraseña") }
        )
    }
}

//Composable para estructurar y validar el paso final antes del registro:
@Composable
fun FinalStep(acceptTerms: Boolean, onAcceptChange: (Boolean) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Último paso", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = acceptTerms, onCheckedChange = onAcceptChange)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Acepto los términos y condiciones")
        }
    }
}

//Composable para estructurar el selector de fecha de nacimiento:
@Composable
fun DatePickerStep(
    dateOfBirth: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formatted = "%04d-%02d-%02d".format(selectedYear, selectedMonth + 1, selectedDay)
                onDateSelected(formatted)
                openDialog = false
            },
            year, month, day
        ).show()
        openDialog = false
    }

    Column {
        Text("Selecciona tu fecha de nacimiento", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { openDialog = true }) {
                    Icon(Icons.Default.CalendarViewWeek, contentDescription = "Seleccionar fecha")
                }
            }
        )
    }
}

//Composable para estructurar el dialog de tarjeta. Este viene de ui.components
@Composable
fun AddCardStep(
    onAddCard: (String, String, String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            "¿Quieres añadir una tarjeta de crédito o débito ahora?",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = "Añadir tarjeta")
            Spacer(Modifier.width(8.dp))
            Text("AÑADIR TARJETA")
        }

        if (showDialog) {
            AddCardDialog(
                onDismiss = { showDialog = false },
                onSave = { code, expirationDate, cvv ->
                    onAddCard(code, expirationDate, cvv)
                }
            )
        }
    }
}

// Preview de la pantalla (actualmente sin utilidad)
@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    val fakeColors = AppColors()
    val navController = rememberNavController()
    //RegisterScreen()
}