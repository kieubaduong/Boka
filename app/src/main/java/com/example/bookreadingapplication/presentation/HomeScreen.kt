package com.example.bookreadingapplication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.bookreadingapplication.R
import com.example.bookreadingapplication.data.model.Book
import com.example.bookreadingapplication.graph.Graph
import com.example.bookreadingapplication.theme.AppColor
import com.example.bookreadingapplication.util.gradientBackground

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth(),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(R.drawable.home_screen_app_bar_bg),
                contentDescription = "Background Image",
            )
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ) {
                Box(Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {
                        //TODO
                    },
                    enabled = false,

                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(
                            color = AppColor.greyTrans,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            navController.navigate(Graph.SEARCH)
                        },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    placeholder = {
                        Text(text = "Search book name or author", color = Color.White)
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                )
                Box(Modifier.height(30.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Popular",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
            Icon(
                modifier = Modifier.clickable { /* Handle arrow icon click */ },
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Arrow Icon"
            )
        }

        LazyRow(
            modifier = Modifier.padding(16.dp, top = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(topBooks) { book ->
                Box(
                    modifier = Modifier.clickable {
                        navController.navigate(Graph.DETAIL)
                    }
                ) {
                    BookItem(book)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recommended for you",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
            Icon(
                modifier = Modifier.clickable { /* Handle arrow icon click */ },
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Arrow Icon"
            )
        }

        LazyRow(
            modifier = Modifier.padding(16.dp, top = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recommendedBooks) { book ->
                Box(
                    modifier = Modifier.clickable {
                        navController.navigate(Graph.DETAIL)
                    }
                ) {
                    BookItem(book)
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Column(
        modifier = Modifier.width(120.dp)
    ) {
        Box {
            AsyncImage(
                model = "https://link.gdsc.app/QL7oYJ2",
                contentDescription = book.title,
                placeholder = painterResource(R.drawable.book),
                modifier = Modifier
                    .width(120.dp)
                    .height(176.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                Column(
                    modifier = Modifier
                        .height(36.dp)
                        .width(26.dp)
                        .gradientBackground(
                            colors = listOf(
                                Color(0xFFB91D73),
                                Color(0xFFF953C6),
                            ),
                            angle = 22.5f
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(14.dp)
                    )
                    Text(
                        text = book.rating.toString(),
                        style = TextStyle(fontSize = 12.sp, color = Color.White),
                    )
                }
            }
        }
        Text(
            text = "Enter Prise Design Sprints",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 8.dp),
            maxLines = 2,
        )
        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Category,
                contentDescription = "Genre Icon",
                tint = AppColor.grey,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "arts and music,...",
                style = TextStyle(fontSize = 16.sp, color = AppColor.grey),
                modifier = Modifier.padding(start = 4.dp),
                maxLines = 1,
            )
        }
    }
}


val topBooks = listOf(
    Book(title = "Book 1", rating = 4, category = "horror, zombies,..."),
    Book(title = "Book 2", rating = 3, category = "horror, zombies,..."),
    Book(title = "Book 3", rating = 4, category = "horror, zombies,..."),
    Book(title = "Book 4", rating = 4, category = "horror, zombies,..."),
    Book(title = "Book 5", rating = 4, category = "horror, zombies,..."),
)

val recommendedBooks = listOf(
    Book(title = "Book 6", rating = 4, category = "horror, zombies,..."),
    Book(title = "Book 7", rating = 4, category = "horror, zombies,..."),
    Book(title = "Book 8", rating = 3, category = "horror, zombies,..."),
    Book(title = "Book 9", rating = 3, category = "horror, zombies,..."),
    Book(title = "Book 10", rating = 3, category = "horror, zombies,..."),
)
