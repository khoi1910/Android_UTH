package com.example.btvn_t06

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.btvn_t06.ui.screens.AddTaskScreen
import com.example.btvn_t06.ui.screens.TaskDetailScreen
import com.example.btvn_t06.ui.screens.TaskListScreen

@Composable
fun SmartTasksApp(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "task_list"
    ) {
        composable("task_list") {
            TaskListScreen(
                onTaskClick = { taskId ->
                    navController.navigate("task_detail/$taskId")
                },
                onAddTaskClick = {
                    navController.navigate("add_task")
                }
            )
        }
        composable("task_detail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull() ?: 1
            TaskDetailScreen(
                taskId = taskId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("add_task") {
            AddTaskScreen(
                onBackClick = { navController.popBackStack() },
                onTaskAdded = {
                    navController.popBackStack()
                }
            )
        }
    }
}