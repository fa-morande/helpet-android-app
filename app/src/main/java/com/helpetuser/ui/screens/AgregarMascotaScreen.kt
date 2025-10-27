package com.helpetuser.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helpetuser.ui.components.TopAppBar
import com.helpetuser.ui.viewmodel.AgregarMascotaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarMascotaScreen(
    navController: NavController,
    viewModel: AgregarMascotaViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var vacunas by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Formulario de Registro",
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FormRow(
                label = "Nombre:",
                value = nombre,
                onValueChange = { nombre = it }
            )

            FormRow(
                label = "Fecha Nacimiento:",
                value = fechaNacimiento,
                onValueChange = { fechaNacimiento = it },
                placeholder = "AAAA-MM-DD"
            )

            FormDropdown(
                label = "Especie:",
                value = especie,
                options = listOf("Perro", "Gato", "Otro"),
                onValueChange = { especie = it }
            )

            FormRow(
                label = "Raza:",
                value = raza,
                onValueChange = { raza = it }
            )

            FormDropdown(
                label = "Vacunas:",
                value = vacunas,
                options = listOf("Sí", "No", "Al día"),
                onValueChange = { vacunas = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.agregarMascota(
                        nombre = nombre,
                        fechaNacimiento = fechaNacimiento,
                        especie = especie,
                        raza = raza
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nombre.isNotBlank() && especie.isNotBlank()
            ) {
                Text("Registrar Mascota")
            }
        }
    }
}

// --------------------------------------------------------------------------
// componente privado
// --------------------------------------------------------------------------

@Composable
private fun FormRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(0.4f),
            style = MaterialTheme.typography.bodyLarge
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            singleLine = true,
            modifier = Modifier.weight(0.6f),
            textStyle = MaterialTheme.typography.bodyLarge
        )
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(0.4f),
            style = MaterialTheme.typography.bodyLarge
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.weight(0.6f)
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},//no editable
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()//necesario para conectar
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge,
                placeholder = { Text("Seleccionar") }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
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