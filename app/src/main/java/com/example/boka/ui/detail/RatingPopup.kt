package com.example.boka.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun RatingPopup(currentRating: Int, onRatingSelected: (Int) -> Unit) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB91D73),
            Color(0xFFF953C6)
        ),
    )

    AlertDialog(
        onDismissRequest = { },
        title = { Text("Select Rating") },
        text = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                for (i in 1..5) {
                    Icon(
                        imageVector = if (i <= currentRating) Icons.Default.Star else Icons.Outlined.Grade,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onRatingSelected(i) }
                            .padding(4.dp)
                            .graphicsLayer(alpha = 0.99f)
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(gradient, blendMode = BlendMode.SrcAtop)
                                }
                            }
                    )
                }
            }
        },
        confirmButton = { },
        dismissButton = { }
    )
}