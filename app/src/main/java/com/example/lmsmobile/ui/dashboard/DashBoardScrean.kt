package com.example.lmsmobile.ui.dashboard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lmsmobile.data.network.RetrofitClient
import com.example.lmsmobile.data.repository.TaskRepository
import com.example.lmsmobile.navigation.Routes
import com.example.lmsmobile.ui.dashboard.components.DashboardTopBar
import com.example.lmsmobile.ui.dashboard.components.SideBar
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DashboardScreen(
    studentIndex: String,
    studentName: String,
    degreeId: Long,
    navController: NavHostController
) {
    val decodedName = URLDecoder.decode(studentName, StandardCharsets.UTF_8.name())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Auto-close drawer when returning to Dashboard
    LaunchedEffect(Unit) {
        drawerState.close()
    }

    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(TaskRepository(RetrofitClient.apiService))
    )
    val tasks by taskViewModel.tasks.collectAsState()

    LaunchedEffect(degreeId) {
        Log.d("DashboardScreen", "Loading tasks for degreeId: $degreeId")
        taskViewModel.loadTasks(degreeId)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF090979), Color(0xFF4B6CB7))
                        )
                    )
            ) {
                Spacer(modifier = Modifier.height(90.dp))
                SideBar(onItemClick = { label ->
                    when (label) {
                        "Results" -> {
                            val encodedName = URLEncoder.encode(studentName.trim(), StandardCharsets.UTF_8.name())
                            val route = Routes.resultsRoute(studentIndex, encodedName, degreeId)
                            navController.navigate(route)
                        }
                        "Subjects" -> { /* TODO */ }
                        "Profile" -> { /* TODO */ }
                    }
                })
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // TopBar with no title
            DashboardTopBar(
                title = "",
                drawerState = drawerState,
                scope = scope,
                onNotificationClick = { /* TODO */ },
                onProfileClick = { /* TODO */ }
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Welcome message
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Welcome, ",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 22.sp)
                )
                Text(
                    text = decodedName,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "\uD83D\uDC4B", // 👋 waving hand emoji
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Section title
                Text("📋 Task Schedule", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))

                // Task list
                if (tasks.isEmpty()) {
                    Text("No tasks available.", style = MaterialTheme.typography.bodyMedium)
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(tasks) { task ->
                            Log.d("DashboardScreen", "Rendering task: ${task.name}")
                            TaskCard(task)
                        }
                    }
                }
            }
        }
    }
}