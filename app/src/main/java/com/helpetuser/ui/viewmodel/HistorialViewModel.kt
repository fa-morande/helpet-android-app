package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.ReservaDao
import com.helpetuser.model.Reserva
import kotlinx.coroutines.flow.*

data class HistorialUiState(
    val historialReservas: List<Reserva> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class HistorialViewModel(
    private val reservaDao: ReservaDao,
    private val usuarioId: Int // <--- ID dinámico recibido
) : ViewModel() {

    // Se usa el usuarioId del constructor
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