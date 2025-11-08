package com.example.lmsmobile.data.repository

import com.example.lmsmobile.data.network.ApiService
import com.example.lmsmobile.data.model.TaskDto

class TaskRepository(private val api: ApiService) {
    suspend fun fetchTasks(degreeId: Long): List<TaskDto> {
        return api.getScheduledTasks(degreeId)
    }
}