package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.SucursalDao
import com.helpetuser.data.local.dao.VeterinariaDao

class SucursalViewModelFactory(
    private val veterinariaDao: VeterinariaDao,
    private val sucursalDao: SucursalDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SucursalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SucursalViewModel(veterinariaDao, sucursalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}