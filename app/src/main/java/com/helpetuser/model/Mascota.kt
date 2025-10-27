package com.helpetuser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mascotas")
data class Mascota(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val nombre: String,
    val especie: String,
    val sexo: String,
    val raza: String,
    val fechaNacimiento: String,
    val foto: String? = null,
    val activo: Boolean = true
)