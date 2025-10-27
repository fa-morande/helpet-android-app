package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.model.Mascota
import kotlinx.coroutines.launch

class AgregarMascotaViewModel(
    private val mascotaDao: MascotaDao
) : ViewModel() {

    fun agregarMascota(
        nombre: String,
        fechaNacimiento: String,
        especie: String,
        raza: String
    ) {
        viewModelScope.launch {
            // se crea el objeto Mascota
            val nuevaMascota = Mascota(
                usuarioId = 1,
                nombre = nombre,
                especie = especie,
                sexo = "No especificado",
                raza = raza,
                fechaNacimiento = fechaNacimiento,
                activo = true
            )
            mascotaDao.insert(nuevaMascota)
        }
    }
}