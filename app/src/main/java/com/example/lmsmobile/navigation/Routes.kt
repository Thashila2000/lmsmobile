package com.example.lmsmobile.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Routes {
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard/{studentIndex}/{studentName}/{degreeId}"
    const val TASK_SCHEDULE = "tasks/{degreeId}"

    fun dashboardRoute(index: String, name: String, degreeId: Long): String {
        val encodedName = URLEncoder.encode(name.trim(), StandardCharsets.UTF_8.name())
        return "dashboard/${index.trim()}/$encodedName/$degreeId"
    }

    fun taskScheduleRoute(degreeId: Long): String {
        return "tasks/$degreeId"
    }
}