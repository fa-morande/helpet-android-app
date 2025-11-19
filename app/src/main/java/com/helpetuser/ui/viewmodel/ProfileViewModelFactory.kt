package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.PromocionUsuarioDao
import com.helpetuser.data.local.dao.UsuarioDao
import com.helpetuser.data.local.dao.VeterinariaDao

class ProfileViewModelFactory(
    private val usuarioDao: UsuarioDao,
    private val veterinariaDao: VeterinariaDao,
    private val promocionUsuarioDao: PromocionUsuarioDao,
    private val usuarioId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(usuarioDao, veterinariaDao, promocionUsuarioDao, usuarioId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}