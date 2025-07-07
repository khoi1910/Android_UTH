package com.example.btvn_mvvm.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btvn_mvvm.data.model.HomeworkTask
import com.example.btvn_mvvm.data.repository.HomeworkRepository
import com.example.btvn_mvvm.data.repository.HomeworkRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddHomeworkViewModel(
    private val repository: HomeworkRepository = HomeworkRepositoryImpl.getInstance()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddHomeworkUiState())
    val uiState: StateFlow<AddHomeworkUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun addTask(onSuccess: () -> Unit) {
        val currentState = _uiState.value

        if (currentState.title.isBlank()) {
            _uiState.value = currentState.copy(error = "Tiêu đề không được để trống")
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, error = null)
            try {
                val task = HomeworkTask(
                    title = currentState.title,
                    description = currentState.description
                )
                repository.addTask(task)
                _uiState.value = AddHomeworkUiState() // Reset form
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    error = e.message ?: "Có lỗi xảy ra"
                )
            }
        }
    }
}

data class AddHomeworkUiState(
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)