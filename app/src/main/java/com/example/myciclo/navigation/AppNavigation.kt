package com.example.myciclo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myciclo.ui.views.LoginScreen
import com.example.myciclo.ui.views.RegistroScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginExitoso = {
                    navController.navigate("notas") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate("registro") }
            )
        }
        composable("registro") {
            RegistroScreen(
                onVolver = { navController.popBackStack() }
            )
        }
    }
}
