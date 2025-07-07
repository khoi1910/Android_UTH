package com.example.btvn_t06.models

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val category: String,
    val priority: TaskPriority,
    val dueDate: String,
    val subtasks: List<Subtask> = emptyList(),
    val attachments: List<String> = emptyList()
) {
    fun toggleCompletion(): Task {
        val newStatus = if (status == TaskStatus.COMPLETED) {
            TaskStatus.PENDING
        } else {
            TaskStatus.COMPLETED
        }
        return copy(status = newStatus)
    }

    fun updateSubtask(subtaskId: Int, isCompleted: Boolean): Task {
        val updatedSubtasks = subtasks.map { subtask ->
            if (subtask.id == subtaskId) {
                subtask.copy(isCompleted = isCompleted)
            } else {
                subtask
            }
        }
        return copy(subtasks = updatedSubtasks)
    }
}

data class Subtask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean
)

enum class TaskStatus(val displayName: String) {
    IN_PROGRESS("In Progress"),
    PENDING("Pending"),
    COMPLETED("Completed")
}

enum class TaskPriority(val displayName: String) {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low")
}