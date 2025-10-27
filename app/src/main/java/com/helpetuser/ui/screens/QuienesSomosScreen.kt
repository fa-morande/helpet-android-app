package com.helpetuser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helpetuser.ui.components.TopAppBar

@Composable
fun QuienesSomosScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Quiénes Somos",
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())//permite scroll
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Somos Helpet",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Una aplicación creada por estudiantes apasionados por las mascotas y la tecnología. Nuestro objetivo es facilitar la conexión entre dueños de mascotas y servicios veterinarios de calidad.",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "¡Gracias por usar Helpet!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
