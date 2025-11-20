package com.helpetuser.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.helpetuser.ui.components.CardDetalleVeterinaria
import com.helpetuser.ui.components.ContenedorVeterinarias
import com.helpetuser.ui.viewmodel.MapViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    // Estado del permiso de ubicación
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    // Al iniciar, pedimos permiso si no lo tenemos
    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        } else {
            viewModel.getCurrentLocation()
        }
    }

    // Si se otorga el permiso despues de pedirlo, obtenemos ubicación
    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            viewModel.getCurrentLocation()
        }
    }

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (locationPermissionState.status.isGranted) {
                if (uiState.userLocation != null) {
                    // --- MAPA DE GOOGLE ---
                    val userLatLng = LatLng(uiState.userLocation!!.latitude, uiState.userLocation!!.longitude)
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(userLatLng, 14f)
                    }

                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(isMyLocationEnabled = true)
                    ) {
                        // Marcadores para las veterinarias
                        uiState.veterinariasCercanas.forEach { item ->
                            Marker(
                                state = MarkerState(position = LatLng(item.sucursal.latitud, item.sucursal.longitud)),
                                title = item.sucursal.nombre,
                                snippet = "A ${String.format("%.1f", item.distanciaKm)} km",
                                onClick = {
                                    viewModel.selectVeterinaria(item)
                                    false // Retorna false para centrar la cámara y mostrar info window por defecto
                                }
                            )
                        }
                    }

                    // --- COMPONENTES EN LA PARTE BAJA ---
                    // Si hay una seleccionada, mostramos el detalle, si no, la lista
                    BottomSheetContent(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        uiState = uiState,
                        onItemClick = { /* Navegar a detalle o centrar en mapa */ },
                        onDismissSelection = { viewModel.selectVeterinaria(null) }
                    )
                } else {
                    // Cargando ubicación...
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                // UI si no hay permiso
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Se necesita permiso de ubicación para ver el mapa.")
                    Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                        Text("Dar Permiso")
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    uiState: com.helpetuser.ui.viewmodel.MapUiState,
    onItemClick: (Int) -> Unit,
    onDismissSelection: () -> Unit
) {
    // Usamos un Surface para que se vea sobre el mapa
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 350.dp), // Altura limitada
        shape = MaterialTheme.shapes.large,
        tonalElevation = 8.dp
    ) {
        if (uiState.selectedVeterinaria != null) {
            // Vista Detalle (CardDetalleVeterinaria)
            Box {
                CardDetalleVeterinaria(
                    item = uiState.selectedVeterinaria,
                    onAgendarClick = { /* Navegar a Agendar */ }
                )
                // Botón para cerrar detalle
                Button(
                    onClick = onDismissSelection,
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                ) {
                    Text("X")
                }
            }
        } else {
            // Vista Lista (ContenedorVeterinarias)
            Column {
                Text(
                    "Veterinarias Cercanas (${uiState.veterinariasCercanas.size})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                ContenedorVeterinarias(
                    listaVeterinarias = uiState.veterinariasCercanas,
                    onItemClick = onItemClick
                )
            }
        }
    }
}