package com.example.btvn_t06.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btvn_t06.models.Task
import com.example.btvn_t06.models.TaskStatus
import com.example.btvn_t06.models.TaskPriority
import com.example.btvn_t06.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onTaskClick: (Int) -> Unit,
    onAddTaskClick: () -> Unit,
    viewModel: TaskViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState(initial = emptyList()) // Added initial value
    val isLoading by viewModel.isLoading.collectAsState(initial = false) // Added initial value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(0xFFE53E3E), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "ST",
                                color = Color.White,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "SmartTasks",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2B6CB0)
                            )
                            Text(
                                text = "A simple and efficient to-do app",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle notification */ }) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFFED8936)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.height(80.dp)
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            tint = Color(0xFF2196F3)
                        )
                    },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2196F3),
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = "Calendar",
                            tint = Color.Gray
                        )
                    },
                    selected = false,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2196F3),
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    Color(0xFF2196F3),
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { onAddTaskClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    selected = false,
                    onClick = onAddTaskClick,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2196F3),
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Assignment,
                            contentDescription = "Tasks",
                            tint = Color.Gray
                        )
                    },
                    selected = false,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2196F3),
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.Gray
                        )
                    },
                    selected = false,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2196F3),
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Assignment,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Tasks Yet!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Stay productive - add something to do",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(tasks) { task ->
                    TaskCard(
                        task = task,
                        onClick = { onTaskClick(task.id) },
                        onToggleComplete = { viewModel.toggleTaskCompletion(task.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onClick: () -> Unit,
    onToggleComplete: () -> Unit
) {
    val backgroundColor = when (task.status) {
        TaskStatus.IN_PROGRESS -> Color(0xFFE53E3E).copy(alpha = 0.1f)
        TaskStatus.PENDING -> Color(0xFFED8936).copy(alpha = 0.1f)
        TaskStatus.COMPLETED -> Color(0xFF38A169).copy(alpha = 0.1f)
    }

    val statusColor = when (task.status) {
        TaskStatus.IN_PROGRESS -> Color(0xFFE53E3E)
        TaskStatus.PENDING -> Color(0xFFED8936)
        TaskStatus.COMPLETED -> Color(0xFF38A169)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.description,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Checkbox(
                    checked = task.status == TaskStatus.COMPLETED,
                    onCheckedChange = { onToggleComplete() },
                    colors = CheckboxDefaults.colors(
                        checkedColor = statusColor
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status: ${task.status.displayName}",
                    fontSize = 12.sp,
                    color = statusColor,
                    fontWeight = FontWeight.Medium
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = task.dueDate,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Priority and Category tags
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Priority tag
                val priorityColor = when (task.priority) {
                    TaskPriority.HIGH -> Color(0xFFE53E3E)
                    TaskPriority.MEDIUM -> Color(0xFFED8936)
                    TaskPriority.LOW -> Color(0xFF38A169)
                }

                Box(
                    modifier = Modifier
                        .background(
                            priorityColor.copy(alpha = 0.2f),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = task.priority.displayName,
                        fontSize = 10.sp,
                        color = priorityColor,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Category tag
                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFF2B6CB0).copy(alpha = 0.2f),
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = task.category,
                        fontSize = 10.sp,
                        color = Color(0xFF2B6CB0),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}