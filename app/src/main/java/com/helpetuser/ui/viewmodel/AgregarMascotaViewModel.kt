package com.helpetuser.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helpetuser.data.local.dao.MascotaDao
import com.helpetuser.model.Mascota
import kotlinx.coroutines.launch

class AgregarMascotaViewModel(
    private val mascotaDao: MascotaDao,
    private val usuarioId: Int // <--- ID dinámico recibido
) : ViewModel() {

    fun agregarMascota(
        nombre: String,
        fechaNacimiento: String,
        especie: String,
        raza: String
    ) {
        viewModelScope.launch {
            val nuevaMascota = Mascota(
                usuarioId = usuarioId, // <--- ¡Aquí se usa el ID real!
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