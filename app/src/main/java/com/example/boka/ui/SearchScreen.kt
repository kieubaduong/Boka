package com.example.boka.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.boka.R
import com.example.boka.ui.common.RatingBar
import com.example.boka.navigation.Graph
import com.example.boka.ui.theme.AppColor

@Composable
fun SearchScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(R.drawable.search_screen_app_bar_bg),
                contentDescription = "Background Image",
            )
            Card(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Search book name or author",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppColor.black
                        )
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {
                            //TODO
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = AppColor.white,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        placeholder = {
                            Text(text = "design", color = AppColor.grey)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = AppColor.grey,
                            )
                        }
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            SearchedBookItem(navController)
            SearchedBookItem(navController)
            SearchedBookItem(navController)
        }
    }
}

@Composable
fun SearchedBookItem(navController: NavHostController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                navController.navigate(Graph.DETAIL)
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColor.white,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ){
        Row(
        ) {
            AsyncImage(
                model = "https://link.gdsc.app/QL7oYJ2",
                contentDescription = "search book",
                placeholder = painterResource(R.drawable.book),
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.padding(10.dp),
            ) {
                Text(
                    text = "Enter Prise Design Sprints",
                    style = TextStyle(fontSize = 20.sp, color = Color(0xFF333333)),
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Northwestern Mutual",
                    style = TextStyle(fontSize = 13.sp, color = Color(0xFF666666)),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row() {
                    RatingBar(rating = 4)
                    Text(text = "4.0", style = TextStyle(fontSize = 13.sp, color = Color(0xFF999999)))
                }
                Spacer(modifier = Modifier.height(10.dp))
                GenreList(genres = listOf("Arts & Music", "Business"))
            }
        }
    }
}

@Composable
fun GenreList(genres: List<String>) {
    LazyRow {
        items(genres) { genre ->
            Card(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color(0xFFF953C6)),
                colors = CardDefaults.cardColors(
                    containerColor = AppColor.white,
                ),
                modifier = Modifier.padding(end = 7.dp)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = genre,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFF953C6)
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


