package com.example.boka.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.boka.data.model.Book
import com.example.boka.data.network.api.ApiService
import com.example.boka.data.network.book.BookService
import com.example.boka.data.repository.BookRepo
import com.example.boka.ui.common.HttpImage
import com.example.boka.ui.common.RatingBar
import com.example.boka.util.ApiResult

@Composable
fun BookDetailScreen(navController: NavHostController, bookId: Int?, isbn: String) {
    val bookService = BookService(ApiService.bookApi)
    val bookRepo = BookRepo(bookService)
    val bookDetailViewModel = remember { BookDetailViewModel(bookRepo, bookId ?: 0) }

    val getBookDetailResult by bookDetailViewModel.getBookDetailResult.collectAsState()
    val contentBasedBooks by bookDetailViewModel.contentBasedBooks.collectAsState()
    val itemBasedBooks by bookDetailViewModel.itemBasedBooks.collectAsState()

    var rating by remember { mutableStateOf(0) }
    var isPopupVisible by remember { mutableStateOf(false) }

    when (getBookDetailResult) {
        is ApiResult.Loading -> {
            Scaffold {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        is ApiResult.Error -> {
            Scaffold {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 15.dp, start = 20.dp)
                            .align(Alignment.TopStart)
                            .clickable {
                                navController.popBackStack()
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "Back Icon",
                            tint = Color(0xFF666666),
                            modifier = Modifier
                                .size(23.dp)
                        )
                    }
                    Text(text = (getBookDetailResult as ApiResult.Error).exception.toString())
                }
            }

        }

        is ApiResult.Success -> {
            val book = (getBookDetailResult as ApiResult.Success).data
            rating = book.userRating
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 24.dp)
            ) {
                if (isPopupVisible) {
                    RatingPopup(
                        currentRating = rating,
                        onRatingSelected = { newRating ->
                            rating = newRating
                            isPopupVisible = false
                            if (bookId != null) {
                                bookDetailViewModel.getBookDetail(bookId)
                            }
                        },
                        bookId = bookId ?: 0,
                        bookRepo = bookRepo,
                    )
                }
                Box(
                    modifier = Modifier
                        .height(474.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    HttpImage(
                        url = book.imageL,
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.2f),
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 72.dp, start = 34.dp)
                            .align(Alignment.TopStart)
                            .clickable {
                                navController.popBackStack()
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIos,
                            contentDescription = "Back Icon",
                            tint = Color(0xFF666666),
                            modifier = Modifier
                                .size(23.dp)
                        )
                    }
                    Box(
                        modifier = Modifier.padding(top = 64.dp)
                    ) {
                        HttpImage(
                            url = book.imageL,
                            modifier = Modifier
                                .height(240.dp)
                                .width(160.dp)
                                .clip(RoundedCornerShape(8.dp)),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 282.dp)
                            .background(
                                Color.Transparent
                            )
                    ) {
                        Button(
                            onClick = {
                                isPopupVisible = true

                            },
                            modifier = Modifier
                                .width(114.dp)
                                .height(44.dp)
                                .background(Color.Transparent),
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFFF953C6),
                                                Color(0xFFB91D73),
                                            ),
                                        ),
                                    )
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ThumbUp,
                                        contentDescription = "Star Icon",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Rate",
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight(500),
                                            color = Color.White,
                                        )
                                    )
                                }
                            }

                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 334.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = book.title,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF333333),
                            ),
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Text(
                            text = book.author,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF666666),
                            )
                        )
                        RatingBar(rating = rating)
                    }
                }
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = book.description,
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF24253D),
                    )
                )
                Text(
                    modifier = Modifier.padding(top = 11.dp, start = 27.dp),
                    text = "Related to this topic",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF6648A8),
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 20.dp, start = 27.dp, end = 27.dp),
                ) {
                    when (contentBasedBooks) {
                        is ApiResult.Loading -> {
                            Box(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is ApiResult.Error -> {
                            Box(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(text = (contentBasedBooks as ApiResult.Error).exception.toString())
                            }
                        }

                        is ApiResult.Success -> {
                            val books = (contentBasedBooks as ApiResult.Success<List<Book>>).data
                            books.forEach { book ->
                                RecommendBookItem(book)
                            }
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(top = 11.dp, start = 27.dp),
                    text = "Books you might like",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF6648A8),
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = 20.dp, start = 27.dp, end = 27.dp),
                ) {
                    when (itemBasedBooks) {
                        is ApiResult.Loading -> {
                            Box(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is ApiResult.Error -> {
                            Box(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(text = (itemBasedBooks as ApiResult.Error).exception.toString())
                            }
                        }

                        is ApiResult.Success -> {
                            val books = (itemBasedBooks as ApiResult.Success<List<Book>>).data
                            books.forEach { book ->
                                RecommendBookItem(book)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendBookItem(book: Book) {
    Column(
        modifier = Modifier.width(90.dp)
    ) {
        HttpImage(
            url = book.imageL,
            modifier = Modifier
                .width(90.dp)
                .height(126.dp)
                .clip(RoundedCornerShape(16.dp)),
        )
        Spacer(modifier = Modifier.height(11.dp))
        Text(
            text = book.title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight(500),
                color = Color(0xFF2D2D2D),
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}