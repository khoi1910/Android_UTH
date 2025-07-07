package com.example.btvnt05

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: AuthViewModel
) {
    val user by viewModel.user

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate("profile") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("profile") {
            user?.let { currentUser ->
                ProfileScreen(
                    user = currentUser,
                    onBackClick = {
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}