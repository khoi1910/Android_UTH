package com.example.btvn_t06.repository

import com.example.btvn_t06.models.Task
import com.example.btvn_t06.models.TaskStatus
import com.example.btvn_t06.models.TaskPriority
import com.example.btvn_t06.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class TaskRepository {
    private val apiService = RetrofitClient.apiService // Sử dụng RetrofitClient hiện có
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: Flow<List<Task>> = _tasks.asStateFlow()

    // API call - GET /tasks
    suspend fun getAllTasks(): Result<List<Task>> {
        return try {
            val response = apiService.getAllTasks()
            if (response.isSuccessful) {
                response.body()?.let { taskList ->
                    _tasks.value = taskList
                    Result.success(taskList)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // API call - GET /task/{id}
    suspend fun getTaskById(id: Int): Result<Task> {
        return try {
            val response = apiService.getTaskById(id)
            if (response.isSuccessful) {
                response.body()?.let { task ->
                    Result.success(task)
                } ?: Result.failure(Exception("Task not found"))
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // API call - DELETE /task/{id}
    suspend fun deleteTask(taskId: Int): Result<Unit> {
        return try {
            val response = apiService.deleteTask(taskId)
            if (response.isSuccessful) {
                // Update local list after successful deletion
                _tasks.value = _tasks.value.filter { it.id != taskId }
                Result.success(Unit)
            } else {
                Result.failure(Exception("API Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // LOCAL OPERATIONS (since no API endpoints available)

    suspend fun addTask(task: Task): Result<Task> {
        return try {
            // Generate new ID based on existing tasks
            val newId = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1
            val newTask = task.copy(id = newId)

            // Add to local list
            _tasks.value = _tasks.value + newTask
            Result.success(newTask)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTask(task: Task): Result<Task> {
        return try {
            // Update in local list
            _tasks.value = _tasks.value.map {
                if (it.id == task.id) task else it
            }
            Result.success(task)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}