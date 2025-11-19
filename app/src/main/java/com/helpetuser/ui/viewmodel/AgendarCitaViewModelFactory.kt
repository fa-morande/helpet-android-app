package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.data.local.dao.ReservaDao
import com.helpetuser.data.local.dao.ServicioDao
import com.helpetuser.data.local.dao.SucursalDao
import com.helpetuser.data.local.dao.VeterinariaDao

class AgendarCitaViewModelFactory(
    private val reservaDao: ReservaDao,
    private val mascotaDao: MascotaDao,
    private val servicioDao: ServicioDao,
    private val sucursalDao: SucursalDao,
    private val veterinariaDao: VeterinariaDao,
    private val sucursalId: Int,
    private val usuarioId: Int // <-- AÑADIR
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgendarCitaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgendarCitaViewModel(
                reservaDao, mascotaDao, servicioDao, sucursalDao, veterinariaDao, sucursalId, usuarioId // <-- PASAR
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}