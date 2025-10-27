package com.helpetuser.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helpetuser.R
import com.helpetuser.ui.components.TopAppBar
import com.helpetuser.ui.navigation.Routes
import com.helpetuser.ui.viewmodel.MascotaDetailViewModel

@Composable
fun MascotaDetailScreen(
    navController: NavController,
    viewModel: MascotaDetailViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val mascota = uiState.mascota
    val ultimaReserva = uiState.ultimaReserva

    Scaffold(
        topBar = {
            TopAppBar(
                title = mascota?.nombre ?: "Detalle de Mascota",
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                mascota != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_pet_placeholder),
                            contentDescription = "Foto de ${mascota.nombre}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )

                        //detalles usando funcion privada
                        DetailInfoRow(label = "Nombre:", value = mascota.nombre)
                        DetailInfoRow(label = "Edad:", value = "Edad pendiente")
                        DetailInfoRow(label = "Raza:", value = mascota.raza)
                        DetailInfoRow(label = "Especie:", value = mascota.especie)
                        DetailInfoRow(label = "Sexo:", value = mascota.sexo)
                        DetailInfoRow(label = "Vacunas:", value = "pendiente")
                        DetailInfoRow(label = "Última Revisión:", value = ultimaReserva?.fechaHora ?: "Ninguna")
                        DetailInfoRow(label = "Última Veterinaria:", value = "pendiente")

                        Spacer(modifier = Modifier.weight(1f))//empuja los botones al fondo

                        //botones de accion
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    val ultimaSucursalId = ultimaReserva?.sucursalId
                                    if (ultimaSucursalId != null) {
                                        navController.navigate(Routes.createAgendarCitaRoute(ultimaSucursalId))
                                    } else {
                                        navController.navigate(Routes.SUCURSAL_SCREEN)
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Volver a agendar")
                            }
                            OutlinedButton(
                                onClick = { navController.navigate(Routes.SUCURSAL_SCREEN) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Nueva veterinaria")
                            }
                        }
                    }
                }
                else -> {
                    Text("No se encontró la mascota.")
                }
            }
        }
    }
}

// --------------------------------------------------------------------------
// componente privado
// --------------------------------------------------------------------------
@Composable
private fun DetailInfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}