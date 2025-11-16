package com.helpetuser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helpetuser.ui.navigation.Routes

@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(32.dp), // Padding extra para centrar contenido
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Aquí iría el logo de Helpet
            Text(
                text = "Bienvenido a Helpet",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "La mejor forma de gestionar la salud de tu mascota.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { navController.navigate(Routes.LOGIN_SCREEN) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { navController.navigate(Routes.REGISTER_SCREEN) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Usuario")
            }
        }
    }
}