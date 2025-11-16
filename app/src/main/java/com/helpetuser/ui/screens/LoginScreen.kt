package com.helpetuser.ui.screens

import androidx.compose.foundation.layout.*
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

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observar el estado de loginSuccess
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            // Navegamos al Home y limpiamos todo el stack de login
            navController.navigate(Routes.HOME_SCREEN) {
                popUpTo(Routes.WELCOME_SCREEN) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Iniciar Sesión",
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Iniciar sesión en Helpet",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

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
                    onClick = { viewModel.login(email, password) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                ) {
                    Text("Entrar")
                }
            }

            // --- PANTALLA DE CARGA ---
            // (Implementación de Screen 5 del Miro)
            if (uiState.isLoading) {
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
                            text = "Danos un segundo...",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}