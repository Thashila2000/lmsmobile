package com.example.lmsmobile.ui.dashboard

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lmsmobile.data.network.RetrofitClient
import com.example.lmsmobile.data.repository.TaskRepository
import com.example.lmsmobile.ui.dashboard.components.DashboardTopBar
import com.example.lmsmobile.ui.dashboard.components.SideBar
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DashboardScreen(
    studentIndex: String,
    studentName: String,
    degreeId: Long
) {
    val decodedName = URLDecoder.decode(studentName, StandardCharsets.UTF_8.name())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
            SideBar(onItemClick = { label ->
                // TODO: Handle navigation based on label
            })
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            DashboardTopBar(
                drawerState = drawerState,
                scope = scope,
                onSearch = { query -> /* TODO */ },
                onNotificationClick = { /* TODO */ },
                onProfileClick = { /* TODO */ }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome, ",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 18.sp)
                    )
                    Text(
                        text = decodedName,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("📋 Task Schedule", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))

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