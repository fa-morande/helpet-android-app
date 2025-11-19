package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    val veterinarias: List<Veterinaria> = emptyList(),
    val promociones: List<PromocionUsuario> = emptyList(),
    val isLoading: Boolean = true
)

class ProfileViewModel(
    private val usuarioDao: UsuarioDao,
    private val veterinariaDao: VeterinariaDao,
    private val promocionUsuarioDao: PromocionUsuarioDao,
    private val usuarioId: Int // ID Dinámico
) : ViewModel() {

    private val _uiStateFlow = combine(
        usuarioDao.getById(usuarioId), // Usar ID
        veterinariaDao.getAll(),
        promocionUsuarioDao.getByUsuarioId(usuarioId) // Usar ID
    ) { usuario, veterinarias, promociones ->
        ProfileUiState(
            usuario = usuario,
            veterinarias = veterinarias,
            promociones = promociones,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState()
    )

    val uiState: StateFlow<ProfileUiState> = _uiStateFlow
}