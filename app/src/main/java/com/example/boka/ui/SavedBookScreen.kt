package com.example.boka.ui

import com.example.boka.core.NormalScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.boka.R
import com.example.boka.data.model.Book
import com.example.boka.ui.common.RatingBar
import com.example.boka.ui.theme.AppColor
import com.example.boka.util.roundNumber

@Composable
fun SavedBookScreen(navController: NavHostController) {
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.saved_book_screen_app_bar_bg),
            contentDescription = "App bar background",
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 51.dp),
            text = "Saved books",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(600),
                color = Color.White
            )
        )

        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 105.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            bookEntities.forEach { book ->
                book.title?.let {
                    book.rating?.let { it1 ->
                        book.category?.let { it2 ->
                            Box (
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(NormalScreen.BookDetail.route)
                                    }
                                    ){
                                BookItem(
                                    bookName = it,
                                    rating = it1,
                                    category = it2
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(
    bookName: String,
    rating: Double,
    category: String,
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB91D73),
            Color(0xFFF953C6)
        ),
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(13.dp)),
        colors = CardDefaults.cardColors(
            containerColor = AppColor.white,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = "https://link.gdsc.app/QL7oYJ2",
                contentDescription = bookName,
                placeholder = painterResource(R.drawable.book),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(60.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .padding(start = 14.dp, end = 15.dp)
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    text = bookName,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF393C50)
                    )
                )
                RatingBar(rating = roundNumber(rating), 18)
                Text(
                    text = category,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF979797)
                    )
                )
            }
            Box(
                modifier = Modifier
                    .width(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete book icon",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.SrcAtop)
                            }
                        },
                    tint = Color.Black
                )
            }
        }
    }
}

val bookEntities = listOf(
    Book(title = "Enter Prise Design Sprints", rating =  3.0, category =  "Northwestern"),
    Book(title ="Enter Prise Design Sprints",rating =  3.0,category =  "Northwestern"),
    Book(title ="Enter Prise Design Sprints", rating = 3.0,category =  "Northwestern"),
    Book(title ="Enter Prise Design Sprints", rating = 3.0,category =  "Northwestern"),
    Book(title ="Enter Prise Design Sprints", rating = 3.0,category =  "Northwestern"),
)
