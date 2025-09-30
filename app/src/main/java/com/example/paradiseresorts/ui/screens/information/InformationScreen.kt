//Este archivo contiene la definición y funcionalidad de la pantalla de Información de hoteles.
package com.example.paradiseresorts.ui.screens.information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paradiseresorts.R
import com.example.paradiseresorts.ui.classes.CatalogProvider

@Composable
fun InformationScreen(
    informationViewModel: InformationViewModel
) {

    val hotels = CatalogProvider.hotels
    val rooms = CatalogProvider.rooms

    // Colores consistentes con la aplicación:
    val blueColor = Color(0xFF405DE6)
    val purpleColor = Color(0xFF5851DB)
    val pinkColor = Color(0xFFC13584)
    val orangeColor = Color(0xFFE1306C)
    val yellowColor = Color(0xFFFCAF45)
    val cardYellow = Color(0xFFF8E21C)

    // Mapeo de imágenes
    val hotelImages = mapOf(
        1 to listOf(
            R.drawable.hotelmiramar1,
            R.drawable.hotelmiramar2,
            R.drawable.hotelmiramar3,
            R.drawable.hotelmiramar4,
            R.drawable.hotelmiramar5
        ),
        2 to listOf(
            R.drawable.hotelvolcanes1,
            R.drawable.hotelvolcanes2,
            R.drawable.hotelvolcanes3,
            R.drawable.hotelvolcanes4,
            R.drawable.hotelvolcanes5
        ),
        3 to listOf(
            R.drawable.hotelcolonial1,
            R.drawable.hotelcolonial2,
            R.drawable.hotelcolonial3,
            R.drawable.hotelcolonial4,
            R.drawable.hotelcolonial5
        ),
        4 to listOf(
            R.drawable.hotelpacifico1,
            R.drawable.hotelpacifico2,
            R.drawable.hotelpacifico3,
            R.drawable.hotelpacifico4,
            R.drawable.hotelpacifico5
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Nuestros Hoteles",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = purpleColor,
            modifier = Modifier.padding(16.dp)
        )

        hotels.forEach { hotel ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = cardYellow)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = hotel.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = blueColor
                        )
                    )
                    Text(
                        text = hotel.location,
                        style = MaterialTheme.typography.bodyMedium.copy(color = orangeColor)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Imagen principal del hotel
                    hotelImages[hotel.id]?.firstOrNull()?.let { img ->
                        Image(
                            painter = painterResource(id = img),
                            contentDescription = hotel.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Carrusel de habitaciones
                    val hotelRooms = rooms.filter { it.hotelId == hotel.id }
                    Text(
                        text = "Habitaciones",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = pinkColor
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(hotelRooms) { room ->
                            Card(
                                modifier = Modifier
                                    .width(220.dp)
                                    .padding(4.dp),
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = yellowColor)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = room.category,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = purpleColor
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    val imgIndex = hotelRooms.indexOf(room)
                                    hotelImages[hotel.id]?.getOrNull(imgIndex + 1)?.let { img ->
                                        Image(
                                            painter = painterResource(id = img),
                                            contentDescription = room.category,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(140.dp)
                                                .clip(RoundedCornerShape(10.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "$${"%,.2f".format(room.price)} / noche",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = blueColor
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = room.name,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = orangeColor
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
}
