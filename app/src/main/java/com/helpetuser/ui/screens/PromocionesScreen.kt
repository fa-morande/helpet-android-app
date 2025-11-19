package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.PromocionUsuarioDao
import com.helpetuser.data.local.dao.UsuarioDao
import com.helpetuser.data.local.dao.VeterinariaDao
import com.helpetuser.model.PromocionUsuario
import com.helpetuser.model.Usuario
import com.helpetuser.model.Veterinaria
import kotlinx.coroutines.flow.*

data class ProfileUiState(
    val usuario: Usuario? = null,
    val veterinariasFavoritas: List<Veterinaria> = emptyList(), // Cambié nombre a 'veterinariasFavoritas' para consistencia con versiones previas si es necesario
    val promociones: List<PromocionUsuario> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null // <--- AGREGADO: Campo necesario para PromocionesScreen
)

class ProfileViewModel(
    private val usuarioDao: UsuarioDao,
    private val veterinariaDao: VeterinariaDao,
    private val promocionUsuarioDao: PromocionUsuarioDao,
    private val usuarioId: Int
) : ViewModel() {

    private val _uiStateFlow = combine(
        usuarioDao.getById(usuarioId),
        veterinariaDao.getAll(),
        promocionUsuarioDao.getByUsuarioId(usuarioId)
    ) { usuario, veterinarias, promociones ->
        ProfileUiState(
            usuario = usuario,
            veterinariasFavoritas = veterinarias,
            promociones = promociones,
            isLoading = false,
            error = null
        )
    }.catch { e ->
        // Manejo de errores para que la UI lo sepa
        emit(ProfileUiState(isLoading = false, error = e.message ?: "Error desconocido"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState()
    )

    val uiState: StateFlow<ProfileUiState> = _uiStateFlow
}