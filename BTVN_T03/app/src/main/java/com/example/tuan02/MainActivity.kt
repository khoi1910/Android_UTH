package com.example.tuan02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*

import com.example.tuan02.ui.theme.Tuan02Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tuan02Theme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(navController)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("ui_components") { UIComponentListScreen(navController) }
        composable("text_detail") { TextDetailScreen(navController) }
        composable("image_detail") { ImageDetailScreen(navController) }
        composable("textfield_screen") { TextFieldScreen(navController) }
        composable("row_screen") { RowLayoutScreen(navController) }
        composable("column_screen") { ColumnLayoutScreen(navController) }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text("Nguyễn Bùi Minh Nhật", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("2251120033", fontSize = 14.sp)
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.jetpack_compose_logo),
                contentDescription = "Jetpack Compose Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("Jetpack Compose", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }

        Button(
            onClick = { navController.navigate("ui_components") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
        ) {
            Text("I'm ready", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun UIComponentListScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "UI Components List",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp)
        )

        Section("Display") {
            UIComponentItem("Text", "Displays text") {
                navController.navigate("text_detail")
            }
            UIComponentItem("Image", "Displays an image") {
                navController.navigate("image_detail")
            }

        }

        Section("Input") {
            UIComponentItem("TextField", "Input field for text") {
                navController.navigate("textfield_screen")
            }

            UIComponentItem("PasswordField", "Input field for passwords")
        }

        Section("Layout") {
            UIComponentItem("Column", "Arranges elements vertically") {
                navController.navigate("column_screen")
            }
            UIComponentItem("Row", "Arranges elements horizontally") {
                navController.navigate("row_screen")
            }

        }
    }
}

@Composable
fun Section(title: String, content: @Composable () -> Unit) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    content()
}

@Composable
fun UIComponentItem(title: String, description: String, onClick: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(Color(0xFFBBDEFB), shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
            .let {
                if (onClick != null) it.clickable(onClick = onClick) else it
            }
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(text = description)
    }
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TextDetailScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Text Detail",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2196F3)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF2196F3)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 26.sp)) {
                        append("The ")
                    }
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                        append("quick ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFBF7B2F),
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Brown\n")
                    }
                    append("fox j u m p s ")
                    withStyle(
                        style = SpanStyle(
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("over\n")
                    }
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("the ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("lazy")
                    }
                    append(" dog.")
                },
                fontSize = 32.sp,
                lineHeight = 42.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Images", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color(0xFF2196F3))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF2196F3))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_gtvt),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = "https://giaothongvantai.hcm.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png",
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ảnh từ trong app
            Image(
                painter = painterResource(id = R.drawable.u1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 8.dp)
            )
            Text("In app", fontSize = 14.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldScreen(navController: NavHostController) {
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "TextField",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2196F3)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF2196F3)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {
                        Text("Thông tin nhập", color = Color.Gray)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (text.isEmpty()) "Tự động cập nhật dữ liệu theo textfield" else text,
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowLayoutScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Row Layout",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2196F3)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF2196F3)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(4) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp)
                            .background(Color(0xFF90CAF9), RoundedCornerShape(12.dp))
                    )
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp)
                            .background(Color(0xFF42A5F5), RoundedCornerShape(12.dp))
                    )
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp)
                            .background(Color(0xFF90CAF9), RoundedCornerShape(12.dp))
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnLayoutScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Column Layout",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2196F3)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF2196F3)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp))
                    .padding(vertical = 24.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .height(140.dp)
                        .background(Color(0xFFA5D6A7), RoundedCornerShape(20.dp))
                )
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .height(140.dp)
                        .background(Color(0xFF66BB6A), RoundedCornerShape(20.dp))
                )
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .height(140.dp)
                        .background(Color(0xFFA5D6A7), RoundedCornerShape(20.dp))
                )
            }
        }
    }
}








