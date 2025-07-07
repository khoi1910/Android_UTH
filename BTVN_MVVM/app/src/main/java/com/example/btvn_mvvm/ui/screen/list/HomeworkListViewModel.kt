package com.example.btvn_mvvm.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btvn_mvvm.data.model.HomeworkTask
import com.example.btvn_mvvm.data.repository.HomeworkRepository
import com.example.btvn_mvvm.data.repository.HomeworkRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeworkListViewModel(
    private val repository: HomeworkRepository = HomeworkRepositoryImpl.getInstance()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeworkListUiState())
    val uiState: StateFlow<HomeworkListUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val tasks = repository.getAllTasks()
                _uiState.value = _uiState.value.copy(
                    tasks = tasks,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun toggleTaskCompletion(task: HomeworkTask) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !task.isCompleted))
            loadTasks()
        }
    }

    // Thêm method để refresh data từ bên ngoài
    fun refreshTasks() {
        loadTasks()
    }
}

data class HomeworkListUiState(
    val tasks: List<HomeworkTask> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)