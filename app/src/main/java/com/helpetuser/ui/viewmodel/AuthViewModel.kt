package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.UsuarioDao
import com.helpetuser.data.manager.SessionManager
import com.helpetuser.model.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val registerSuccess: Boolean = false,
    val error: String? = null
)

class AuthViewModel(
    private val usuarioDao: UsuarioDao,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, contrasena: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val usuario = usuarioDao.getByEmail(email).firstOrNull()
            delay(1000) // Simulación de carga

            // --- VALIDACIÓN REAL DE CONTRASEÑA ---
            if (usuario != null && usuario.contrasena == contrasena) {
                sessionManager.saveUserId(usuario.id)
                _uiState.value = AuthUiState(isLoading = false, loginSuccess = true)
            } else if (usuario == null) {
                _uiState.value = AuthUiState(isLoading = false, error = "El correo no está registrado")
            } else {
                _uiState.value = AuthUiState(isLoading = false, error = "Contraseña incorrecta")
            }
        }
    }

    fun register(nombre: String, email: String, telefono: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val existe = usuarioDao.getByEmail(email).firstOrNull()
            delay(1500)

            if (existe != null) {
                _uiState.value = AuthUiState(isLoading = false, error = "El correo ya existe")
                return@launch
            }

            // --- GUARDADO REAL CON CONTRASEÑA ---
            val nuevoUsuario = Usuario(
                nombre = nombre,
                correo = email,
                telefono = telefono,
                contrasena = password, // <--- Guardamos la contraseña ingresada
                estado = true
            )
            usuarioDao.insert(nuevoUsuario)

            val usuarioCreado = usuarioDao.getByEmail(email).firstOrNull()
            if (usuarioCreado != null) {
                sessionManager.saveUserId(usuarioCreado.id)
                _uiState.value = AuthUiState(isLoading = false, registerSuccess = true)
            } else {
                _uiState.value = AuthUiState(isLoading = false, error = "Error al crear usuario")
            }
        }
    }

    fun clearError() {
        _uiState.value = AuthUiState()
    }
}