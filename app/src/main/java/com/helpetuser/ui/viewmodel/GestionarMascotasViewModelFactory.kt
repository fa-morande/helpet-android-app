package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.MascotaDao

class GestionarMascotasViewModelFactory(
    private val mascotaDao: MascotaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GestionarMascotasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GestionarMascotasViewModel(mascotaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}