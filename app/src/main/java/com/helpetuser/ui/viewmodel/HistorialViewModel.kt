package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.ReservaDao
import com.helpetuser.model.Reserva
import kotlinx.coroutines.flow.*

// uiState para el historial
data class HistorialUiState(
    val historialReservas: List<Reserva> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class HistorialViewModel(
    private val reservaDao: ReservaDao
) : ViewModel() {

    //se sigue con el user 1
    private val usuarioId = 1

    //expone el flow del historial mapeado a UiState
    val uiState: StateFlow<HistorialUiState> = reservaDao.getHistorialByUsuarioId(usuarioId)
        .map { reservas ->
            HistorialUiState(historialReservas = reservas, isLoading = false)
        }
        .catch { e ->
            emit(HistorialUiState(isLoading = false, error = e.localizedMessage ?: "Error al cargar historial"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HistorialUiState(isLoading = true)
        )
}