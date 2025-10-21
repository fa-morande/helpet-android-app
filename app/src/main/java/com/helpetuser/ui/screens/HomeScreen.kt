package com.helpetuser.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.helpetuser.ui.components.MascotaCard
import com.helpetuser.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel, //se agrega el parametro para el click
    onMascotaClick: (String) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.error != null -> {
                    Text(text = uiState.error!!)
                }
                else -> {
                    // LazyColumn para lista en formato columna
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Text(text = "¡Bienvenido a Helpet!")
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        //se crea la card mascota
                        items(uiState.mascotas) { mascotaConReserva ->
                            MascotaCard(
                                mascota = mascotaConReserva.mascota,
                                proximaReserva = mascotaConReserva.proximaReserva,
                                //se le pasa el id mascota cuando hace click
                                onClick = { onMascotaClick(mascotaConReserva.mascota.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
