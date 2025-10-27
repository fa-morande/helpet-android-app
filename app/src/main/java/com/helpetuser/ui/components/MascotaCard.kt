package com.helpetuser.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        shape = MaterialTheme.shapes.medium,//borde
        //sombra
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        //color de fondo
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pet_placeholder),//no se te olvide el placeholder
                    contentDescription = "Foto de ${mascota.nombre}",
                    modifier = Modifier
                        .size(60.dp)//tama;o foto user
                        .clip(CircleShape)//marco redondo para la foto
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = mascota.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold//negrita
                    )
                    Text(
                        text = "${mascota.especie} - ${mascota.raza}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            //proxima cita
            proximaReserva?.let { reserva ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Próxima Cita",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Próxima cita: ${reserva.fechaHora}",//string directo
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

//preview privado
@Preview(showBackground = true)
@Composable
private fun MascotaCardPreview() {
    val mascotaEjemplo = remember {
        Mascota(
            id = 1, usuarioId = 1, nombre = "Kiara", especie = "Perro",
            raza = "Mestiza", sexo = "Hembra", fechaNacimiento = "2018-05-10",
            activo = true
        )
    }
    val reservaEjemplo = remember {
        Reserva(
            id = 1, usuarioId = 1, mascotaId = 1, sucursalId = 1, servicioId = 1,
            profesionalId = 1, fechaHora = "30 de Oct, 10:00 hrs.", estado = "CONFIRMADA",
            comentarios = "Preview"
        )
    }
    HelpetUserTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            MascotaCard(mascota = mascotaEjemplo, proximaReserva = reservaEjemplo, onClick = {})
            Spacer(modifier = Modifier.height(16.dp))
            MascotaCard(mascota = mascotaEjemplo, proximaReserva = null, onClick = {})
        }
    }
}