package com.example.lmsmobile.data.model

data class Result(
    val id: Long,
    val indexNumber: String,
    val subjectName: String,
    val courseworkGrade: String,
    val examGrade: String,
    val gpa: Double
)