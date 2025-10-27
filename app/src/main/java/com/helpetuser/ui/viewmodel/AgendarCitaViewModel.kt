package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.*
import com.helpetuser.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

//uiState para cargar datos
data class AgendarCitaUiState(
    val sucursal: Sucursal? = null,
    val veterinaria: Veterinaria? = null,
    val mascotasUsuario: List<Mascota> = emptyList(),
    val serviciosDisponibles: List<Servicio> = emptyList(),
    val isLoading: Boolean = true
)

//para obtener datos del viewmodel
class AgendarCitaViewModel(
    private val reservaDao: ReservaDao,
    private val mascotaDao: MascotaDao,
    private val servicioDao: ServicioDao,
    private val sucursalDao: SucursalDao,
    private val veterinariaDao: VeterinariaDao,
    private val sucursalId: Int
) : ViewModel() {

    private val usuarioId = 1


    private val _uiStateFlow = combine(
        sucursalDao.getById(sucursalId),
        mascotaDao.getByUsuarioId(usuarioId),
        servicioDao.getAll()
    ) { sucursal, mascotas, servicios ->
        //cargar la veterinaria asociada a la sucursal
        val veterinaria = sucursal?.let { veterinariaDao.getById(it.veterinariaId).firstOrNull() }

        AgendarCitaUiState(
            sucursal = sucursal,
            veterinaria = veterinaria,
            mascotasUsuario = mascotas,
            serviciosDisponibles = servicios,
            isLoading = false
        )
    }.catch {
        emit(AgendarCitaUiState(isLoading = false))
    }

    val uiState: StateFlow<AgendarCitaUiState> = _uiStateFlow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AgendarCitaUiState(isLoading = true) // Estado inicial
        )

    fun guardarReserva(
        mascotaSeleccionada: Mascota?,
        servicioSeleccionado: Servicio?,
        diaSeleccionado: String
    ) {
        // solo guarda si tiene los datos necesarios
        if (mascotaSeleccionada == null || servicioSeleccionado == null || diaSeleccionado.length < 8) return

        viewModelScope.launch {
            //agenda hora automaticamente a las 9
            val fechaHoraCompleta = "$diaSeleccionado 09:00"

            val nuevaReserva = Reserva(
                usuarioId = usuarioId,
                mascotaId = mascotaSeleccionada.id,
                sucursalId = sucursalId,
                servicioId = servicioSeleccionado.id,
                profesionalId = null,
                fechaHora = fechaHoraCompleta,
                estado = "PENDIENTE",
                comentarios = null
            )
            reservaDao.insert(nuevaReserva)
        }
    }
}