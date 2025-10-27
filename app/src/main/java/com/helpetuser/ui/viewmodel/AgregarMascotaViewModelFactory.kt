package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.MascotaDao

class AgregarMascotaViewModelFactory(
    private val mascotaDao: MascotaDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgregarMascotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgregarMascotaViewModel(mascotaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}