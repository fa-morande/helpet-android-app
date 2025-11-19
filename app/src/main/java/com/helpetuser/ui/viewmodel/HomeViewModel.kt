package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.data.local.dao.ReservaDao
import com.helpetuser.model.Mascota
import com.helpetuser.model.Reserva
import kotlinx.coroutines.flow.*

// Clase contenedora para la UI
data class MascotaConReserva(
    val mascota: Mascota,
    val proximaReserva: Reserva?
)

data class HomeUiState(
    val mascotas: List<MascotaConReserva> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null // Agregado de nuevo
)

class HomeViewModel(
    private val mascotaDao: MascotaDao,
    private val reservaDao: ReservaDao,
    private val usuarioId: Int
) : ViewModel() {

    private val _uiStateFlow = combine(
        mascotaDao.getByUsuarioId(usuarioId),
        reservaDao.getAll()
    ) { mascotas, todasLasReservas ->

        val mascotasConData = mascotas.map { mascota ->
            // Buscamos la reserva más próxima para esta mascota
            val reserva = todasLasReservas
                .filter { it.mascotaId == mascota.id && it.usuarioId == usuarioId }
                // Ordenamos por fecha para tomar la más reciente/próxima
                // Nota: Aquí podrías filtrar solo las futuras si quisieras
                .maxByOrNull { it.fechaHora }

            MascotaConReserva(mascota, reserva)
        }

        HomeUiState(
            mascotas = mascotasConData,
            isLoading = false,
            error = null
        )
    }.catch { e ->
        emit(HomeUiState(isLoading = false, error = e.message ?: "Error desconocido"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    val uiState: StateFlow<HomeUiState> = _uiStateFlow
}