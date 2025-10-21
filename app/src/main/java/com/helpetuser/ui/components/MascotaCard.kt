package com.helpetuser.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.helpetuser.R
import com.helpetuser.model.Mascota
import com.helpetuser.model.Reserva
import com.helpetuser.ui.theme.HelpetUserTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun MascotaCard(
    mascota: Mascota,
    proximaReserva: Reserva?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder para la imagen de la mascota
            Image(
                painter = painterResource(id = R.drawable.ic_pet_placeholder),
                contentDescription = "Foto de ${mascota.nombre}",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = mascota.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${mascota.especie} - ${mascota.raza}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // seccion por si existe proxima cita
        proximaReserva?.let { reserva ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Próxima Cita",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        // Usamos el nombre del servicio desde el objeto Reserva
                        text = "Próxima cita el ${reserva.fechaHora.toFormattedString()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

// funcion para formatear la fecha
fun Date.toFormattedString(): String {
    val sdf = SimpleDateFormat("dd 'de' MMMM, HH:mm", Locale("es", "ES"))
    return sdf.format(this) + " hrs."
}


// preview para el desarrollo
@Preview(showBackground = true)
@Composable
fun MascotaCardPreview() {
    val mascotaEjemplo = Mascota(
        id = "1",
        usuarioId = "1",
        nombre = "Kiara",
        especie = "Perro",
        raza = "Mestiza",
        fechaNacimiento = Date(),
        activo = true
    )
    val reservaEjemplo = Reserva(
        id = "1",
        usuarioId = "1",
        mascotaId = "1",
        sucursalId = "1",
        servicioId = "1",
        profesionalId = "1",
        fechaHora = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(5)),
        estado = "CONFIRMADA",
        comentarios = "mas o menos nomas la weaita"
    )

    HelpetUserTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            MascotaCard(mascota = mascotaEjemplo, proximaReserva = reservaEjemplo, onClick = {})
            Spacer(modifier = Modifier.height(16.dp))
            MascotaCard(mascota = mascotaEjemplo, proximaReserva = null, onClick = {})
        }
    }
}
