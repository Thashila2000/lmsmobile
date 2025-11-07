package com.example.lmsmobile.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Routes {
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard/{studentIndex}/{studentName}"

    fun dashboardRoute(index: String, name: String): String {
        return "dashboard/${index.trim()}/${
            URLEncoder.encode(name.trim(), StandardCharsets.UTF_8.name())
        }"
    }

    // Notes Routes
    const val NOTES = "notes"
    const val ADD_NOTE = "add_note"
}