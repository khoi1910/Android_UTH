package com.example.btvn_t06.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.btvn_t06.models.TaskStatus
import com.example.btvn_t06.models.TaskPriority
import com.example.btvn_t06.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Int,
    onBackClick: () -> Unit,
    viewModel: TaskViewModel = viewModel()
) {
    val selectedTask by viewModel.selectedTask.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(taskId) {
        viewModel.loadTaskById(taskId) // Correct method from TaskViewModel
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle edit */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = {
                        viewModel.deleteTask(taskId)
                        onBackClick()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
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
        } else {
            selectedTask?.let { task ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Task Header
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = when (task.status) {
                                    TaskStatus.IN_PROGRESS -> Color(0xFFE53E3E).copy(alpha = 0.1f)
                                    TaskStatus.PENDING -> Color(0xFFED8936).copy(alpha = 0.1f)
                                    TaskStatus.COMPLETED -> Color(0xFF38A169).copy(alpha = 0.1f)
                                }
                            )
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
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = task.description,
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                    }

                                    Checkbox(
                                        checked = task.status == TaskStatus.COMPLETED,
                                        onCheckedChange = { viewModel.toggleTaskCompletion(task.id) },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = when (task.status) {
                                                TaskStatus.IN_PROGRESS -> Color(0xFFE53E3E)
                                                TaskStatus.PENDING -> Color(0xFFED8936)
                                                TaskStatus.COMPLETED -> Color(0xFF38A169)
                                            }
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Task Info
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Task Information",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                // Status
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Status:",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = task.status.displayName,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = when (task.status) {
                                            TaskStatus.IN_PROGRESS -> Color(0xFFE53E3E)
                                            TaskStatus.PENDING -> Color(0xFFED8936)
                                            TaskStatus.COMPLETED -> Color(0xFF38A169)
                                        }
                                    )
                                }

                                // Priority
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Priority:",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = task.priority.displayName,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = when (task.priority) {
                                            TaskPriority.HIGH -> Color(0xFFE53E3E)
                                            TaskPriority.MEDIUM -> Color(0xFFED8936)
                                            TaskPriority.LOW -> Color(0xFF38A169)
                                        }
                                    )
                                }

                                // Category
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Category:",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = task.category,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF2B6CB0)
                                    )
                                }

                                // Due Date
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Due Date:",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = task.dueDate,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    // Subtasks
                    if (task.subtasks.isNotEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Subtasks (${task.subtasks.count { it.isCompleted }}/${task.subtasks.size})",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }

                        items(task.subtasks) { subtask ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (subtask.isCompleted)
                                        Color(0xFF38A169).copy(alpha = 0.1f)
                                    else Color.White
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = subtask.isCompleted,
                                        onCheckedChange = { _ ->
                                            viewModel.toggleSubtaskCompletion(task.id, subtask.id)
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(0xFF38A169)
                                        )
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = subtask.title,
                                        fontSize = 14.sp,
                                        color = if (subtask.isCompleted) Color.Gray else Color.Black
                                    )
                                }
                            }
                        }
                    }

                    // Attachments
                    if (task.attachments.isNotEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "Attachments",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))

                                    task.attachments.forEach { attachment ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Default.AttachFile,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp),
                                                tint = Color(0xFF2B6CB0)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = attachment,
                                                fontSize = 14.sp,
                                                color = Color(0xFF2B6CB0)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Task not found",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}