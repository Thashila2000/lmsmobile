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
import com.example.lmsmobile.ui.login.LoginScreen
import com.example.lmsmobile.ui.notes.AddNoteScreen
import com.example.lmsmobile.ui.notes.NotesScreen
import com.example.lmsmobile.ui.notes.NotesViewModel
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
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { response ->
                    val safeIndex = response.indexNumber ?: "unknown"
                    val rawName = response.name

                    // Handle null or "NULL" names from backend
                    val safeName = if (rawName.isNullOrBlank() || rawName == "NULL") {
                        "Student"
                    } else {
                        rawName
                    }

                    val encodedName = URLEncoder.encode(safeName.trim(), StandardCharsets.UTF_8.name())
                    navController.navigate(Routes.dashboardRoute(safeIndex, safeName)) //  uses the function
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


        composable(
            route = Routes.DASHBOARD,
            arguments = listOf(
                navArgument("studentIndex") { type = NavType.StringType },
                navArgument("studentName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val studentIndex = backStackEntry.arguments?.getString("studentIndex") ?: "unknown"
            val encodedName = backStackEntry.arguments?.getString("studentName") ?: "Student"
            val decodedName = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.name())

            DashboardScreen(
                navController = navController,    // ✅ pass navController
                studentIndex = studentIndex,
                studentName = decodedName
            )
        }
    }
}