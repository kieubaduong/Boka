package com.example.boka.ui.favourite_genres

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.boka.core.BottomBarScreen
import com.example.boka.data.model.Genre
import com.example.boka.data.network.api.ApiService
import com.example.boka.data.network.master.MasterService
import com.example.boka.data.network.user.UserService
import com.example.boka.data.repository.MasterRepo
import com.example.boka.data.repository.UserRepo
import com.example.boka.ui.theme.AppColor
import com.example.boka.util.ApiResult

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
)
@Composable
fun FavouriteGenreScreen(navController: NavController) {
    val masterApi = ApiService.masterApi
    val masterService = MasterService(masterApi)
    val masterRepo = MasterRepo(masterService)

    val userApi = ApiService.userApi
    val userService = UserService(userApi)
    val userRepo = UserRepo(userService)

    val favouriteGenresViewModel = remember { FavouriteGenresViewModel(masterRepo, userRepo) }

    val getAllGenresResult by favouriteGenresViewModel.genres.collectAsState()
    val updateProfileResult by favouriteGenresViewModel.updateProfileResult.collectAsState()

    val genres: List<Genre>

    var searchText by remember { mutableStateOf("") }
    val selectedCategories = remember { mutableStateListOf<Genre>() }
    val showFab = remember { mutableStateOf(false) }
    val loading = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    var errorMessage = "Something went wrong"

    Log.d("FavouriteGenreScreen", "rebuild")

    when (updateProfileResult) {
        is ApiResult.Success -> {
            LaunchedEffect(Unit) {
                navController.popBackStack()
                navController.navigate(BottomBarScreen.Home.route)
            }
        }

        is ApiResult.Error -> {
            loading.value = false
            errorMessage =
                (updateProfileResult as ApiResult.Error).exception.toString()
            showDialog.value = true
        }

        else -> {}
    }

    when (getAllGenresResult) {
        is ApiResult.Success -> {
            genres = (getAllGenresResult as ApiResult.Success<List<Genre>>).data
            val filteredCategories = genres.filter {
                it.name.contains(searchText, ignoreCase = true)
            }

            Scaffold(
                floatingActionButton = {
                    if (showFab.value) {
                        FloatingActionButton(
                            onClick = {
                                loading.value = true
                                favouriteGenresViewModel.updateGenresProfile(
                                    selectedCategories.map { it.id })

                            },
                            modifier = Modifier
                                .padding(bottom = 20.dp, end = 20.dp),
                        ) {
                            Icon(Icons.Default.DoneAll, contentDescription = "Done All")
                        }
                    }
                },
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    keyboardController?.hide()
                }

            ) { contentPadding ->
                if (showDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        text = {
                            Text(
                                text = errorMessage,
                                style = TextStyle(
                                    color = MaterialTheme.colors.onSurface,
                                    fontSize = MaterialTheme.typography.body1.fontSize
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        confirmButton = {},

                        )
                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (loading.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Gray.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(60.dp),
                                color = AppColor.purple
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 15.dp)
                            .padding(contentPadding)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                modifier = Modifier.weight(1f),
                            )
                            if (searchText.isNotEmpty()) {
                                Box(
                                    modifier = Modifier.size(50.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            searchText = ""
                                            keyboardController?.hide()
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = null,
                                            tint = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                        if (selectedCategories.isNotEmpty()) {
                            Row(
                                modifier = Modifier.padding(
                                    vertical = 8.dp,
                                    horizontal = 16.dp
                                )
                            ) {
                                selectedCategories.forEach { category ->
                                    Chip(
                                        onClick = { selectedCategories.remove(category) },
                                        content = {
                                            Text(category.name)
                                        },
                                        modifier = Modifier.padding(end = if (selectedCategories.last() == category) 0.dp else 8.dp),
                                    )
                                }
                            }
                        }
                        LazyColumn(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {

                            items(filteredCategories) { category ->
                                Row(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                )
                                {
                                    Checkbox(
                                        checked = category in selectedCategories,
                                        onCheckedChange = {
                                            if (selectedCategories.size >= 3 && category !in selectedCategories) return@Checkbox
                                            if (it) {
                                                selectedCategories.add(category)
                                                searchText = ""
                                                if (selectedCategories.size == 3) showFab.value =
                                                    true

                                            } else {
                                                selectedCategories.remove(category)
                                                if (selectedCategories.size < 3) showFab.value =
                                                    false
                                            }
                                        },
                                        enabled = selectedCategories.size < 3 || (category in selectedCategories)
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

    DisposableEffect(Unit) {
        onDispose {
            Log.d("FavouriteGenresScreen", "onDispose")
            searchText = ""
            selectedCategories.clear()
            showFab.value = false
            loading.value = false
            showDialog.value = false
            errorMessage = ""
        }
    }

}
