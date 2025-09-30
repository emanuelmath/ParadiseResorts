package com.example.paradiseresorts.ui.classes

import com.example.paradiseresorts.data.database.entities.HotelEntity
import com.example.paradiseresorts.data.database.entities.RoomEntity
import com.example.paradiseresorts.domain.models.Service

object CatalogProvider {

    // Lista fija de hoteles
    val hotels = listOf(
        HotelEntity(id = 1, name = "Hotel Miramar", location = "San Salvador"),
        HotelEntity(id = 2, name = "Hotel Los Volcanes", location = "Santa Ana"),
        HotelEntity(id = 3, name = "Hotel Colonial", location = "Suchitoto"),
        HotelEntity(id = 4, name = "Hotel Pacifico", location = "La Libertad")
    )

    // Categorías de habitaciones por hotel
    private val categories = listOf("Económica", "Estándar", "Premium", "Suite")
    private val basePrices = listOf(50.0, 80.0, 120.0, 200.0)

    val rooms = buildList {
        var roomId = 1
        for (hotel in hotels) {
            categories.forEachIndexed { index, category ->
                add(
                    RoomEntity(
                        id = roomId++,
                        name = "$category - ${hotel.name}",
                        price = basePrices[index],
                        hotelId = hotel.id,
                        category = category,
                        isReserved = false
                    )
                )
            }
        }
    }

    // Catálogo de servicios fijo (solo para UI y lógica)
    val services = listOf(
        Service(id = 1, nombre = "Spa Relax", dui = "Masajes y sauna relajante individual", price = 30.0),
        Service(id = 2, nombre = "Transporte VIP", dui = "Traslado privado aeropuerto-hotel o cualquier destion", price = 50.0),
        Service(id = 3, nombre = "Tour de la ciudad", dui = "Visita guiada por los principales lugares turísticos de la zona", price = 40.0),
        Service(id = 4, nombre = "Comida buffet", dui = "Comidas variadas incluido", price = 25.0)
    )

}
