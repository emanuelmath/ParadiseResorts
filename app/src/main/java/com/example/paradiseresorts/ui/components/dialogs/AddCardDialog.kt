package com.example.paradiseresorts.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddCardDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var cardCode by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onSave(cardCode, expirationDate, cvv)
                onDismiss()
            }) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        title = { Text("Añadir tarjeta") },
        text = {
            Column {
                OutlinedTextField(
                    value = cardCode,
                    onValueChange = { cardCode = it },
                    label = { Text("Número de tarjeta") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                /*OutlinedTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it },
                    label = { Text("Nombre del titular") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))*/
                OutlinedTextField(
                    value = expirationDate,
                    onValueChange = { expirationDate = it },
                    label = { Text("Fecha de expiración (MM/AA)") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    singleLine = true
                )
            }
        }
    )
}
