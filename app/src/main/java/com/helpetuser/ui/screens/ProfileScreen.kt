package com.helpetuser.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helpetuser.model.Usuario
import com.helpetuser.ui.navigation.Routes
import com.helpetuser.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController,
    onLogoutClick: () -> Unit // <--- Nuevo parámetro recibido desde AppNavigation
) {
    val uiState by profileViewModel.uiState.collectAsState()
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.usuario != null -> {
                    ProfileContent(
                        usuario = uiState.usuario!!,
                        navController = navController,
                        onLogoutClick = onLogoutClick // <--- Se pasa al contenido
                    )
                }
                else -> {
                    Text(text = "No se pudo encontrar el perfil de usuario.")
                }
            }
        }
    }
}

@Composable
private fun ProfileContent(
    usuario: Usuario,
    navController: NavController,
    onLogoutClick: () -> Unit // <--- Se recibe aquí
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ProfileHeader(usuario = usuario)
        HighlightButtons(navController = navController)
        ProfileMenuList(navController = navController)
        AccountActionButtons(onLogoutClick = onLogoutClick) // <--- Se pasa a los botones
    }
}

// --------------------------------------------------------------------------
// componentes privados
// --------------------------------------------------------------------------
@Composable
private fun ProfileHeader(usuario: Usuario) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = usuario.nombre,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Avatar de Usuario",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun HighlightButtons(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HighlightButton(
            text = "Historial",
            icon = Icons.Default.History,
            onClick = { navController.navigate(Routes.HISTORIAL) },
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HighlightButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.height(80.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun ProfileMenuList(navController: NavController) {
    Column {
        ProfileMenuItem(
            text = "Gestionar mascotas",
            icon = Icons.Default.Pets,
            onClick = { navController.navigate(Routes.GESTIONAR_MASCOTAS) }
        )
        ProfileMenuItem(
            text = "Promociones",
            icon = Icons.Default.Discount,
            onClick = { navController.navigate(Routes.PROMOTIONS_SCREEN) }
        )
        ProfileMenuItem(
            text = "Quiénes somos",
            icon = Icons.Default.Info,
            onClick = { navController.navigate(Routes.QUIENES_SOMOS) }
        )
    }
}

@Composable
private fun ProfileMenuItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun AccountActionButtons(
    onLogoutClick: () -> Unit // <--- Recibe la acción
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { /* Lógica de eliminar cuenta futura */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Eliminar mi cuenta")
        }
        OutlinedButton(
            onClick = { onLogoutClick() }, // <--- Ejecuta el cierre de sesión
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}