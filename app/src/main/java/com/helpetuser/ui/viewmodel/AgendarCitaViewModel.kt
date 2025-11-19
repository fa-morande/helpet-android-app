package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.*
import com.helpetuser.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AgendarCitaUiState(
    val sucursal: Sucursal? = null,
    val veterinaria: Veterinaria? = null,
    val mascotasUsuario: List<Mascota> = emptyList(),
    val serviciosDisponibles: List<Servicio> = emptyList(),
    val isLoading: Boolean = true
)

class AgendarCitaViewModel(
    private val reservaDao: ReservaDao,
    private val mascotaDao: MascotaDao,
    private val servicioDao: ServicioDao,
    private val sucursalDao: SucursalDao,
    private val veterinariaDao: VeterinariaDao,
    private val sucursalId: Int,
    private val usuarioId: Int
) : ViewModel() {

    private val _uiStateFlow = combine(
        sucursalDao.getById(sucursalId),
        mascotaDao.getByUsuarioId(usuarioId),
        servicioDao.getAll()
    ) { sucursal, mascotas, servicios ->
        val veterinaria = sucursal?.let { veterinariaDao.getById(it.veterinariaId).firstOrNull() }
        AgendarCitaUiState(
            sucursal = sucursal,
            veterinaria = veterinaria,
            mascotasUsuario = mascotas,
            serviciosDisponibles = servicios,
            isLoading = false
        )
    }.catch {
        emit(AgendarCitaUiState(isLoading = false))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AgendarCitaUiState())

    val uiState: StateFlow<AgendarCitaUiState> = _uiStateFlow

    fun guardarReserva(mascotaSeleccionada: Mascota?, servicioSeleccionado: Servicio?, diaSeleccionado: String) {
        if (mascotaSeleccionada == null || servicioSeleccionado == null || diaSeleccionado.length < 8) return
        viewModelScope.launch {
            val nuevaReserva = Reserva(
                usuarioId = usuarioId,
                mascotaId = mascotaSeleccionada.id,
                sucursalId = sucursalId,
                servicioId = servicioSeleccionado.id,
                profesionalId = null,
                fechaHora = "$diaSeleccionado 09:00",
                estado = "PENDIENTE",
                comentarios = null
            )
            reservaDao.insert(nuevaReserva)
        }
    }
}