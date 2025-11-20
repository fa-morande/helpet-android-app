package com.helpetuser.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.helpetuser.R
import com.helpetuser.model.Mascota
import com.helpetuser.model.Sucursal
import com.helpetuser.model.Veterinaria

// Wrapper para pasar datos combinados a la UI
data class VeterinariaConDistancia(
    val veterinaria: Veterinaria,
    val sucursal: Sucursal,
    val distanciaKm: Double
)

// ------------------------------------------------------------
// CARD VETERINARIA (Para listas y mapas)
// Titulo, distancia, logo, nivel de estrellas
// ------------------------------------------------------------
@Composable
fun CardVeterinaria(
    item: VeterinariaConDistancia,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.veterinaria.logoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_placeholder_vet),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.sucursal.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                // Estrellas (Simulación estática por ahora, puedes hacerlo dinámico)
                StarRatingBar(rating = 4)

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${String.format("%.1f", item.distanciaKm)} km",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

// ------------------------------------------------------------
// CARD DETALLE VETERINARIA (Vista completa)
// ------------------------------------------------------------
@Composable
fun CardDetalleVeterinaria(
    item: VeterinariaConDistancia,
    onAgendarClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.sucursal.nombre,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            StarRatingBar(rating = 4, modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = item.sucursal.direccion + ", " + item.sucursal.comuna,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "A ${String.format("%.1f", item.distanciaKm)} km de ti",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Descripción:",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Clínica veterinaria especializada con atención de urgencias y especialistas certificados.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAgendarClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agendar Hora")
            }
        }
    }
}

// ------------------------------------------------------------
// CONTENEDOR VETERINARIAS (Lista dinámica)
// ------------------------------------------------------------
@Composable
fun ContenedorVeterinarias(
    listaVeterinarias: List<VeterinariaConDistancia>,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp) // Espacio para mapa o bottom bar
    ) {
        items(listaVeterinarias) { item ->
            CardVeterinaria(
                item = item,
                onClick = { onItemClick(item.sucursal.id) }
            )
        }
    }
}

// ------------------------------------------------------------
// FORMULARIO MASCOTA CONSULTA
// ------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
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

// Componente auxiliar de estrellas
@Composable
fun StarRatingBar(rating: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.StarBorder,
                contentDescription = null,
                tint = Color(0xFFFFC107), // Amarillo Amber
                modifier = Modifier.size(16.dp)
            )
        }
    }
}