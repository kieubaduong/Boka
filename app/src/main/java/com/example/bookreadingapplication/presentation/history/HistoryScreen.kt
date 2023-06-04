package com.example.bookreadingapplication.presentation.history


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.bookreadingapplication.R
import com.example.bookreadingapplication.common.RatingBar
import com.example.bookreadingapplication.common.loremIpsum
import com.example.bookreadingapplication.core.NormalScreen
import com.example.bookreadingapplication.data.model.Book

@Composable
fun HistoryScreen(navController: NavHostController) {
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.home_screen_app_bar_bg),
            contentDescription = "App bar background",
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
            text = "Rated books",
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
            books.forEach { book ->
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
    rating: Int,
    category: String,
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB91D73),
            Color(0xFFF953C6)
        ),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = "https://link.gdsc.app/QL7oYJ2",
            contentDescription = bookName,
            placeholder = painterResource(R.drawable.book),
            modifier = Modifier
                .fillMaxHeight()
                .width(76.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
        ) {
            Text(
                text = bookName,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF333333)
                )
            )
            Spacer(modifier = Modifier.height(9.dp))
            Text(
                text = loremIpsum,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF666666)
                ),
                maxLines = 2
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = category,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF999999)
                    )
                )
                RatingBar(rating = rating, 18)
            }
        }
    }
}

val books = listOf(
    Book(title = "Enter Prise Design Sprints", rating =  3, category =  "Northwestern"),
    Book(title ="Enter Prise Design Sprints",rating =  3,category =  "Northwestern"),
    Book(title ="Enter Prise Design Sprints", rating = 3,category =  "Northwestern"),
    Book(title ="Enter Prise Design Sprints", rating = 3,category =  "Northwestern"),
    Book(title ="Enter Prise Design Sprints", rating = 3,category =  "Northwestern"),
)
