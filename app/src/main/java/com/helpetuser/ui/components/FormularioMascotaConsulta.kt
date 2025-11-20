package com.helpetuser.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.helpetuser.model.Mascota

@OptIn(ExperimentalMaterial3Api::class) // <--- ESTO SOLUCIONA EL ERROR
@Composable
fun FormularioMascotaConsulta(
    mascotas: List<Mascota>,
    onMascotaSelected: (Mascota) -> Unit,
    onTipoConsultaSelected: (String) -> Unit
) {
    var mascotaExpanded by remember { mutableStateOf(false) }
    var tipoExpanded by remember { mutableStateOf(false) }
    var mascotaText by remember { mutableStateOf("") }
    var tipoText by remember { mutableStateOf("") }

    val tiposConsulta = listOf("Consulta General", "Vacunación", "Urgencia", "Control")

    Column(modifier = Modifier.fillMaxWidth()) {
        // Dropdown Mascotas
        Text("Selecciona tu mascota", style = MaterialTheme.typography.labelMedium)
        ExposedDropdownMenuBox(
            expanded = mascotaExpanded,
            onExpandedChange = { mascotaExpanded = !mascotaExpanded }
        ) {
            OutlinedTextField(
                value = mascotaText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = mascotaExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = mascotaExpanded,
                onDismissRequest = { mascotaExpanded = false }
            ) {
                mascotas.forEach { mascota ->
                    DropdownMenuItem(
                        text = { Text(mascota.nombre) },
                        onClick = {
                            mascotaText = mascota.nombre
                            onMascotaSelected(mascota)
                            mascotaExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown Tipo Consulta
        Text("Tipo de Consulta", style = MaterialTheme.typography.labelMedium)
        ExposedDropdownMenuBox(
            expanded = tipoExpanded,
            onExpandedChange = { tipoExpanded = !tipoExpanded }
        ) {
            OutlinedTextField(
                value = tipoText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tipoExpanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = tipoExpanded,
                onDismissRequest = { tipoExpanded = false }
            ) {
                tiposConsulta.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            tipoText = tipo
                            onTipoConsultaSelected(tipo)
                            tipoExpanded = false
                        }
                    )
                }
            }
        }
    }
}