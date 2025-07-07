package com.example.btvn_mvvm.data.repository

import com.example.btvn_mvvm.data.model.HomeworkTask

class HomeworkRepositoryImpl private constructor() : HomeworkRepository {
    companion object {
        @Volatile
        private var INSTANCE: HomeworkRepositoryImpl? = null

        fun getInstance(): HomeworkRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomeworkRepositoryImpl().also { INSTANCE = it }
            }
        }
    }

    private val tasks = mutableListOf<HomeworkTask>()
    private var nextId = 1

    override suspend fun getAllTasks(): List<HomeworkTask> {
        return tasks.toList()
    }

    override suspend fun addTask(task: HomeworkTask) {
        tasks.add(task.copy(id = nextId++))
    }

    override suspend fun updateTask(task: HomeworkTask) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
        }
    }

    override suspend fun deleteTask(id: Int) {
        tasks.removeAll { it.id == id }
    }
}