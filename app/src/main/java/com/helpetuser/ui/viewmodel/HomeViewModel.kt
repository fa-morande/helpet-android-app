package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.helpetuser.model.Mascota
import com.helpetuser.model.Reserva
import com.helpetuser.repository.HelpetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Wrapper para combinar la mascota con su próxima reserva
data class MascotaConReserva(
    val mascota: Mascota,
    val proximaReserva: Reserva?
)

// Define el estado de la pantalla Home
data class HomeUiState(
    val mascotas: List<MascotaConReserva> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class HomeViewModel(
    private val repository: HelpetRepository = HelpetRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // El ID del usuario debería ser dinámico en una app real
        cargarMascotasDelUsuario("1") // Usamos el ID "1" como String
    }

    private fun cargarMascotasDelUsuario(usuarioId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val misMascotas = repository.getMascotasDelUsuario(usuarioId)
                val mascotasConReservas = misMascotas.map { mascota ->
                    MascotaConReserva(
                        mascota = mascota,
                        proximaReserva = repository.getProximaReserva(mascota.id)
                    )
                }
                _uiState.update {
                    it.copy(mascotas = mascotasConReservas, isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error al cargar mascotas: ${e.message}", isLoading = false) }
            }
        }
    }

    // Companion object que actúa como Factory para crear el ViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Aquí se especifica cómo crear la instancia de HomeViewModel,
                // pasando el repositorio como dependencia.
                return HomeViewModel(
                    HelpetRepository()
                ) as T
            }
        }
    }
}
