package com.example.th02t04.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.th02t04.screens.SplashScreen
import com.example.th02t04.screens.HomeScreen
import com.example.th02t04.screens.OnboardingScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen() }
        composable("onboarding") { OnboardingScreen(navController) }
    }
}
