package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.data.local.dao.ReservaDao
import com.helpetuser.model.Mascota
import com.helpetuser.model.Reserva
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class MascotaConReserva(
    val mascota: Mascota,
    val proximaReserva: Reserva?
)

data class HomeUiState(
    val mascotas: List<MascotaConReserva> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class HomeViewModel(
    private val mascotaDao: MascotaDao,
    private val reservaDao: ReservaDao
) : ViewModel() {
    private val usuarioId = 1

    //flow reactivo
    //junta el flow de mascotas con el flow de reservas
    private val _uiStateFlow = combine(
        mascotaDao.getByUsuarioId(usuarioId),
        reservaDao.getAll()
    ) { misMascotas, todasLasReservas ->
        val mascotasConReservas = misMascotas.map { mascota ->
            MascotaConReserva(
                mascota = mascota,
                proximaReserva = todasLasReservas
                    .filter { it.mascotaId == mascota.id }
                    .maxByOrNull { it.fechaHora }
            )
        }
        HomeUiState(mascotas = mascotasConReservas, isLoading = false)
    }

    val uiState: StateFlow<HomeUiState> = _uiStateFlow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeUiState(isLoading = true)
        )
}