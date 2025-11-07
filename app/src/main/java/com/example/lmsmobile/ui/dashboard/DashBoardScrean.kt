package com.example.lmsmobile.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(
    studentIndex: String,
    studentName: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Welcome, $studentName!",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 26.sp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Index Number: $studentIndex",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "This is your LMS dashboard.",
                style = MaterialTheme.typography.bodyMedium
            )

            // TODO: Add dashboard content (cards, lists, etc.)
        }
    }
}