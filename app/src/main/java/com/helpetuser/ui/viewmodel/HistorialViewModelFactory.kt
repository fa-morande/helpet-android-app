package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.ReservaDao

class HistorialViewModelFactory(
    private val reservaDao: ReservaDao,
    private val usuarioId: Int // <--- Recibe el ID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistorialViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Pasa el ID al ViewModel
            return HistorialViewModel(reservaDao, usuarioId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}