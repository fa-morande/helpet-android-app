package com.helpetuser.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.helpetuser.R
import com.helpetuser.model.Sucursal
import com.helpetuser.ui.theme.HelpetUserTheme
import com.helpetuser.ui.viewmodel.SucursalCardModel

@Composable
fun SucursalCard(
    sucursalModel: SucursalCardModel,
    onAgendarClick: (sucursalId: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(sucursalModel.logoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.ic_placeholder_vet),
                contentDescription = "Logo de ${sucursalModel.sucursal.nombre}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = sucursalModel.sucursal.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = sucursalModel.sucursal.comuna,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { onAgendarClick(sucursalModel.sucursal.id) }) {
                Text("Agendar")
            }
        }
    }
}

//preview privado
@Preview(showBackground = true)
@Composable
private fun SucursalCardPreview() {
    val previewModel = SucursalCardModel(
        sucursal = Sucursal(
            id = 1, veterinariaId = 1, nombre = "VetSalud Centro",
            comuna = "Cerrillos", direccion = "Av. Principal 123",
            telefono = "+569...", latitud = 0.0, longitud = 0.0, horario = "L-V..."
        ),
        logoUrl = "https://picsum.photos/200/200?random=1.webp"
    )
    HelpetUserTheme {
        SucursalCard(
            sucursalModel = previewModel,
            onAgendarClick = {}
        )
    }
}