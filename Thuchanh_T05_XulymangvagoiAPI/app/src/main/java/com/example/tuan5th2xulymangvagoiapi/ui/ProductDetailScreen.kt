package com.example.tuan5th2xulymangvagoiapi.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.* // Import remember, mutableStateOf, LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter // Import Coil cho ảnh từ URL
import com.example.tuan5th2xulymangvagoiapi.api.RetrofitClient
import com.example.tuan5th2xulymangvagoiapi.data.Product
import com.example.tuan5th2xulymangvagoiapi.ui.theme.Tuan5TH2XuLyMangVaGoiAPITheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProductDetailScreen() {
    var product by remember { mutableStateOf<Product?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Coroutine để gọi API khi Composable được khởi tạo
    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) { // Chuyển sang IO dispatcher để thực hiện mạng
            try {
                val response = RetrofitClient.apiService.getProduct()
                withContext(Dispatchers.Main) { // Chuyển về Main dispatcher để cập nhật UI
                    if (response.isSuccessful) {
                        product = response.body()
                    } else {
                        errorMessage = "Error: ${response.code()} - ${response.message()}"
                    }
                    isLoading = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    errorMessage = "Network error: ${e.message}"
                    isLoading = false
                }
            }
        }
    }

    // Hiển thị giao diện dựa trên trạng thái
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator() // Hiển thị vòng tròn loading
        } else if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, fontSize = 18.sp)
        } else if (product != null) {
            ProductContent(product = product!!)
        } else {
            Text(text = "No product data available.", fontSize = 18.sp)
        }
    }
}

@Composable
fun ProductContent(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Cho phép cuộn nếu nội dung dài
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hình ảnh sản phẩm
        Image(
            painter = rememberAsyncImagePainter(product.imageUrl), // Load ảnh từ URL
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), // Chiều cao cố định cho ảnh
            contentScale = ContentScale.Crop // Cắt ảnh để vừa với kích thước
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Tên sản phẩm
        Text(
            text = product.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Giá sản phẩm
        Text(
            text = "Giá: ${String.format("%,.0f", product.price)}đ", // Định dạng giá có dấu phẩy
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.error, // Màu đỏ cho giá
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Mô tả sản phẩm
        Text(
            text = product.description,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    Tuan5TH2XuLyMangVaGoiAPITheme {
        ProductDetailScreen()
    }
}
