package com.example.boka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.boka.graph.RootNavGraph
import com.example.boka.presentation.detail.BookDetailScreen
import com.example.boka.theme.BookReadingApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookReadingApplicationTheme {
                RootNavGraph(navController = rememberNavController())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    BookReadingApplicationTheme {
        BookDetailScreen(navController)
    }
}