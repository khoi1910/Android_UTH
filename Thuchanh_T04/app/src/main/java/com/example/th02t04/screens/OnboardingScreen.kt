package com.example.th02t04.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.th02t04.R

data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = listOf(
        OnboardingPage(
            imageRes = R.drawable.onboarding_img,
            title = "Easy Time Management",
            description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first"
        ),
        OnboardingPage(
            imageRes = R.drawable.onboarding2_img,
            title = "Increase Work Effectiveness",
            description = "Time management and the determination of more important tasks will give your job statistics better and always improve"
        ),
        OnboardingPage(
            imageRes = R.drawable.onboarding3_img, // ← ảnh "Reminder Notification"
            title = "Reminder Notification",
            description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set"
        )
    )

    var currentPage by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Top: Indicators + Skip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                pages.forEachIndexed { index, _ ->
                    Dot(isSelected = index == currentPage)
                    if (index < pages.lastIndex) Spacer(modifier = Modifier.width(6.dp))
                }
            }
            TextButton(onClick = {
                if (currentPage < pages.lastIndex) {
                    currentPage = pages.lastIndex
                }
                // else: do nothing (Skip ở trang cuối không làm gì)
            }) {

            Text(text = "skip", color = Color.Blue, fontSize = 14.sp)
            }
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = pages[currentPage].imageRes),
                contentDescription = "Onboarding",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = pages[currentPage].title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = pages[currentPage].description,
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Bottom Navigation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = if (currentPage == 0) Arrangement.Center else Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentPage > 0) {
                IconButton(
                    onClick = { currentPage-- },
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color(0xFF2196F3), shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow), // ← ảnh mũi tên
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Button(
                onClick = {
                    if (currentPage < pages.lastIndex) {
                        currentPage++
                    } else {
                        navController.navigate("home") {
                            popUpTo("onboarding") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .then(
                        if (currentPage == 0) Modifier.fillMaxWidth()
                        else Modifier.weight(1f)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = if (currentPage == pages.lastIndex) "Get Started" else "Next",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Dot(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(
                color = if (isSelected) Color.Blue else Color.LightGray,
                shape = RoundedCornerShape(5.dp)
            )
    )
}
