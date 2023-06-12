package com.example.boka.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.boka.data.model.ApiResult
import com.example.boka.graph.Graph
import com.example.boka.theme.AppColor
import com.example.boka.util.gradientBackground


@Composable
fun SignInScreen(navController: NavHostController) {
    val signInViewModel: SignInViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val signInResult by signInViewModel.signInResult.collectAsState()
    val isFirstAppear = remember { mutableStateOf(true) }

    Scaffold { paddingValue ->
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddingValue),
                horizontalAlignment = Alignment.Start
            ) {
                Box(modifier = Modifier.height(35.dp))
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier.height(15.dp))
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColor.lightPurple
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(color = AppColor.lightGrey, shape = RoundedCornerShape(8.dp)),
                    placeholder = {
                        Text(
                            text = "Input your email",
                            color = AppColor.darkBlue
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColor.lightPurple
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(color = AppColor.lightGrey, shape = RoundedCornerShape(8.dp)),
                    placeholder = {
                        Text(text = "Input your password", color = AppColor.darkBlue)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    singleLine = true,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                passwordVisibility = !passwordVisibility
                            }
                        )
                    }
                )
                Box(modifier = Modifier.height(26.dp))
                Button(
                    onClick = {
                        isFirstAppear.value = false
                        signInViewModel.login(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .gradientBackground(
                                colors = listOf(
                                    AppColor.purple,
                                    AppColor.pink,
                                ),
                                angle = 88.03f,
                            )
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Login")
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Forgot password?",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight(400),
                            color = AppColor.darkBlue
                        ),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Text(
                            text = "You do not have an account? ",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight(400),
                                color = AppColor.darkBlue
                            ),
                        )
                        Text(
                            text = "Sign up",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight(700),
                                color = AppColor.darkBlue
                            ),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                when (signInResult) {
                    is ApiResult.Success -> {
                        LaunchedEffect(Unit) {
                            navController.popBackStack()
                            navController.navigate(Graph.BASE)
                        }
                    }
                    is ApiResult.Error -> {
                        val exception = (signInResult as ApiResult.Error).exception
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Login failed: ${exception.message}",
                            style = TextStyle(
                                fontSize = 13.sp,
                                fontWeight = FontWeight(700),
                                color = Color.Red
                            ),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    else -> {}
                }
            }
            if (!isFirstAppear.value) {
                when (signInResult) {
                    is ApiResult.Loading -> {
                        Box(
                            modifier = Modifier.align(Alignment.Center)
                                .background(Color.Gray.copy(alpha = 0.2f))
                                .fillMaxSize(),
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                                    .size(60.dp),
                                color = AppColor.purple
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}

