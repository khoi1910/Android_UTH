package com.example.btvn_t02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practice01Screen()
        }
    }
}

@Composable
fun Practice01Screen() {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var ageText by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        // Tiêu đề
        Text(
            text = "THỰC HÀNH 01",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Khung nhập
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD3D3D3), RoundedCornerShape(12.dp)) // xám sáng
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Họ và tên",
                        modifier = Modifier.width(100.dp),
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tuổi",
                        modifier = Modifier.width(100.dp),
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = ageText,
                        onValueChange = { ageText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Nút kiểm tra
        Button(
            onClick = {
                val age = ageText.text.toIntOrNull()
                result = if (name.text.isBlank() || age == null) {
                    "Vui lòng nhập đầy đủ và đúng định dạng."
                } else {
                    val group = when {
                        age > 65 -> "Người già"
                        age in 6..65 -> "Người lớn"
                        age in 2..5 -> "Trẻ em"
                        age in 0..1 -> "Em bé"
                        else -> "Tuổi không hợp lệ"
                    }
                    "${name.text} - $group"
                }
            },
            modifier = Modifier
                .width(160.dp)
                .height(50.dp)
                .shadow(4.dp, RoundedCornerShape(8.dp)), // Bóng đổ giống hình
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0D47A1), // Xanh đậm
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Kiểm tra", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (result.isNotBlank()) {
            Text(
                text = result,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPractice01() {
    Practice01Screen()
}
