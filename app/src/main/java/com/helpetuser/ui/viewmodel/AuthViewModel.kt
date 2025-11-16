package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.UsuarioDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import com.helpetuser.model.Usuario

data class AuthUiState(
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val registerSuccess: Boolean = false,
    val error: String? = null
)

class AuthViewModel(
    private val usuarioDao: UsuarioDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, contrasena: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            // Simulación de validación
            // En un futuro, aquí iría la llamada a tu backend (PL/SQL)
            val usuario = usuarioDao.getByEmail(email).firstOrNull()

            kotlinx.coroutines.delay(2000) // Simula la demora de la red

            if (usuario != null && contrasena == "1234") { // Contraseña de prueba
                _uiState.value = AuthUiState(isLoading = false, loginSuccess = true)
            } else {
                _uiState.value = AuthUiState(isLoading = false, error = "Credenciales incorrectas")
            }
        }
    }

    // Para limpiar el error cuando el usuario vuelve a escribir
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    // Dentro de la clase AuthViewModel

// (Tu función login() y clearError() existentes...)

    fun register(nombre: String, email: String, telefono: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)

            // 1. Validar que el email no exista
            val usuarioExistente = usuarioDao.getByEmail(email).firstOrNull()

            kotlinx.coroutines.delay(1500) // Simula la demora de red

            if (usuarioExistente != null) {
                _uiState.value = AuthUiState(isLoading = false, error = "El correo ya está registrado")
                return@launch
            }

            // 2. Crear y guardar el nuevo usuario
            val nuevoUsuario = Usuario(
                nombre = nombre,
                correo = email,
                telefono = telefono,
                estado = true
                // La contraseña se manejaría en el backend, aquí la omitimos
            )
            usuarioDao.insert(nuevoUsuario)

            // 3. Notificar éxito
            _uiState.value = AuthUiState(isLoading = false, registerSuccess = true)
        }
    }
}