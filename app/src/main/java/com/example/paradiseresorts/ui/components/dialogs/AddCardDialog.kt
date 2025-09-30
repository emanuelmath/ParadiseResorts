package com.example.paradiseresorts.ui.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paradiseresorts.ui.components.buttons.PrimaryButton
import com.example.paradiseresorts.ui.components.buttons.SecondaryButton
import java.util.Calendar

@Composable
fun AddCardDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var cardCode by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    // 🔹 Mes y Año para expiración
    val months = (1..12).map { it.toString().padStart(2, '0') }
    val currentYearFull = Calendar.getInstance().get(Calendar.YEAR)
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
    val years = (currentYearFull..currentYearFull + 20).map { it.toString().takeLast(2) }

    var selectedMonth by remember { mutableStateOf(months.first()) }
    var selectedYear by remember { mutableStateOf(years.first()) }

    var monthExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }

    // 🔹 Validaciones
    val isCardCodeValid = cardCode.length in 13..19 && cardCode.all { it.isDigit() }
    val isCvvValid = cvv.length in 3..4 && cvv.all { it.isDigit() }

    // Fecha de expiración válida
    val selectedMonthInt = selectedMonth.toIntOrNull() ?: 1
    val selectedYearInt = ("20$selectedYear").toIntOrNull() ?: currentYearFull
    val isExpirationValid = (selectedYearInt > currentYearFull) ||
            (selectedYearInt == currentYearFull && selectedMonthInt >= currentMonth)

    val isFormValid = isCardCodeValid && isCvvValid && isExpirationValid

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Añadir tarjeta") },
        text = {
            Column {
                OutlinedTextField(
                    value = cardCode,
                    onValueChange = { cardCode = it },
                    label = { Text("Número de tarjeta") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isCardCodeValid && cardCode.isNotEmpty()
                )
                if (!isCardCodeValid && cardCode.isNotEmpty()) {
                    Text(
                        text = "Número inválido (13-19 dígitos)",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = selectedMonth,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Mes") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                            singleLine = true
                        )
                        DropdownMenu(
                            expanded = monthExpanded,
                            onDismissRequest = { monthExpanded = false }
                        ) {
                            months.forEach { month ->
                                DropdownMenuItem(
                                    onClick = { selectedMonth = month; monthExpanded = false },
                                    text = { Text(month) }
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { monthExpanded = true }
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = selectedYear,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Año") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                            singleLine = true
                        )
                        DropdownMenu(
                            expanded = yearExpanded,
                            onDismissRequest = { yearExpanded = false }
                        ) {
                            years.forEach { year ->
                                DropdownMenuItem(
                                    onClick = { selectedYear = year; yearExpanded = false },
                                    text = { Text(year) }
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { yearExpanded = true }
                        )
                    }
                }

                if (!isExpirationValid) {
                    Text(
                        text = "Fecha de expiración inválida",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isCvvValid && cvv.isNotEmpty()
                )
                if (!isCvvValid && cvv.isNotEmpty()) {
                    Text(
                        text = "CVV inválido (3-4 dígitos)",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            PrimaryButton(
                text = "Guardar",
                onClick = {
                    val expirationDate = "$selectedMonth/$selectedYear"
                    onSave(cardCode, expirationDate, cvv)
                    onDismiss()
                },
                enabled = isFormValid
            )
        },
        dismissButton = {
            SecondaryButton(
                text = "Cancelar",
                onClick = onDismiss
            )
        }
    )
}


/*
@Composable
fun AddCardDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var cardCode by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    // 🔹 Mes y Año para expiración
    val months = (1..12).map { it.toString().padStart(2, '0') }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (currentYear..currentYear + 20).map { it.toString().takeLast(2) }

    var selectedMonth by remember { mutableStateOf(months.first()) }
    var selectedYear by remember { mutableStateOf(years.first()) }

    var monthExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Añadir tarjeta") },
        text = {
            Column {
                OutlinedTextField(
                    value = cardCode,
                    onValueChange = { cardCode = it },
                    label = { Text("Número de tarjeta") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                // 🔹 Selector de mes/año
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = selectedMonth,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Mes") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                            singleLine = true
                        )
                        DropdownMenu(
                            expanded = monthExpanded,
                            onDismissRequest = { monthExpanded = false }
                        ) {
                            months.forEach { month ->
                                DropdownMenuItem(
                                    onClick = { selectedMonth = month; monthExpanded = false },
                                    text = { Text(month) }
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { monthExpanded = true }
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = selectedYear,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Año") },
                            modifier = Modifier.fillMaxWidth(),
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
                            singleLine = true
                        )
                        DropdownMenu(
                            expanded = yearExpanded,
                            onDismissRequest = { yearExpanded = false }
                        ) {
                            years.forEach { year ->
                                DropdownMenuItem(
                                    onClick = { selectedYear = year; yearExpanded = false },
                                    text = { Text(year) }
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { yearExpanded = true }
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            PrimaryButton(
                text = "Guardar",
                onClick = {
                    val expirationDate = "$selectedMonth/$selectedYear"
                    onSave(cardCode, expirationDate, cvv)
                    onDismiss()
                }
            )
        },
        dismissButton = {
            SecondaryButton(
                text = "Cancelar",
                onClick = onDismiss
            )
        }
    )
}
*/