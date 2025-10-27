package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.data.local.dao.ReservaDao

class MascotaDetailViewModelFactory(
    private val mascotaDao: MascotaDao,
    private val reservaDao: ReservaDao,
    private val mascotaId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MascotaDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MascotaDetailViewModel(mascotaDao, reservaDao, mascotaId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}