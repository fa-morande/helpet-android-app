package com.helpetuser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promociones_usuario")
data class PromocionUsuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val veterinariaId: Int,
    val titulo: String,
    val descripcion: String,
    val fechaExpiracion: String,
    val estado: String
)