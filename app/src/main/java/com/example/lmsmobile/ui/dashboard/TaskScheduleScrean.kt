package com.example.lmsmobile.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lmsmobile.data.network.RetrofitClient
import com.example.lmsmobile.data.repository.TaskRepository


@Composable
fun TaskScheduleScreen(degreeId: Long) {
    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(TaskRepository(RetrofitClient.apiService))
    )
    val tasks by taskViewModel.tasks.collectAsState()
    val isLoading by taskViewModel.isLoading.collectAsState()
    val errorMessage by taskViewModel.errorMessage.collectAsState()

    LaunchedEffect(degreeId) {
        taskViewModel.loadTasks(degreeId)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("📋 Task Schedule", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage.isNotEmpty() -> {
                Text("❌ Error: $errorMessage", color = MaterialTheme.colorScheme.error)
            }
            tasks.isEmpty() -> {
                Text("No tasks found for this degree.")
            }
            else -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(tasks) { task ->
                        TaskCard(task)
                    }
                }
            }
        }
    }
}