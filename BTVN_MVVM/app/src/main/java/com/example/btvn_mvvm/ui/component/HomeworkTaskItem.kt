package com.example.btvn_mvvm.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.btvn_mvvm.data.model.HomeworkTask

@Composable
fun HomeworkTaskItem(
    task: HomeworkTask,
    onToggleComplete: () -> Unit
) {
    // Định nghĩa màu sắc cho các task khác nhau
    val backgroundColor = when {
        task.isCompleted -> Color(0xFFE8F5E8) // Xanh lá nhạt cho completed
        else -> {
            // Sử dụng hash của title để đảm bảo màu consistent
            val colors = listOf(
                Color(0xFFE3F2FD), // Xanh dương nhạt
                Color(0xFFFCE4EC), // Hồng nhạt
                Color(0xFFF3E5F5), // Tím nhạt
                Color(0xFFE0F2F1), // Xanh lá nhạt
                Color(0xFFFFF3E0), // Cam nhạt
                Color(0xFFE8F5E8)  // Xanh lá rất nhạt
            )
            colors[task.title.hashCode().rem(colors.size)]
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onToggleComplete
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (task.isCompleted) Color.Gray else Color.Black,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
                if (task.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = task.description,
                        fontSize = 14.sp,
                        color = if (task.isCompleted) Color.Gray else Color(0xFF666666),
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                        lineHeight = 20.sp
                    )
                }
            }

            // Checkbox hoặc indicator
            if (task.isCompleted) {
                Card(
                    shape = RoundedCornerShape(50),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                Card(
                    shape = RoundedCornerShape(50),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.size(24.dp),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color.Gray)
                ) {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}