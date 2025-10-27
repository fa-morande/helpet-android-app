package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.PromocionUsuarioDao
import com.helpetuser.data.local.dao.UsuarioDao
import com.helpetuser.data.local.dao.VeterinariaDao
import com.helpetuser.model.PromocionUsuario
import com.helpetuser.model.Usuario
import com.helpetuser.model.Veterinaria
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ProfileUiState(
    val usuario: Usuario? = null,
    val veterinariasFavoritas: List<Veterinaria> = emptyList(),
    val promociones: List<PromocionUsuario> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class ProfileViewModel(
    private val usuarioDao: UsuarioDao,
    private val veterinariaDao: VeterinariaDao,
    private val promocionUsuarioDao: PromocionUsuarioDao
) : ViewModel() {

    //se sigue con el user 1
    private val usuarioId = 1

    //juntamos los 3 flows
    private val _uiStateFlow = combine(
        usuarioDao.getById(usuarioId),
        veterinariaDao.getAll(),
        promocionUsuarioDao.getByUsuarioId(usuarioId)
    ) { usuario, veterinarias, promociones ->
        ProfileUiState(
            usuario = usuario,
            veterinariasFavoritas = veterinarias,
            promociones = promociones,
            isLoading = false
        )
    }

    //se explone el flow
    val uiState: StateFlow<ProfileUiState> = _uiStateFlow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProfileUiState(isLoading = true)
        )
}