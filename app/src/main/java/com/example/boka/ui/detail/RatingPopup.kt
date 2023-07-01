package com.example.boka.ui.detail

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.boka.data.repository.BookRepo
import com.example.boka.util.ApiResult

@Composable
fun RatingPopup(
    currentRating: Int,
    onRatingSelected: (Int) -> Unit,
    bookId : Int,
    bookRepo: BookRepo,
) {
    val ratingViewModel = remember { RatingViewModel(bookRepo, bookId) }

    val rateBookResult by ratingViewModel.rateBookResult.collectAsState()
    val isFirst = remember { mutableStateOf(true) }

    Log.d("Rebuild", "RatingPopup")


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
            Column {
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
                                .clickable {
                                    ratingViewModel.rating = i
                                    ratingViewModel.rateBook(i)
                                    isFirst.value = false
                                }
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
                
                when (rateBookResult) {
                    is ApiResult.Loading -> {
                        if (!isFirst.value) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        else{
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = "",
                                )
                            }
                        }
                    }

                    is ApiResult.Error -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = ((rateBookResult as ApiResult.Error).exception.message)
                                    ?: "Error",
                            )
                        }
                    }

                    is ApiResult.Success -> {
                        onRatingSelected(ratingViewModel.rating)
                    }
                }

            }
        },
        confirmButton = { },
        dismissButton = { }
    )
}