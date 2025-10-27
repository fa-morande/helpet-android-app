package com.helpetuser.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.helpetuser.ui.components.SucursalCard
import com.helpetuser.ui.components.TopAppBar
import com.helpetuser.ui.navigation.Routes
import com.helpetuser.ui.viewmodel.SucursalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SucursalScreen(
    navController: NavController,
    viewModel: SucursalViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedComuna by viewModel.selectedComuna.collectAsState()
    val showComunaDropdownState = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Veterinarias",
                onBackClicked = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            //filtro de comunas uiState.comunasFiltro
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { showComunaDropdownState.value = true },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectedComuna ?: "Todas las comunas",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Desplegar comunas"
                        )
                    }
                }

                DropdownMenu(
                    expanded = showComunaDropdownState.value,
                    onDismissRequest = { showComunaDropdownState.value = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    DropdownMenuItem(
                        text = { Text("Todas las comunas") },
                        onClick = {
                            viewModel.filterByComuna(null)
                            showComunaDropdownState.value = false
                        }
                    )
                    // usa la lista de comunas del UiState
                    uiState.comunasFiltro.forEach { comuna ->
                        DropdownMenuItem(
                            text = { Text(comuna) },
                            onClick = {
                                viewModel.filterByComuna(comuna)
                                showComunaDropdownState.value = false
                            }
                        )
                    }
                }
            }
            //lista sucursales
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.sucursalesMostradas.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                    Text("No se encontraron sucursales para esta comuna.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    //lista pre filtrada con uistate
                    items(uiState.sucursalesMostradas) { sucursalCardModel ->
                        SucursalCard(
                            sucursalModel = sucursalCardModel,
                            onAgendarClick = { sucursalId ->
                                navController.navigate(Routes.createAgendarCitaRoute(sucursalId))
                            }
                        )
                    }
                }
            }
        }
    }
}