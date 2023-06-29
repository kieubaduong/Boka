package com.example.boka.ui.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.boka.R
import com.example.boka.core.userAgent
import com.example.boka.util.initUntrustedImageLoader
import kotlinx.coroutines.launch

@Composable
fun HttpImage(url: String, modifier: Modifier) {
    val untrustedImageLoader: ImageLoader = initUntrustedImageLoader(LocalContext.current)
    val request = ImageRequest.Builder(LocalContext.current)
        .data(data = url)
        .setHeader("User-Agent", userAgent)
        .apply(block = fun ImageRequest.Builder.() {
            crossfade(true)
            placeholder(R.drawable.book)
        }).build()
    val imageSize = remember { mutableStateOf(Pair(0, 0)) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            val result = untrustedImageLoader.execute(request)
            if (result is SuccessResult) {
                imageSize.value = result.drawable.intrinsicWidth to result.drawable.intrinsicHeight
            }
        }
    }

    if (imageSize.value == Pair(1, 1)) {
        Image(
            painter = painterResource(R.drawable.book),
            contentDescription = "Http image",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            painter = rememberAsyncImagePainter(
                model = request,
                imageLoader = untrustedImageLoader
            ),
            contentDescription = "Http image",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}