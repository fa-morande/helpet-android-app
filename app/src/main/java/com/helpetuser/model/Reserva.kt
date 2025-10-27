package com.helpetuser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservas")
data class Reserva(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val mascotaId: Int,
    val sucursalId: Int,
    val servicioId: Int,
    val profesionalId: Int?,
    val fechaHora: String,
    val estado: String,
    val comentarios: String?,
)