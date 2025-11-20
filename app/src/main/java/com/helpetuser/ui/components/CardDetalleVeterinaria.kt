package com.helpetuser.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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