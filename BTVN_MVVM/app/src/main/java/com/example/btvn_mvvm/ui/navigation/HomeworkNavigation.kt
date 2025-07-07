package com.example.btvn_mvvm.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.btvn_mvvm.ui.screen.add.AddHomeworkScreen
import com.example.btvn_mvvm.ui.screen.list.HomeworkListScreen
import com.example.btvn_mvvm.ui.screen.list.HomeworkListViewModel

@Composable
fun HomeworkNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") { backStackEntry ->
            val viewModel: HomeworkListViewModel = viewModel()

            // Refresh data khi navigate back từ add screen
            LaunchedEffect(backStackEntry) {
                viewModel.refreshTasks()
            }

            HomeworkListScreen(
                onNavigateToAdd = {
                    navController.navigate("add")
                },
                viewModel = viewModel
            )
        }
        composable("add") {
            AddHomeworkScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}