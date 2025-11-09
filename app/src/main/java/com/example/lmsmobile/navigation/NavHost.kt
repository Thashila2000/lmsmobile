package com.example.lmsmobile.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lmsmobile.ui.dashboard.DashboardScreen
import com.example.lmsmobile.ui.dashboard.TaskScheduleScreen
import com.example.lmsmobile.ui.login.LoginScreen
import com.example.lmsmobile.ui.notes.AddNoteScreen
import com.example.lmsmobile.ui.notes.NotesScreen
import com.example.lmsmobile.ui.notes.NotesViewModel
import com.example.lmsmobile.ui.results.ResultsScreen
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
        // 🔐 Login screen
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { response ->
                    val safeIndex = response.indexNumber.ifBlank { "unknown" }
                    val safeName = response.fullName.ifBlank { "Student" }
                    val encodedName = URLEncoder.encode(safeName.trim(), StandardCharsets.UTF_8.name())
                    val degreeId = response.degree?.id ?: 0L
                    val dashboardRoute = Routes.dashboardRoute(safeIndex, encodedName, degreeId)
                    navController.navigate(dashboardRoute) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.NOTES) {
            val context = LocalContext.current
            val vm: NotesViewModel = viewModel(
                factory = ViewModelProvider.AndroidViewModelFactory(context.applicationContext as Application)
            )
            NotesScreen(vm) { navController.navigate(Routes.ADD_NOTE) }
        }

        composable(Routes.ADD_NOTE) {
            val vm: NotesViewModel = viewModel()
            AddNoteScreen(vm) { navController.popBackStack() }
        }


        // 🏠 Dashboard screen
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
                degreeId = degreeId,
                navController = navController
            )
        }

            DashboardScreen(
                navController = navController,    // ✅ pass navController
                studentIndex = studentIndex,
                studentName = decodedName
        // 📋 Task Schedule screen
        composable(
            route = Routes.TASK_SCHEDULE,
            arguments = listOf(
                navArgument("degreeId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val degreeId = backStackEntry.arguments?.getLong("degreeId") ?: 0L
            TaskScheduleScreen(degreeId = degreeId)
        }

        // 📊 Results screen
        composable(
            route = Routes.RESULTS,
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

            ResultsScreen(
                indexNumber = studentIndex,
                studentName = decodedName,
                degreeId = degreeId,
                navController = navController
            )
        }
    }
}