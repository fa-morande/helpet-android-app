package com.helpetuser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helpetuser.model.Mascota
import com.helpetuser.model.Servicio
import com.helpetuser.ui.components.TopAppBar
import com.helpetuser.ui.viewmodel.AgendarCitaViewModel

@Composable
fun AgendarCitaScreen(
    navController: NavController,
    viewModel: AgendarCitaViewModel,
    sucursalId: Int
) {
    val uiState by viewModel.uiState.collectAsState()
    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
    var servicioSeleccionado by remember { mutableStateOf<Servicio?>(null) }
    var diaTexto by remember { mutableStateOf("AAAA-MM-DD") }

    LaunchedEffect(uiState.mascotasUsuario) {
        if (mascotaSeleccionada == null) mascotaSeleccionada = uiState.mascotasUsuario.firstOrNull()
    }
    LaunchedEffect(uiState.serviciosDisponibles) {
        if (servicioSeleccionado == null) servicioSeleccionado = uiState.serviciosDisponibles.firstOrNull()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = "Agendar Cita",
                onBackClicked = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                uiState.veterinaria?.let { vet ->
                    Text("Veterinaria: ${vet.nombre}", fontWeight = FontWeight.Bold)
                }
                uiState.sucursal?.let { suc ->
                    Text("Sucursal: ${suc.nombre}")
                    Text("Dirección: ${suc.direccion}")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }

                FormDropdown(
                    label = "Mascota:",
                    value = mascotaSeleccionada?.nombre ?: "Seleccione...",
                    //opdiones de nombres de las mascotas
                    options = uiState.mascotasUsuario.map { it.nombre },
                    onValueChange = { nombreSeleccionado ->
                        //busca la mascota completa por el nombre
                        mascotaSeleccionada = uiState.mascotasUsuario.find { it.nombre == nombreSeleccionado }
                    }
                )

                FormDropdown(
                    label = "Servicio:",
                    value = servicioSeleccionado?.nombre ?: "Seleccione...",
                    options = uiState.serviciosDisponibles.map { it.nombre },
                    onValueChange = { nombreSeleccionado ->
                        servicioSeleccionado = uiState.serviciosDisponibles.find { it.nombre == nombreSeleccionado }
                    }
                )

                OutlinedTextField(
                    value = diaTexto,
                    onValueChange = { diaTexto = it },
                    label = { Text("Día de la Cita (AAAA-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.guardarReserva(
                            mascotaSeleccionada = mascotaSeleccionada,
                            servicioSeleccionado = servicioSeleccionado,
                            diaSeleccionado = diaTexto
                        )
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = mascotaSeleccionada != null && servicioSeleccionado != null && diaTexto.length >= 8
                ) {
                    Text("Confirmar Reserva")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormDropdown(
    label: String,
    value: String,
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(0.4f) // Ajusta peso si es necesario
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.weight(0.6f)
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth() // Ocupa ancho
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}