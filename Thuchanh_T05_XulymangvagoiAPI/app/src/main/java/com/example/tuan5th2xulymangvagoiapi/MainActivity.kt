package com.example.tuan5th2xulymangvagoiapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tuan5th2xulymangvagoiapi.ui.ProductDetailScreen // Import màn hình mới của bạn
import com.example.tuan5th2xulymangvagoiapi.ui.theme.Tuan5TH2XuLyMangVaGoiAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tuan5TH2XuLyMangVaGoiAPITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductDetailScreen() // HIỂN THỊ MÀN HÌNH CHI TIẾT SẢN PHẨM
                }
            }
        }
    }
}

// Giữ lại hoặc xóa Preview nếu bạn muốn
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun DefaultPreview() {
    Tuan5TH2XuLyMangVaGoiAPITheme {
        ProductDetailScreen()
    }
}

