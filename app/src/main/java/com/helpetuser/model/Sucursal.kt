package com.helpetuser.model

data class Sucursal(
    val id: String,
    val veterinariaId: String,
    val nombre: String,
    val comuna: String,
    val direccion: String,
    val telefono: String?,
    val latitud: Double,
    val longitud: Double,
    val horario: String? = null
)
