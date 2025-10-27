package com.helpetuser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.helpetuser.ui.components.MascotaCard
import com.helpetuser.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onMascotaClick: (String) -> Unit,
    onAddPetClick: () -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Mascotas") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddPetClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir Mascota"
                )
            }
        }
    ) { paddingValues ->
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
                uiState.error != null -> {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Text(
                                text = "Bienvenido a Helpet",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }

                        items(uiState.mascotas) { mascotaConReserva ->
                            MascotaCard(
                                mascota = mascotaConReserva.mascota,
                                proximaReserva = mascotaConReserva.proximaReserva,
                                onClick = { onMascotaClick(mascotaConReserva.mascota.id.toString()) }
                            )
                        }
                    }
                }
            }
        }
    }
}