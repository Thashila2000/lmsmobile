package com.example.lmsmobile.data.model

data class TaskDto(
    val id: Long,
    val name: String,
    val duration: Long,
    val priority: Int,
    val computedStart: String,
    val computedEnd: String,
    val degreeId: Long,
    val degreeName: String
)