package com.example.btvn_t06.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btvn_t06.models.Task
import com.example.btvn_t06.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    val tasks = repository.tasks

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.getAllTasks()
                .onSuccess {
                    // Tasks are automatically updated via Flow
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message
                }

            _isLoading.value = false
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.addTask(task)
                .onSuccess {
                    // Task is automatically added to list via Flow
                }
                .onFailure { exception ->
                    _errorMessage.value = "Failed to add task: ${exception.message}"
                }

            _isLoading.value = false
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.updateTask(task)
                .onSuccess {
                    // Task is automatically updated in list via Flow
                    // Also update selectedTask if it's the same task
                    if (_selectedTask.value?.id == task.id) {
                        _selectedTask.value = task
                    }
                }
                .onFailure { exception ->
                    _errorMessage.value = "Failed to update task: ${exception.message}"
                }

            _isLoading.value = false
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.deleteTask(taskId)
                .onSuccess {
                    // Task is automatically removed from list via Flow
                }
                .onFailure { exception ->
                    _errorMessage.value = "Failed to delete task: ${exception.message}"
                }

            _isLoading.value = false
        }
    }

    fun loadTaskById(taskId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.getTaskById(taskId)
                .onSuccess { task ->
                    _selectedTask.value = task
                }
                .onFailure { exception ->
                    _errorMessage.value = "Failed to load task: ${exception.message}"
                }

            _isLoading.value = false
        }
    }

    fun toggleTaskCompletion(taskId: Int) {
        viewModelScope.launch {
            val currentTasks = (tasks as StateFlow<List<Task>>).value
            val taskToUpdate = currentTasks.find { it.id == taskId }

            taskToUpdate?.let { task ->
                val updatedTask = task.copy(
                    status = if (task.status == com.example.btvn_t06.models.TaskStatus.COMPLETED) {
                        com.example.btvn_t06.models.TaskStatus.PENDING
                    } else {
                        com.example.btvn_t06.models.TaskStatus.COMPLETED
                    }
                )
                updateTask(updatedTask)
            }
        }
    }

    // New method to handle subtask completion
    fun toggleSubtaskCompletion(taskId: Int, subtaskId: Int) {
        viewModelScope.launch {
            val currentTask = _selectedTask.value
            currentTask?.let { task ->
                val updatedSubtasks = task.subtasks.map { subtask ->
                    if (subtask.id == subtaskId) {
                        subtask.copy(isCompleted = !subtask.isCompleted)
                    } else {
                        subtask
                    }
                }
                val updatedTask = task.copy(subtasks = updatedSubtasks)
                updateTask(updatedTask)
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}