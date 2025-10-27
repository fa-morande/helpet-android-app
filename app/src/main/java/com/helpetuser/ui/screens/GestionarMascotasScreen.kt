package com.helpetuser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helpetuser.model.Mascota
import com.helpetuser.ui.components.TopAppBar
import com.helpetuser.ui.viewmodel.GestionarMascotasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionarMascotasScreen(
    navController: NavController,
    viewModel: GestionarMascotasViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    var mascotaSeleccionadaParaEliminar by remember { mutableStateOf<Mascota?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Gestionar Mascotas",
                onBackClicked = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
                }
                uiState.error != null -> {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }
                else -> {
                    //seccion lista de las mascotas
                    Text(
                        "Mis Mascotas Registradas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    if (uiState.mascotasUsuario.isEmpty()) {
                        Text(
                            "Aún no tienes mascotas registradas.",
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        //lista
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Nombre", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                                    Text("Especie", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                                    Text("Edad", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                                }
                                Divider()
                            }
                            // Filas
                            items(uiState.mascotasUsuario) { mascota ->
                                MascotaRowItem(mascota = mascota)
                                Divider()
                            }
                        }
                    }


                    //seccion eliminar mascotas en caso de que hayan
                    if (uiState.mascotasUsuario.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "Mandar al cielo a:", // Texto original
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))


                        ExposedDropdownMenuBox(
                            expanded = dropdownExpanded,
                            onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            OutlinedTextField(
                                value = mascotaSeleccionadaParaEliminar?.nombre ?: "Selecciona una mascota...",
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                textStyle = MaterialTheme.typography.bodyLarge
                            )

                            ExposedDropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false }
                            ) {
                                uiState.mascotasUsuario.forEach { mascota ->
                                    DropdownMenuItem(
                                        text = { Text(mascota.nombre, style = MaterialTheme.typography.bodyLarge) },
                                        onClick = {
                                            mascotaSeleccionadaParaEliminar = mascota
                                            dropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                viewModel.eliminarMascota(mascotaSeleccionadaParaEliminar)
                                mascotaSeleccionadaParaEliminar = null
                            },
                            enabled = mascotaSeleccionadaParaEliminar != null,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Confirmar")
                        }
                    }
                }
            }
        }
    }
}

// --------------------------------------------------------------------------
// componente privado
// --------------------------------------------------------------------------
@Composable
private fun MascotaRowItem(mascota: Mascota) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Pets,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(mascota.nombre, style = MaterialTheme.typography.bodyLarge)
        }
        Text(
            mascota.especie,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            "Edad Pendiente",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}