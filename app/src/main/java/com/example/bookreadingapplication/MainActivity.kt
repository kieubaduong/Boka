package com.example.bookreadingapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookreadingapplication.onboarding.OnboardingScreen
import com.example.bookreadingapplication.theme.BookReadingApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookReadingApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                ) {
                    OnboardingScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookReadingApplicationTheme {
        OnboardingScreen()
    }
}