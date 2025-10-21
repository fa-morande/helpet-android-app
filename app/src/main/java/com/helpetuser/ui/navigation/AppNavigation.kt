package com.helpetuser.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.helpetuser.ui.navigation.Routes
import com.helpetuser.ui.screens.HomeScreen
import com.helpetuser.ui.screens.ProfileScreen
import com.helpetuser.ui.viewmodel.HomeViewModel

//items barra navegacion
sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem(Routes.HOME_SCREEN, "Mis Mascotas", Icons.Default.Home)
    object Add : BottomNavItem(Routes.ADD_SCREEN, "Añadir", Icons.Default.Add)
    object Profile : BottomNavItem(Routes.PROFILE_SCREEN, "Perfil", Icons.Default.Person)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navItems = listOf(BottomNavItem.Home, BottomNavItem.Add, BottomNavItem.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // estructura de navegacion
        NavHost(
            navController = navController,
            startDestination = Routes.HOME_SCREEN,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME_SCREEN) {
                val homeViewModel: HomeViewModel = viewModel()
                HomeScreen(homeViewModel = homeViewModel, onMascotaClick = { mascotaId ->
                    navController.navigate(Routes.createMascotaDetailRoute(mascotaId))
                })
            }
            composable(Routes.ADD_SCREEN) {
                Text("Pantalla de Añadir Mascota")
            }
            composable(Routes.PROFILE_SCREEN) {
                ProfileScreen()
            }
            composable(Routes.MASCOTA_DETAIL_SCREEN) { backStackEntry ->
                val mascotaId = backStackEntry.arguments?.getString("mascotaId")
                Text("Detalle de la mascota con ID: $mascotaId")
            }
        }
    }
}
