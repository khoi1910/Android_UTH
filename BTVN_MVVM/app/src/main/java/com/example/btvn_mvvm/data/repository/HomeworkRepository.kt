package com.example.btvn_mvvm.data.repository

import com.example.btvn_mvvm.data.model.HomeworkTask

interface HomeworkRepository {
    suspend fun getAllTasks(): List<HomeworkTask>
    suspend fun addTask(task: HomeworkTask)
    suspend fun updateTask(task: HomeworkTask)
    suspend fun deleteTask(id: Int)
}