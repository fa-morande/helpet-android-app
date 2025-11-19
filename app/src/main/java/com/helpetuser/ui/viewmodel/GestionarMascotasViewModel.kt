package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.model.Mascota
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class GestionarMascotasUiState(
    val mascotasUsuario: List<Mascota> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class GestionarMascotasViewModel(
    private val mascotaDao: MascotaDao,
    private val usuarioId: Int // <--- ID dinámico recibido
) : ViewModel() {

    // Se usa el usuarioId del constructor
    val uiState: StateFlow<GestionarMascotasUiState> = mascotaDao.getByUsuarioId(usuarioId)
        .map { mascotas ->
            GestionarMascotasUiState(mascotasUsuario = mascotas, isLoading = false)
        }
        .catch { e -> emit(GestionarMascotasUiState(isLoading = false, error = e.localizedMessage)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GestionarMascotasUiState(isLoading = true)
        )

    fun eliminarMascota(mascota: Mascota?) {
        if (mascota == null) return
        viewModelScope.launch {
            mascotaDao.delete(mascota)
        }
    }
}