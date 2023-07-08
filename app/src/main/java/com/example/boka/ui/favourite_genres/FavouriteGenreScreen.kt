package com.example.boka.ui.favourite_genres

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.boka.data.model.Genre
import com.example.boka.data.network.api.ApiService
import com.example.boka.data.network.master.MasterService
import com.example.boka.data.repository.MasterRepo
import com.example.boka.util.ApiResult

@Composable
fun FavouriteGenreScreen(navController: NavController) {

    val masterApi = ApiService.masterApi
    val masterService = MasterService(masterApi)
    val masterRepo = MasterRepo(masterService)
    val favouriteGenresViewModel = remember { FavouriteGenresViewModel(masterRepo) }

    val getAllGenresResult by favouriteGenresViewModel.genres.collectAsState()

    val genres: List<Genre>

    var searchText by remember { mutableStateOf("") }
    val selectedCategories = remember { mutableStateListOf<Genre>() }



    when (getAllGenresResult) {
        is ApiResult.Success -> {
            genres = (getAllGenresResult as ApiResult.Success<List<Genre>>).data
            val filteredCategories = genres.filter {
                it.name.contains(searchText, ignoreCase = true)
            }
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                items(filteredCategories) { category ->
                    Row(modifier = Modifier.padding(vertical = 8.dp)) {
                        Checkbox(
                            checked = category in selectedCategories,
                            onCheckedChange = {
                                if (it) {
                                    selectedCategories.add(category)
                                } else {
                                    selectedCategories.remove(category)
                                }
                            }
                        )
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        is ApiResult.Error -> {
            androidx.compose.material3.Text(
                text = (getAllGenresResult as ApiResult.Error).exception.message
                    ?: "Error",
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                style = TextStyle(color = Color.Red),
                overflow = TextOverflow.Ellipsis,
            )
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
