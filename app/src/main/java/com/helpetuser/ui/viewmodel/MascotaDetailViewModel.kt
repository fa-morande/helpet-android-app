package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.data.local.dao.ReservaDao
import com.helpetuser.model.Mascota
import com.helpetuser.model.Reserva
import kotlinx.coroutines.flow.*

//se agrega ultima reserva al uiState
data class MascotaDetailUiState(
    val mascota: Mascota? = null,
    val ultimaReserva: Reserva? = null,
    val isLoading: Boolean = true
)

class MascotaDetailViewModel(
    mascotaDao: MascotaDao,
    reservaDao: ReservaDao,
    mascotaId: Int
) : ViewModel() {

    private val _uiStateFlow = combine(
        mascotaDao.getById(mascotaId),
        reservaDao.getProximaByMascotaId(mascotaId)
    ) { mascota, reserva ->
        //se crea el uiState con ambos datos
        MascotaDetailUiState(
            mascota = mascota,
            ultimaReserva = reserva,
            isLoading = false
        )
    }
        .catch {
            emit(MascotaDetailUiState(isLoading = false))
        }

    //se expone el estado sin cambios
    val uiState: StateFlow<MascotaDetailUiState> = _uiStateFlow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MascotaDetailUiState(isLoading = true)
        )
}