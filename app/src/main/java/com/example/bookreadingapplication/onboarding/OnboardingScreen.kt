package com.example.bookreadingapplication.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookreadingapplication.R
import com.example.bookreadingapplication.util.gradientBackground
import com.example.bookreadingapplication.util.montserratFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen() {
    Box(
        modifier = Modifier
            .gradientBackground(
                colors = listOf(
                    Color(0xFF7644AD),
                    Color(0xFFD54381),
                ),
                angle = 135f+45f,
            )
            .fillMaxSize(

            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(127.dp)
                    .background(Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(79.dp)
                        .width(60.dp)
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "Book Reading Application".uppercase(),
                color = Color.White,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = montserratFamily,
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Book for everyone",
                color = Color.White,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = montserratFamily,
                )
            )
        }

    }
}

