package com.helpetuser.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.helpetuser.data.manager.SessionManager
import com.helpetuser.ui.screens.*
import com.helpetuser.ui.viewmodel.*

// Clase para los ítems del menú inferior
sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Add : BottomNavItem(Routes.SUCURSAL_SCREEN, "Veterinarias", Icons.Default.Store)
    object Home : BottomNavItem(Routes.HOME_SCREEN, "Mis Mascotas", Icons.Default.Pets)
    object Profile : BottomNavItem(Routes.PROFILE_SCREEN, "Perfil", Icons.Default.Person)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // 1. Session Manager y Estado del Usuario
    val sessionManager = remember { SessionManager(context) }
    val currentUserId by sessionManager.userIdFlow.collectAsState()

    // 2. Base de Datos y DAOs
    val database = HelpetDatabase.getDatabase(context)
    val mascotaDao = database.mascotaDao()
    val reservaDao = database.reservaDao()
    val usuarioDao = database.usuarioDao()
    val veterinariaDao = database.veterinariaDao()
    val promocionUsuarioDao = database.promocionUsuarioDao()
    val servicioDao = database.servicioDao()
    val sucursalDao = database.sucursalDao()

    // Rutas de la BottomBar
    val bottomBarRoutes = listOf(Routes.HOME_SCREEN, Routes.SUCURSAL_SCREEN, Routes.PROFILE_SCREEN)

    // 3. Lógica de Redirección
    LaunchedEffect(currentUserId) {
        val isAuthRoute = navController.currentBackStackEntry?.destination?.route in listOf(
            Routes.WELCOME_SCREEN, Routes.LOGIN_SCREEN, Routes.REGISTER_SCREEN
        )
        // Si se cierra sesión y no estamos en una pantalla de auth, volver al inicio
        if (currentUserId == null && !isAuthRoute) {
            navController.navigate(Routes.WELCOME_SCREEN) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            // Solo mostrar barra si hay usuario logueado y es una ruta principal
            if (currentUserId != null && currentDestination?.route in bottomBarRoutes) {
                NavigationBar {
                    val items = listOf(BottomNavItem.Add, BottomNavItem.Home, BottomNavItem.Profile)
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
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

        // Decidir ruta inicial
        val startDestination = if (currentUserId != null) Routes.HOME_SCREEN else Routes.WELCOME_SCREEN

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // --- AUTENTICACIÓN ---
            composable(Routes.WELCOME_SCREEN) { WelcomeScreen(navController) }

            composable(Routes.LOGIN_SCREEN) {
                val factory = AuthViewModelFactory(usuarioDao, sessionManager)
                val vm: AuthViewModel = viewModel(factory = factory)
                LoginScreen(navController, vm)
            }

            composable(Routes.REGISTER_SCREEN) {
                val factory = AuthViewModelFactory(usuarioDao, sessionManager)
                val vm: AuthViewModel = viewModel(factory = factory)
                // CORRECCIÓN: Se llama a RegistrarScreen, no RegisterScreen
                RegistrarScreen(navController, vm)
            }

            // --- PANTALLAS PRINCIPALES (Protegidas con ID) ---

            composable(Routes.HOME_SCREEN) {
                if (currentUserId != null) {
                    val factory = HomeViewModelFactory(mascotaDao, reservaDao, currentUserId!!)
                    val vm: HomeViewModel = viewModel(factory = factory)
                    HomeScreen(
                        homeViewModel = vm,
                        onMascotaClick = { id -> navController.navigate(Routes.createMascotaDetailRoute(id)) },
                        onAddPetClick = { navController.navigate(Routes.AGREGAR_MASCOTA_SCREEN) }
                    )
                }
            }

            composable(Routes.PROFILE_SCREEN) {
                if (currentUserId != null) {
                    val factory = ProfileViewModelFactory(usuarioDao, veterinariaDao, promocionUsuarioDao, currentUserId!!)
                    val vm: ProfileViewModel = viewModel(factory = factory)
                    ProfileScreen(
                        profileViewModel = vm,
                        navController = navController,
                        onLogoutClick = { sessionManager.logout() }
                    )
                }
            }

            composable(Routes.SUCURSAL_SCREEN) {
                val factory = SucursalViewModelFactory(veterinariaDao, sucursalDao)
                val vm: SucursalViewModel = viewModel(factory = factory)
                SucursalScreen(navController, vm)
            }

            // --- PANTALLAS SECUNDARIAS ---

            composable(Routes.AGREGAR_MASCOTA_SCREEN) {
                if (currentUserId != null) {
                    val factory = AgregarMascotaViewModelFactory(mascotaDao, currentUserId!!)
                    val vm: AgregarMascotaViewModel = viewModel(factory = factory)
                    AgregarMascotaScreen(navController, vm)
                }
            }

            composable(Routes.GESTIONAR_MASCOTAS) {
                if (currentUserId != null) {
                    val factory = GestionarMascotasViewModelFactory(mascotaDao, currentUserId!!)
                    val vm: GestionarMascotasViewModel = viewModel(factory = factory)
                    GestionarMascotasScreen(navController, vm)
                }
            }

            composable(Routes.HISTORIAL) {
                if (currentUserId != null) {
                    val factory = HistorialViewModelFactory(reservaDao, currentUserId!!)
                    val vm: HistorialViewModel = viewModel(factory = factory)
                    HistorialScreen(navController, vm)
                }
            }

            composable(
                route = Routes.AGENDAR_CITA_SCREEN,
                arguments = listOf(navArgument("sucursalId") { type = NavType.IntType })
            ) { backStackEntry ->
                val sucursalId = backStackEntry.arguments?.getInt("sucursalId")
                if (sucursalId != null && currentUserId != null) {
                    val factory = AgendarCitaViewModelFactory(
                        reservaDao, mascotaDao, servicioDao, sucursalDao, veterinariaDao, sucursalId, currentUserId!!
                    )
                    val vm: AgendarCitaViewModel = viewModel(factory = factory)
                    AgendarCitaScreen(navController, vm, sucursalId)
                }
            }

            composable(Routes.QUIENES_SOMOS) { QuienesSomosScreen(navController) }

            composable(
                route = Routes.MASCOTA_DETAIL_SCREEN,
                arguments = listOf(navArgument("mascotaId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mascotaIdString = backStackEntry.arguments?.getString("mascotaId")
                val mascotaIdInt = mascotaIdString?.toIntOrNull()

                if(mascotaIdInt != null) {
                    val factory = MascotaDetailViewModelFactory(mascotaDao, reservaDao, mascotaIdInt)
                    val vm: MascotaDetailViewModel = viewModel(factory = factory)
                    MascotaDetailScreen(navController, vm)
                }
            }

            composable(Routes.PROMOTIONS_SCREEN){
                if (currentUserId != null) {
                    val factory = ProfileViewModelFactory(usuarioDao, veterinariaDao, promocionUsuarioDao, currentUserId!!)
                    val vm: ProfileViewModel = viewModel(factory = factory)
                    PromocionesScreen(navController, vm)
                }
            }
        }
    }
}