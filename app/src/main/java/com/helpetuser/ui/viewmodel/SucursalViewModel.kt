package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.SucursalDao
import com.helpetuser.data.local.dao.VeterinariaDao
import com.helpetuser.model.Sucursal
import kotlinx.coroutines.flow.*


data class SucursalCardModel(
    val sucursal: Sucursal,
    val logoUrl: String?
)

data class SucursalScreenUiState(
    val sucursalesMostradas: List<SucursalCardModel> = emptyList(),
    val comunasFiltro: List<String> = emptyList(),
    val isLoading: Boolean = true
)

class SucursalViewModel(
    veterinariaDao: VeterinariaDao,
    sucursalDao: SucursalDao
) : ViewModel() {

    private val _veterinariasFlow = veterinariaDao.getAll()
    private val _sucursalesFlow = sucursalDao.getAll()

    private val _selectedComuna = MutableStateFlow<String?>(null)
    val selectedComuna: StateFlow<String?> = _selectedComuna.asStateFlow()

    // se junta lo que hay para el uistate final
    val uiState: StateFlow<SucursalScreenUiState> = combine(
        _veterinariasFlow,
        _sucursalesFlow,
        _selectedComuna
    ) { veterinarias, sucursales, comunaSeleccionada ->

        val vetMap = veterinarias.associateBy { it.id }

        val sucursalesFiltradas = if (comunaSeleccionada == null) {
            sucursales
        } else {
            sucursales.filter { it.comuna == comunaSeleccionada }
        }

        val cardModels = sucursalesFiltradas.map { suc ->
            SucursalCardModel(
                sucursal = suc,
                logoUrl = vetMap[suc.veterinariaId]?.logoUrl
            )
        }

        val comunasUnicas = sucursales.map { it.comuna }.distinct().sorted()

        SucursalScreenUiState(
            sucursalesMostradas = cardModels,
            comunasFiltro = comunasUnicas,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SucursalScreenUiState(isLoading = true)
    )

    //funcion para actualizar el filtro
    fun filterByComuna(comuna: String?) {
        _selectedComuna.value = comuna
    }
}