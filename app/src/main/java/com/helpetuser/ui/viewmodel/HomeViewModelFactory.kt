package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.data.local.dao.ReservaDao

class HomeViewModelFactory(
    private val mascotaDao: MascotaDao,
    private val reservaDao: ReservaDao,
    private val usuarioId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(mascotaDao, reservaDao, usuarioId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}