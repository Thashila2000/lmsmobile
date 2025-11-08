package com.example.lmsmobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lmsmobile.ui.dashboard.DashboardScreen
import com.example.lmsmobile.ui.dashboard.TaskScheduleScreen
import com.example.lmsmobile.ui.login.LoginScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        modifier = modifier
    ) {
        // Login screen
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { response ->
                    val safeIndex = response.indexNumber.ifBlank { "unknown" }
                    val safeName = response.fullName.ifBlank { "Student" }
                    val encodedName = URLEncoder.encode(safeName.trim(), StandardCharsets.UTF_8.name())
                    val degreeId = response.degree?.id ?: 0L
                    val dashboardRoute = Routes.dashboardRoute(safeIndex, encodedName, degreeId)
                    navController.navigate(dashboardRoute)
                }
            )
        }

        //  Dashboard screen
        composable(
            route = Routes.DASHBOARD,
            arguments = listOf(
                navArgument("studentIndex") { type = NavType.StringType },
                navArgument("studentName") { type = NavType.StringType },
                navArgument("degreeId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val studentIndex = backStackEntry.arguments?.getString("studentIndex") ?: "unknown"
            val encodedName = backStackEntry.arguments?.getString("studentName") ?: "Student"
            val decodedName = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.name())
            val degreeId = backStackEntry.arguments?.getLong("degreeId") ?: 0L

            DashboardScreen(
                studentIndex = studentIndex,
                studentName = decodedName,
                degreeId = degreeId
            )
        }

        // Task Schedule screen
        composable(
            route = Routes.TASK_SCHEDULE,
            arguments = listOf(
                navArgument("degreeId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val degreeId = backStackEntry.arguments?.getLong("degreeId") ?: 0L
            TaskScheduleScreen(degreeId = degreeId)
        }
    }
}