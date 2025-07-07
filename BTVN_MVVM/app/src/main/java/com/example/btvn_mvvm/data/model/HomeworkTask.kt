package com.example.btvn_mvvm.data.model

data class HomeworkTask(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)