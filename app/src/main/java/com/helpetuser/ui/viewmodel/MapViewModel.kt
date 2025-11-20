package com.helpetuser.ui.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.helpetuser.data.local.dao.SucursalDao
import com.helpetuser.data.local.dao.VeterinariaDao
import com.helpetuser.ui.components.VeterinariaConDistancia
import com.helpetuser.utils.LocationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class MapUiState(
    val userLocation: Location? = null,
    val veterinariasCercanas: List<VeterinariaConDistancia> = emptyList(),
    val selectedVeterinaria: VeterinariaConDistancia? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class MapViewModel(
    private val sucursalDao: SucursalDao,
    private val veterinariaDao: VeterinariaDao,
    private val context: Context // Cuidado: Usar Application Context para evitar leaks
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    // Cliente de ubicación de Google
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // Function 1: Activar GPS y obtener ubicación (Llamada desde la UI tras verificar permisos)
    @SuppressLint("MissingPermission") // El permiso se chequea en la UI
    fun getCurrentLocation() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        val priority = Priority.PRIORITY_HIGH_ACCURACY
        val cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(priority, cancellationTokenSource.token)
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    _uiState.value = _uiState.value.copy(userLocation = location)
                    // Una vez tenemos ubicación, ejecutamos Function 3 (Filtrar BD)
                    loadVeterinariasCercanas(location)
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = "No se pudo obtener ubicación")
                }
            }
            .addOnFailureListener {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
            }
    }

    // Function 3: Llamar BD y filtrar con Function 2 (Utils)
    private fun loadVeterinariasCercanas(userLocation: Location) {
        viewModelScope.launch {
            val sucursales = sucursalDao.getAll().first() // Obtener lista una vez
            val todasLasVets = veterinariaDao.getAll().first()

            val listaProcesada = sucursales.mapNotNull { sucursal ->
                val vet = todasLasVets.find { it.id == sucursal.veterinariaId }
                if (vet != null) {
                    // Function 2: Calcular distancia
                    val dist = LocationUtils.calcularDistanciaKm(
                        userLocation.latitude, userLocation.longitude,
                        sucursal.latitud, sucursal.longitud
                    )
                    VeterinariaConDistancia(vet, sucursal, dist)
                } else null
            }
                // Filtrar por rango (ej: 10km) y ordenar
                .filter { it.distanciaKm <= 10.0 }
                .sortedBy { it.distanciaKm }

            _uiState.value = _uiState.value.copy(
                veterinariasCercanas = listaProcesada,
                isLoading = false
            )
        }
    }

    fun selectVeterinaria(vet: VeterinariaConDistancia?) {
        _uiState.value = _uiState.value.copy(selectedVeterinaria = vet)
    }
}