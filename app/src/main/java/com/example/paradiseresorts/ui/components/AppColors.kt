/* Esta clase continene la paleta de colores definida para toda la aplicación
* de modo que sean valores consistentes.
*/

package com.example.paradiseresorts.ui.components

import androidx.compose.ui.graphics.Color


data class AppColors(

    //Degradado para fondos de pantalla:
    val backgroundGradient: List<Color> = listOf(
        Color(0xFF405DE6),
        Color(0xFF5851DB),
        Color(0xFFC13584),
        Color(0xFFE1306C),
        Color(0xFFFCAF45)
    ),

    //Degradado para TopBars:
    val barGradient: List<Color> = listOf(
        Color(0xFF5851DB),
        Color(0xFFC13584)
    ),

    //Colores individuales:
    val blueColor: Color = Color(0xFF405DE6),
    val purpleColor: Color = Color(0xFF5851DB),
    val pinkColor: Color = Color(0xFFC13584),
    val orangeColor: Color = Color(0xFFE1306C),
    val yellowColor: Color = Color(0xFFFCAF45)
)
