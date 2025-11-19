package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.helpetuser.data.local.dao.UsuarioDao
import com.helpetuser.data.manager.SessionManager

class AuthViewModelFactory(
    private val usuarioDao: UsuarioDao,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(usuarioDao, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}