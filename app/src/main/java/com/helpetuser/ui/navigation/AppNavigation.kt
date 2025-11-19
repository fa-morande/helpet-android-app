package com.helpetuser.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

        val startDestination = if (currentUserId != null) Routes.HOME_SCREEN else Routes.WELCOME_SCREEN

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.WELCOME_SCREEN) {
                WelcomeScreen(navController = navController)
            }

            composable(Routes.LOGIN_SCREEN) {
                // CORREGIDO: Se pasa sessionManager
                val factory = AuthViewModelFactory(usuarioDao, sessionManager)
                val authViewModel: AuthViewModel = viewModel(factory = factory)
                LoginScreen(
                    navController = navController,
                    viewModel = authViewModel
                )
            }

            composable(Routes.REGISTER_SCREEN) {
                // CORREGIDO: Se pasa sessionManager
                val factory = AuthViewModelFactory(usuarioDao, sessionManager)
                val authViewModel: AuthViewModel = viewModel(factory = factory)
                RegistrarScreen(
                    navController = navController,
                    viewModel = authViewModel
                )
            }

            composable(Routes.HOME_SCREEN) {
                if (currentUserId != null) {
                    // CORREGIDO: Se pasa currentUserId
                    val factory = HomeViewModelFactory(mascotaDao, reservaDao, currentUserId!!)
                    val homeViewModel: HomeViewModel = viewModel(factory = factory)
                    HomeScreen(
                        homeViewModel = homeViewModel,
                        onMascotaClick = { id -> navController.navigate(Routes.createMascotaDetailRoute(id)) },
                        onAddPetClick = { navController.navigate(Routes.AGREGAR_MASCOTA_SCREEN) }
                    )
                }
            }

            composable(Routes.SUCURSAL_SCREEN) {
                val factory = SucursalViewModelFactory(veterinariaDao, sucursalDao)
                val sucursalViewModel: SucursalViewModel = viewModel(factory = factory)
                SucursalScreen(navController, sucursalViewModel)
            }

            composable(Routes.PROFILE_SCREEN) {
                if (currentUserId != null) {
                    // CORREGIDO: Se pasa currentUserId
                    val factory = ProfileViewModelFactory(usuarioDao, veterinariaDao, promocionUsuarioDao, currentUserId!!)
                    val profileViewModel: ProfileViewModel = viewModel(factory = factory)
                    ProfileScreen(
                        profileViewModel = profileViewModel,
                        navController = navController,
                        onLogoutClick = { sessionManager.logout() }
                    )
                }
            }

            composable(
                route = Routes.MASCOTA_DETAIL_SCREEN,
                arguments = listOf(navArgument("mascotaId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mascotaId = backStackEntry.arguments?.getString("mascotaId")?.toIntOrNull()
                if (mascotaId != null) {
                    val factory = MascotaDetailViewModelFactory(mascotaDao, reservaDao, mascotaId)
                    val viewModel: MascotaDetailViewModel = viewModel(factory = factory)
                    MascotaDetailScreen(navController, viewModel)
                }
            }

            composable(
                route = Routes.AGENDAR_CITA_SCREEN,
                arguments = listOf(navArgument("sucursalId") { type = NavType.IntType })
            ) { backStackEntry ->
                val sucursalId = backStackEntry.arguments?.getInt("sucursalId")
                if (sucursalId != null && currentUserId != null) {
                    // CORREGIDO: Se pasa currentUserId
                    val factory = AgendarCitaViewModelFactory(
                        reservaDao, mascotaDao, servicioDao, sucursalDao, veterinariaDao, sucursalId, currentUserId!!
                    )
                    val viewModel: AgendarCitaViewModel = viewModel(factory = factory)
                    // CORREGIDO: Se eliminó el argumento duplicado de navController
                    AgendarCitaScreen(
                        navController = navController,
                        viewModel = viewModel,
                        sucursalId = sucursalId
                    )
                }
            }

            composable(Routes.PROMOTIONS_SCREEN){
                if (currentUserId != null) {
                    // CORREGIDO: Se pasa currentUserId
                    val factory = ProfileViewModelFactory(usuarioDao, veterinariaDao, promocionUsuarioDao, currentUserId!!)
                    val profileViewModel: ProfileViewModel = viewModel(factory = factory)
                    PromocionesScreen(navController, profileViewModel)
                }
            }

            composable(Routes.QUIENES_SOMOS){
                QuienesSomosScreen(navController = navController)
            }

            composable(Routes.GESTIONAR_MASCOTAS){
                if (currentUserId != null) {
                    // CORREGIDO: Se pasa currentUserId
                    val factory = GestionarMascotasViewModelFactory(mascotaDao, currentUserId!!)
                    val viewModel: GestionarMascotasViewModel = viewModel(factory = factory)
                    GestionarMascotasScreen(navController, viewModel)
                }
            }

            composable(Routes.AGREGAR_MASCOTA_SCREEN){
                if (currentUserId != null) {
                    // CORREGIDO: Se pasa currentUserId
                    val factory = AgregarMascotaViewModelFactory(mascotaDao, currentUserId!!)
                    val viewModel: AgregarMascotaViewModel = viewModel(factory = factory)
                    AgregarMascotaScreen(navController, viewModel)
                }
            }

            composable(Routes.HISTORIAL){
                if (currentUserId != null) {
                    // CORREGIDO: Se pasa currentUserId
                    val factory = HistorialViewModelFactory(reservaDao, currentUserId!!)
                    val viewModel: HistorialViewModel = viewModel(factory = factory)
                    HistorialScreen(navController, viewModel)
                }
            }
        }
    }
}