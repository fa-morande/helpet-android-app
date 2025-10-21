package com.helpetuser.model

import java.util.Date

data class Reserva(
    val id: String,
    val usuarioId: String,
    val mascotaId: String,
    val sucursalId: String,
    val servicioId: String,
    val profesionalId: String?,
    val fechaHora: Date,
    val estado: String,
    val comentarios: String?,
)
