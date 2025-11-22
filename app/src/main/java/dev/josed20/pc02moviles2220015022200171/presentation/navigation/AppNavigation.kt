package dev.josed20.pc02moviles2220015022200171.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.josed20.pc02moviles2220015022200171.presentation.auth.LoginScreen
import dev.josed20.pc02moviles2220015022200171.presentation.home.ListadoScreen
import dev.josed20.pc02moviles2220015022200171.presentation.home.RegistroScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Listado : Screen("listado")
    object Registro : Screen("registro")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {

        // LOGIN
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Listado.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // LISTADO (Tu pantalla)
        composable(Screen.Listado.route) {
            ListadoScreen(
                onNavigateToRegistro = {
                    navController.navigate(Screen.Registro.route)
                }
            )
        }

        // REGISTRO (Pantalla de Lucero)
        composable(Screen.Registro.route) {
            // Por ahora ponemos un placeholder hasta que Lucero haga el merge,
            // O si ya tienes el archivo RegistroScreen creado (aunque esté vacío), llámalo.
            RegistroScreen(
                onSaveSuccess = {
                    navController.popBackStack() // Volver al listado al guardar
                }
            )
        }
    }
}