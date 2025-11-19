package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.MascotaDao

class AgregarMascotaViewModelFactory(
    private val mascotaDao: MascotaDao,
    private val usuarioId: Int // <--- Recibe el ID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgregarMascotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Pasa el ID al ViewModel
            return AgregarMascotaViewModel(mascotaDao, usuarioId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}