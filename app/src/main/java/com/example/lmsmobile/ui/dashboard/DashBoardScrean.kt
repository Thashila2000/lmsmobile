package com.example.lmsmobile.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.lmsmobile.navigation.Routes
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DashboardScreen(
    navController: NavHostController,
    studentIndex: String,
    studentName: String
) {
    // Decode name in case it was URL-encoded (e.g., dots replaced with %2E)
    val decodedName = URLDecoder.decode(studentName, StandardCharsets.UTF_8.name())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Welcome, $decodedName!",
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
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { navController.navigate(Routes.NOTES) }) {
                Text("Notes")
            }

            // TODO: Add dashboard content (cards, lists, etc.)
        }
    }
}