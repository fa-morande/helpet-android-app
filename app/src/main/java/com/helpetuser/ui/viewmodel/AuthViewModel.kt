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
    private val sessionManager: SessionManager // Agregado
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, contrasena: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            // Validación simulada (esto luego será real)
            val usuario = usuarioDao.getByEmail(email).firstOrNull()
            delay(1500)

            if (usuario != null && contrasena == "1234") {
                sessionManager.saveUserId(usuario.id) // <-- GUARDAMOS SESIÓN
                _uiState.value = AuthUiState(isLoading = false, loginSuccess = true)
            } else {
                _uiState.value = AuthUiState(isLoading = false, error = "Credenciales incorrectas")
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

            val nuevoUsuario = Usuario(
                nombre = nombre, correo = email, telefono = telefono, estado = true
            )
            usuarioDao.insert(nuevoUsuario)

            // Recuperamos el usuario recién creado para obtener su ID
            val usuarioCreado = usuarioDao.getByEmail(email).firstOrNull()
            if (usuarioCreado != null) {
                sessionManager.saveUserId(usuarioCreado.id) // <-- GUARDAMOS SESIÓN
                _uiState.value = AuthUiState(isLoading = false, registerSuccess = true)
            } else {
                _uiState.value = AuthUiState(isLoading = false, error = "Error al crear usuario")
            }
        }
    }

    fun clearError() {
        _uiState.value = AuthUiState() // Resetea todo el estado
    }
}