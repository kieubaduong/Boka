package com.example.boka.ui.history


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.boka.R
import com.example.boka.core.NormalScreen
import com.example.boka.core.loremIpsum
import com.example.boka.data.model.Book
import com.example.boka.data.network.api.ApiService
import com.example.boka.data.network.history.HistoryService
import com.example.boka.data.repository.HistoryRepo
import com.example.boka.ui.common.HttpImage
import com.example.boka.ui.common.RatingBar
import com.example.boka.util.ApiResult
import com.example.boka.util.roundNumber

@Composable
fun HistoryScreen(navController: NavHostController) {
    val historyApi = ApiService.historyApi
    val historyService = HistoryService(historyApi)
    val historyRepo = HistoryRepo(historyService)

    val historyViewModel = remember { HistoryViewModel(historyRepo) }

    val ratedBooks by historyViewModel.ratedBooks.collectAsState()

    Log.d("alo alo", ApiService.token)

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

        when (ratedBooks) {
            is ApiResult.Success -> {
                val books = (ratedBooks as ApiResult.Success<List<Book>>).data
                if (books.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No recently viewed books",
                            style = TextStyle(color = Color.Red),
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 20.dp,
                                end = 20.dp,
                                top = 105.dp,
                            )
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(36.dp)
                    ) {
                        books.forEach { book ->
                            book.title.let {
                                book.ratingAvg.let { it1 ->
                                    book.category.let { it2 ->
                                        Box(
                                            modifier = Modifier
                                                .clickable {
                                                    navController.navigate(NormalScreen.BookDetail.route)
                                                }
                                        ) {
                                            BookItem(
                                                bookName = it,
                                                rating = it1,
                                                category = it2,
                                                image = book.imageL
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            is ApiResult.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (ratedBooks as ApiResult.Error).exception.message
                            ?: "Error",
                        style = TextStyle(color = Color.Red),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            is ApiResult.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
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
    image: String,
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
        HttpImage(
            url = image,
            modifier = Modifier
                .fillMaxHeight()
                .width(76.dp)
                .clip(RoundedCornerShape(8.dp)),
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
                    ),
                    overflow = TextOverflow.Ellipsis,

                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                )
                RatingBar(rating = roundNumber(rating), 18)
            }
        }
    }
}