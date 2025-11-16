package com.helpetuser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helpetuser.ui.components.TopAppBar
import com.helpetuser.ui.navigation.Routes
import com.helpetuser.ui.viewmodel.AuthViewModel
import com.helpetuser.ui.viewmodel.AuthUiState

@Composable
fun RegistrarScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observador para el éxito del registro
    LaunchedEffect(uiState.registerSuccess) {
        if (uiState.registerSuccess) {
            // ¡Éxito! Navegamos al flujo de Onboarding (Agregar Mascota)
            // y limpiamos el stack de autenticación.
            navController.navigate(Routes.AGREGAR_MASCOTA_SCREEN) {
                popUpTo(Routes.WELCOME_SCREEN) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Únete a Helpet",
                onBackClicked = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            ) {
                Text(
                    text = "Crea tu cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        viewModel.clearError()
                    },
                    label = { Text("Nombre Completo") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.error != null,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        viewModel.clearError()
                    },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.error != null,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = telefono,
                    onValueChange = {
                        telefono = it
                        viewModel.clearError()
                    },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.error != null,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        viewModel.clearError()
                    },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = uiState.error != null,
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                uiState.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        viewModel.register(
                            nombre = nombre,
                            email = email,
                            telefono = telefono,
                            password = password
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading && nombre.isNotBlank() && email.isNotBlank() && password.isNotBlank()
                ) {
                    Text("Registrarme")
                }
            }

            // --- PANTALLA DE CARGA ---
            // (Similar a la de Login, Screen 5 del Miro)
            if (uiState.isLoading) {
                LoadingOverlay()
            }
        }
    }
}

@Composable
private fun LoadingOverlay() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Creando tu cuenta...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}