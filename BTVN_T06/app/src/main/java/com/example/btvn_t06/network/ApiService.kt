package com.example.btvn_t06.network

import com.example.btvn_t06.models.Task
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("tasks")
    suspend fun getAllTasks(): Response<List<Task>>

    @GET("task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): Response<Task>

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") id: Int): Response<Unit>
}