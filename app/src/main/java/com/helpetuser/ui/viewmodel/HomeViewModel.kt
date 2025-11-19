package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.data.local.dao.ReservaDao
import com.helpetuser.model.Mascota
import com.helpetuser.model.Reserva
import kotlinx.coroutines.flow.*

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
    private val reservaDao: ReservaDao,
    private val usuarioId: Int
) : ViewModel() {

    private val _uiStateFlow = combine(
        mascotaDao.getByUsuarioId(usuarioId),
        reservaDao.getAll()
    ) { mascotas, todasLasReservas ->
        val mascotasConData = mascotas.map { mascota ->
            val reserva = todasLasReservas
                .filter { it.mascotaId == mascota.id && it.usuarioId == usuarioId }
                .maxByOrNull { it.fechaHora }
            MascotaConReserva(mascota, reserva)
        }
        HomeUiState(mascotas = mascotasConData, isLoading = false)
    }.catch { e ->
        emit(HomeUiState(isLoading = false, error = e.message))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiStateFlow
}