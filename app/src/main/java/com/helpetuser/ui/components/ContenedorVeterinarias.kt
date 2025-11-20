package com.helpetuser.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ContenedorVeterinarias(
    listaVeterinarias: List<VeterinariaConDistancia>,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        // Dejamos espacio abajo para que no choque con la navegación o elementos flotantes
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        items(listaVeterinarias) { item ->
            CardVeterinaria(
                item = item,
                onClick = { onItemClick(item.sucursal.id) }
            )
        }
    }
}