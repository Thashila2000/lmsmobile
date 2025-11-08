package com.example.lmsmobile.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lmsmobile.data.model.TaskDto
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskCard(task: TaskDto) {
    val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val displayFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    val start = try { isoFormat.parse(task.computedStart) } catch (e: Exception) { null }
    val end = try { isoFormat.parse(task.computedEnd) } catch (e: Exception) { null }
    val now = Date()

    val isExpired = end?.before(now) == true
    val statusColor = if (isExpired) Color.Red else Color(0xFF4CAF50)
    val statusText = if (isExpired) "Expired" else "Available"

    val startFormatted = start?.let { displayFormat.format(it) } ?: "Invalid"
    val endFormatted = end?.let { displayFormat.format(it) } ?: "Invalid"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = statusColor.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = "Start: $startFormatted")
            Text(text = "Duration: ${task.duration} hour(s)")
            Text(text = "End: $endFormatted")
            Spacer(Modifier.height(4.dp))
            Text(text = statusText, color = statusColor, style = MaterialTheme.typography.bodyMedium)
        }
    }
}