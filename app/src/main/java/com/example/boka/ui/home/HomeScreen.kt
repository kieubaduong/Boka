package com.example.boka.ui.home

import android.graphics.drawable.Drawable
import android.widget.ImageView
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.boka.R
import com.example.boka.data.data_source.network.ApiService
import com.example.boka.data.repository.BookRepoImpl
import com.example.boka.domain.entity.BookEntity
import com.example.boka.domain.use_case.GetTopRatedBooksUserCase
import com.example.boka.graph.Graph
import com.example.boka.ui.theme.AppColor
import com.example.boka.util.ApiResult
import com.example.boka.util.gradientBackground
import initUntrustedImageLoader

@Composable
fun HomeScreen(navController: NavHostController) {
    val bookService = ApiService.bookService
    val bookRepo = BookRepoImpl(bookService)
    val getTopRatedBooksUseCase = GetTopRatedBooksUserCase(bookRepo)
    val homeViewModel = remember { HomeViewModel(getTopRatedBooksUseCase) }

    val topRatedBooksResult by homeViewModel.topRatedBooks.collectAsState()

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

        when (topRatedBooksResult) {
            is ApiResult.Success -> {
                val topBooks = (topRatedBooksResult as ApiResult.Success<List<BookEntity>>).data
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
            }

            is ApiResult.Error -> {
                Text(text = (topRatedBooksResult as ApiResult.Error).exception.message ?: "Error")
            }

            is ApiResult.Loading -> {
                CircularProgressIndicator()
            }
        }



        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
            text = "Recommended for you",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )

        LazyRow(
            modifier = Modifier.padding(16.dp, top = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recommendedBookEntities) { book ->
                Box(
                    modifier = Modifier.clickable {
                        navController.navigate(Graph.DETAIL)
                    }
                ) {
                    BookItem(book)
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
            text = "Recently viewed",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
        )

        LazyRow(
            modifier = Modifier.padding(16.dp, top = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recommendedBookEntities) { book ->
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BookItem(bookEntity: BookEntity) {
    val untrustedImageLoader: ImageLoader = initUntrustedImageLoader(LocalContext.current)
    val request = ImageRequest.Builder(LocalContext.current)
        .data(data = "http://images.amazon.com/images/P/0060093269.01.LZZZZZZZ.jpg")
        .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.58")
        .apply(block = fun ImageRequest.Builder.() {
            crossfade(true)
            placeholder(R.drawable.book)
        }).build()

    Column(
        modifier = Modifier.width(120.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = request,
                    imageLoader = untrustedImageLoader
                ),
                contentDescription = bookEntity.title,
                modifier = Modifier.size(100.dp)
            )
//            GlideImage(url = "https://vapa.vn/wp-content/uploads/2022/12/anh-3d-thien-nhien.jpeg")
//            AsyncImage(
//                model = rememberAsyncImagePainter(
//                    model = request,
//                    imageLoader = untrustedImageLoader
//                ),
//                placeholder = painterResource(R.drawable.book),
//
//                contentDescription = bookEntity.title,
//                modifier = Modifier
//                    .width(120.dp)
//                    .height(176.dp)
//                    .clip(RoundedCornerShape(16.dp)),
//                contentScale = ContentScale.Crop,
//            )
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
                        text = bookEntity.rating.toString(),
                        style = TextStyle(fontSize = 12.sp, color = Color.White),
                    )
                }
            }
        }
        Text(
            text = bookEntity.title,
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
                text = bookEntity.category,
                style = TextStyle(fontSize = 16.sp, color = AppColor.grey),
                modifier = Modifier.padding(start = 4.dp),
                maxLines = 1,
            )
        }
    }
}

@Composable
fun GlideImage(url: String) {
    val imageLoaded: MutableState<Boolean> = remember { mutableStateOf(false) }

    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                if (!imageLoaded.value) {
                    Glide.with(context)
                        .load(url)
                        .into(
                            object : CustomTarget<Drawable>() {
                                override fun onResourceReady(
                                    resource: Drawable,
                                    transition: Transition<in Drawable>?,
                                ) {
                                    setImageDrawable(resource)
                                    imageLoaded.value = true
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                    // Do nothing
                                }
                            },
                        )
                }
            }
        },
        modifier = Modifier.size(100.dp)
    )
}

val recommendedBookEntities = listOf(
    BookEntity(title = "Book 6", rating = 4.0, category = "horror, zombies,..."),
    BookEntity(title = "Book 7", rating = 4.0, category = "horror, zombies,..."),
    BookEntity(title = "Book 8", rating = 3.0, category = "horror, zombies,..."),
    BookEntity(title = "Book 9", rating = 3.0, category = "horror, zombies,..."),
    BookEntity(title = "Book 10", rating = 3.0, category = "horror, zombies,..."),
)