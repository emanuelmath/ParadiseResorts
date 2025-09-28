package com.example.paradiseresorts.ui.components.actionbars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Room
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.paradiseresorts.ui.theme.LocalAppColors

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit = {},
    onServicesClick: () -> Unit = {},
    onReservationsClick: () -> Unit = {},
    onInformationClick: () -> Unit = {},
    onFeedbackClick: () -> Unit = {}
) {
    val appColors = LocalAppColors.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onHomeClick) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    tint = appColors.purpleColor,
                    modifier = Modifier.size(26.dp)
                )
            }
            IconButton(onClick = onServicesClick) {
                Icon(
                    imageVector = Icons.Default.RoomService,
                    contentDescription = "Servicios",
                    tint = appColors.purpleColor,
                    modifier = Modifier.size(26.dp)
                )
            }
            IconButton(onClick = onReservationsClick) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Reservas",
                    tint = appColors.purpleColor,
                    modifier = Modifier.size(26.dp)
                )
            }
            IconButton(onClick = onInformationClick) {
                Icon(
                    imageVector = Icons.Default.Room,
                    contentDescription = "Información",
                    tint = appColors.purpleColor,
                    modifier = Modifier.size(26.dp)
                )
            }
            IconButton(onClick = onFeedbackClick) {
                Icon(
                    imageVector = Icons.Default.Feedback,
                    contentDescription = "Feedback",
                    tint = appColors.purpleColor,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BottomBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)),
        verticalArrangement = Arrangement.Bottom
    ) {
        BottomBar()
    }
}