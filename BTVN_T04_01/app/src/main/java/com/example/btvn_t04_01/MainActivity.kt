package com.example.btvn_t04_01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartTasksTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ForgotPasswordNavigation(navController)
                }
            }
        }
    }
}

@Composable
fun SmartTasksTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

@Composable
fun ForgotPasswordNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "forgot_password") {
        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }
        composable("verification/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerificationScreen(navController, email)
        }
        composable("reset_password/{email}/{verificationCode}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val verificationCode = backStackEntry.arguments?.getString("verificationCode") ?: ""
            ResetPasswordScreen(navController, email, verificationCode)
        }
        composable("confirm/{email}/{verificationCode}/{password}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val verificationCode = backStackEntry.arguments?.getString("verificationCode") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            ConfirmScreen(navController, email, verificationCode, password)
        }
    }
}

// Updated UTH Logo component using drawable resource
@Composable
fun UTHLogo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.uth_logo),
            contentDescription = "UTH Logo",
            modifier = Modifier.size(120.dp)
        )
    }
}

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp)) // Reduced from 60.dp

        UTHLogo()

        Spacer(modifier = Modifier.height(12.dp)) // Reduced from 16.dp

        Text(
            text = "SmartTasks",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3)
        )

        Spacer(modifier = Modifier.height(40.dp)) // Reduced from 60.dp

        Text(
            text = "Forgot Password?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your Email, we will send you a verification\ncode.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = true
            },
            placeholder = { Text("Your Email", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = !isEmailValid,
            supportingText = if (!isEmailValid) {
                { Text("Please enter a valid email address", color = MaterialTheme.colorScheme.error) }
            } else null
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (email.isNotEmpty() && isValidEmail(email)) {
                    try {
                        val encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString())
                        navController.navigate("verification/$encodedEmail")
                    } catch (e: Exception) {
                        // Handle encoding error
                        isEmailValid = false
                    }
                } else {
                    isEmailValid = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
        ) {
            Text(
                text = "Next",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun VerificationScreen(navController: NavHostController, email: String) {
    var verificationCode by remember { mutableStateOf(List(5) { "" }) }
    val focusRequesters = remember { List(5) { FocusRequester() } }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button positioned at top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        UTHLogo()

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "SmartTasks",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Verify Code",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter the the code\nwe just sent you on your registered Email",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 5 Code input boxes with auto-focus
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(5) { index ->
                OutlinedTextField(
                    value = verificationCode[index],
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && (newValue.isEmpty() || newValue.all { it.isDigit() })) {
                            val newCode = verificationCode.toMutableList()
                            newCode[index] = newValue
                            verificationCode = newCode

                            // Auto-focus next field when current field is filled
                            if (newValue.isNotEmpty() && index < 4) {
                                focusRequesters[index + 1].requestFocus()
                            }
                            // Auto-focus previous field when current field is emptied
                            else if (newValue.isEmpty() && index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                            // Hide keyboard when last field is filled
                            else if (newValue.isNotEmpty() && index == 4) {
                                keyboardController?.hide()
                            }
                        }
                    },
                    modifier = Modifier
                        .width(55.dp)
                        .height(65.dp)
                        .focusRequester(focusRequesters[index]),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.4f),
                        focusedContainerColor = Color(0xFF2196F3).copy(alpha = 0.05f),
                        unfocusedContainerColor = Color.Gray.copy(alpha = 0.05f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val code = verificationCode.joinToString("")
                if (code.length == 5) {
                    try {
                        val encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString())
                        navController.navigate("reset_password/$encodedEmail/$code")
                    } catch (e: Exception) {
                        // Handle navigation error
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3),
                disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
            ),
            enabled = verificationCode.all { it.isNotEmpty() }
        ) {
            Text(
                text = "Next",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ResetPasswordScreen(navController: NavHostController, email: String, verificationCode: String) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordsMatch by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2196F3)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp)) // Reduced from 40.dp

        UTHLogo()

        Spacer(modifier = Modifier.height(12.dp)) // Reduced from 16.dp

        Text(
            text = "SmartTasks",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3)
        )

        Spacer(modifier = Modifier.height(40.dp)) // Reduced from 60.dp

        Text(
            text = "Create new password",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your new password must be different from\npreviously used password",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordsMatch = true
            },
            placeholder = { Text("Password", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                passwordsMatch = true
            },
            placeholder = { Text("Confirm Password", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = !passwordsMatch,
            supportingText = if (!passwordsMatch) {
                { Text("Passwords do not match", color = MaterialTheme.colorScheme.error) }
            } else null
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (password.isNotEmpty() && password == confirmPassword) {
                    try {
                        val encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString())
                        val encodedPassword = URLEncoder.encode(password, StandardCharsets.UTF_8.toString())
                        navController.navigate("confirm/$encodedEmail/$verificationCode/$encodedPassword")
                    } catch (e: Exception) {
                        passwordsMatch = false
                    }
                } else {
                    passwordsMatch = false
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            enabled = password.isNotEmpty() && confirmPassword.isNotEmpty()
        ) {
            Text(
                text = "Next",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ConfirmScreen(
    navController: NavHostController,
    email: String,
    verificationCode: String,
    password: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2196F3)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp)) // Reduced from 40.dp

        UTHLogo()

        Spacer(modifier = Modifier.height(12.dp)) // Reduced from 16.dp

        Text(
            text = "SmartTasks",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3)
        )

        Spacer(modifier = Modifier.height(40.dp)) // Reduced from 60.dp

        Text(
            text = "Confirm",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We are here to help you!",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Gray.copy(alpha = 0.4f),
                    disabledContainerColor = Color.Gray.copy(alpha = 0.05f),
                    disabledTextColor = Color.Black,
                    disabledLeadingIconColor = Color.Gray
                )
            )

            // Verification code field
            OutlinedTextField(
                value = verificationCode,
                onValueChange = { },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Gray.copy(alpha = 0.4f),
                    disabledContainerColor = Color.Gray.copy(alpha = 0.05f),
                    disabledTextColor = Color.Black,
                    disabledLeadingIconColor = Color.Gray
                )
            )

            // Password field
            OutlinedTextField(
                value = "•".repeat(password.length),
                onValueChange = { },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Gray.copy(alpha = 0.4f),
                    disabledContainerColor = Color.Gray.copy(alpha = 0.05f),
                    disabledTextColor = Color.Black,
                    disabledLeadingIconColor = Color.Gray
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.popBackStack("forgot_password", inclusive = false)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
        ) {
            Text(
                text = "Submit",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}