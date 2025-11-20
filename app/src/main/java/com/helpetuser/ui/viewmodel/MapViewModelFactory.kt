package com.helpetuser.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.SucursalDao
import com.helpetuser.data.local.dao.VeterinariaDao

class MapViewModelFactory(
    private val sucursalDao: SucursalDao,
    private val veterinariaDao: VeterinariaDao,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(sucursalDao, veterinariaDao, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}