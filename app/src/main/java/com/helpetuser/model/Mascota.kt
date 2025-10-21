package com.helpetuser.model

import java.util.Date

data class Mascota(
    val id: String,
    val usuarioId: String,
    val nombre: String,
    val especie: String,
    val raza: String,
    val fechaNacimiento: Date,
    val foto: String? = null,
    val activo: Boolean = true
)
