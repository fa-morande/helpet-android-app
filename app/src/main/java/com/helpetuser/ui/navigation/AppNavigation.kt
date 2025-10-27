package com.helpetuser.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.helpetuser.data.local.HelpetDatabase
import com.helpetuser.ui.screens.*
import com.helpetuser.ui.viewmodel.*

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Add : BottomNavItem(Routes.SUCURSAL_SCREEN, "Veterinarias", Icons.Default.Store)
    object Home : BottomNavItem(Routes.HOME_SCREEN, "Mis Mascotas", Icons.Default.Pets)
    object Profile : BottomNavItem(Routes.PROFILE_SCREEN, "Perfil", Icons.Default.Person)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navItems = listOf(
        BottomNavItem.Add,
        BottomNavItem.Home,
        BottomNavItem.Profile)

    val context = LocalContext.current
    val database = HelpetDatabase.getDatabase(context)
    val mascotaDao = database.mascotaDao()
    val reservaDao = database.reservaDao()
    val usuarioDao = database.usuarioDao()
    val veterinariaDao = database.veterinariaDao()
    val promocionUsuarioDao = database.promocionUsuarioDao()
    val servicioDao = database.servicioDao()
    val sucursalDao = database.sucursalDao()

    val bottomBarRoutes = listOf(
        Routes.HOME_SCREEN,
        Routes.SUCURSAL_SCREEN,
        Routes.PROFILE_SCREEN
    )

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val showBottomBar = currentDestination?.route in bottomBarRoutes

            if (showBottomBar) {
                NavigationBar {
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
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME_SCREEN,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Routes.HOME_SCREEN) {
                val factory = HomeViewModelFactory(mascotaDao, reservaDao)
                val homeViewModel: HomeViewModel = viewModel(factory = factory)

                HomeScreen(
                    homeViewModel = homeViewModel,
                    onMascotaClick = { mascotaId ->
                        navController.navigate(Routes.createMascotaDetailRoute(mascotaId))
                    },
                    onAddPetClick = {
                        navController.navigate(Routes.AGREGAR_MASCOTA_SCREEN)
                    }
                )
            }

            composable(Routes.SUCURSAL_SCREEN) {
                val factory = SucursalViewModelFactory(veterinariaDao, sucursalDao)
                val sucursalViewModel: SucursalViewModel = viewModel(factory = factory)

                SucursalScreen(
                    navController = navController,
                    viewModel = sucursalViewModel
                )
            }

            composable(Routes.PROFILE_SCREEN) {
                val factory = ProfileViewModelFactory(usuarioDao, veterinariaDao, promocionUsuarioDao)
                val profileViewModel: ProfileViewModel = viewModel(factory = factory)

                ProfileScreen(
                    profileViewModel = profileViewModel,
                    navController = navController
                )
            }

            composable(
                route = Routes.MASCOTA_DETAIL_SCREEN,
                arguments = listOf(navArgument("mascotaId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mascotaIdString = backStackEntry.arguments?.getString("mascotaId")
                val mascotaIdInt = mascotaIdString?.toIntOrNull()
                if (mascotaIdInt != null) {
                    val factory = MascotaDetailViewModelFactory(
                        mascotaDao,
                        reservaDao,
                        mascotaIdInt
                    )
                    val viewModel: MascotaDetailViewModel = viewModel(factory = factory)
                    MascotaDetailScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                } else {
                    Text("Error: ID de mascota inválido")
                }
            }

            composable(
                route = Routes.AGENDAR_CITA_SCREEN,
                arguments = listOf(navArgument("sucursalId") { type = NavType.IntType })
            ) { backStackEntry ->
                val sucursalId = backStackEntry.arguments?.getInt("sucursalId")
                if (sucursalId != null) {
                    val factory = AgendarCitaViewModelFactory(
                        reservaDao,
                        mascotaDao,
                        servicioDao,
                        sucursalDao,
                        veterinariaDao,
                        sucursalId
                    )
                    val viewModel: AgendarCitaViewModel = viewModel(factory = factory)
                    AgendarCitaScreen(
                        navController = navController,
                        viewModel = viewModel,
                        sucursalId = sucursalId
                    )
                } else {
                    Text("Error: ID de sucursal inválido")
                }
            }

            composable(Routes.PROMOTIONS_SCREEN){
                val factory = ProfileViewModelFactory(usuarioDao, veterinariaDao, promocionUsuarioDao)
                val profileViewModel: ProfileViewModel = viewModel(factory = factory)
                PromocionesScreen(
                    navController = navController,
                    profileViewModel = profileViewModel
                )
            }

            composable(Routes.QUIENES_SOMOS){
                QuienesSomosScreen(navController = navController)
            }
            composable(Routes.GESTIONAR_MASCOTAS){
                val factory = GestionarMascotasViewModelFactory(mascotaDao)
                val viewModel: GestionarMascotasViewModel = viewModel(factory = factory)
                GestionarMascotasScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(Routes.AGREGAR_MASCOTA_SCREEN){
                val factory = AgregarMascotaViewModelFactory(mascotaDao)
                val viewModel: AgregarMascotaViewModel = viewModel(factory = factory)
                AgregarMascotaScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }

            composable(Routes.HISTORIAL){
                val factory = HistorialViewModelFactory(reservaDao)
                val viewModel: HistorialViewModel = viewModel(factory = factory)
                HistorialScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}