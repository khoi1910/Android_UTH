package com.example.thuchanh01_tuan03


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstAppUI()
        }
    }
}

@Composable
fun MyFirstAppUI() {
    var showName by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề trên cùng
        Text(
            text = "My First App",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Dòng chữ thay đổi
        if (!showName) {
            Text(text = "Hello", fontSize = 20.sp)
        } else {
            Text(
                text = buildAnnotatedString {
                    append("I'm\n")
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("Minh Khôi")
                    pop()
                },
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Nút nhấn
        Button(
            onClick = { showName = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0D47A1),
                contentColor = Color.White
            ),
            modifier = Modifier
                .width(160.dp)
                .height(50.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Say Hi!", fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyFirstApp() {
    MyFirstAppUI()
}
