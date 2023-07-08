package com.example.boka.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.boka.R
import com.example.boka.core.GlobalData
import com.example.boka.core.NormalScreen
import com.example.boka.core.clearToken
import com.example.boka.ui.theme.AppColor
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val user = GlobalData.currentUser


    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.profile_screen_app_bar_bg),
            contentDescription = "App bar background",
            contentScale = ContentScale.FillWidth
        )
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 43.dp),
            text = "Profile",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight(600),
                color = Color.White
            )
        )
        Card(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 106.dp,
                )
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = AppColor.white,
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 25.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                ) {
                    AsyncImage(
                        model = "https://link.gdsc.app/xTV9Pia",
                        contentDescription = "Avatar",
                        placeholder = painterResource(R.drawable.book),
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }
                ProfileItem(
                    title = "Fullname",
                    subtitle = user.name ?: "Dương Đẹp Trai",
                )
                ProfileItem(
                    title = "Email",
                    subtitle = user.email ?: "kieubaduong@gmail.com",
                )
                ProfileItem(
                    title = "Bird date",
                    subtitle = "24/12/2002",
                )
                ProfileItem(
                    title = "Gender",
                    subtitle = "Male",
                )
                ProfileItem(
                    title = "Address",
                    subtitle = "Dorm A VNU-HCM",
                    isLast = true
                )
                Button(
                    onClick = {
//                        navController.navigate(Graph.ROOT)
                        navController.popBackStack()
                        navController.navigate(NormalScreen.Login.route)
                        coroutineScope.launch {
                            clearToken(context)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFB91D73),
                                Color(0xFFF953C6)
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite
                        ),
                    ),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        text = "Log out",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFB91D73),
                        ),
                    )
                }
            }

        }
    }
}

@Composable
fun ProfileItem(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    isLast: Boolean = false
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight(400),
                color = AppColor.darkBlue
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = subtitle,
                modifier = Modifier.padding(end = 4.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFA5A5A5)
                )
            )
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
    if (!isLast) Divider(
        color = Color.Gray,
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}
