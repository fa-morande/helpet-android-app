package com.helpetuser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sucursales")
data class Sucursal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val veterinariaId: Int,
    val nombre: String,
    val comuna: String,
    val direccion: String,
    val telefono: String?,
    val latitud: Double,
    val longitud: Double,
    val horario: String? = null
)