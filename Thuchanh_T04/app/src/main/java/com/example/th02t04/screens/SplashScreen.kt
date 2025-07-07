package com.example.th02t04.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.Color
import com.example.th02t04.R

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(true) {
        delay(2000) // 2 seconds delay
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.uth_logo), // thay bằng ảnh thật của bạn
                contentDescription = "UTH Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "UTH SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF007BFF))
        }
    }
}
