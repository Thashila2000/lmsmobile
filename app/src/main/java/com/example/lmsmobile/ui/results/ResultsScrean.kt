package com.example.lmsmobile.ui.results

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lmsmobile.data.network.RetrofitClient
import com.example.lmsmobile.data.repository.ResultRepository
import com.example.lmsmobile.ui.dashboard.components.DashboardTopBar

@Composable
fun ResultsScreen(
    indexNumber: String,
    studentName: String,
    degreeId: Long,
    navController: NavHostController
) {
    val viewModel: ResultViewModel = viewModel(
        factory = ResultViewModelFactory(ResultRepository(RetrofitClient.apiService))
    )
    val results by viewModel.results.collectAsState()

    LaunchedEffect(indexNumber) {
        Log.d("ResultsScreen", "Loading results for index: '$indexNumber'")
        viewModel.loadResults(indexNumber.trim())
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 🔹 TopBar with back navigation
        DashboardTopBar(
            title = "",
            scope = rememberCoroutineScope(),
            showBackButton = true,
            onBackClick = {
                navController.popBackStack()
            },
            onNotificationClick = {},
            onProfileClick = {}
        )

        // 🔹 Title below TopBar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Assessment,
                contentDescription = "Results Icon",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "📊 Results",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        // 🔹 Results Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            if (results.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No results available.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(results) { result ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = result.subjectName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Coursework Grade: ${result.courseworkGrade}")
                                Text("Exam Grade: ${result.examGrade}")
                                Text("GPA: ${result.gpa}")
                            }
                        }
                    }
                }
            }
        }
    }
}