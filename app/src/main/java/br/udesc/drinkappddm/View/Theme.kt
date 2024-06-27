package br.udesc.drinkappddm.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define background
@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFD1C4E9),
                        Color(0xFFE1BEE7),
                        Color(0xFFFFAB91)
                    )
                )
            )
    ) {
        content()
    }
}


val RoundedCornerShape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)

val HeaderTextStyle = androidx.compose.ui.text.TextStyle(
    fontSize = 25.sp,
    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
    color = Color(0xFF333333)
)

val BodyTextStyle = androidx.compose.ui.text.TextStyle(
    fontSize = 18.sp,
    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
    color = Color(0xFF333333)
)
