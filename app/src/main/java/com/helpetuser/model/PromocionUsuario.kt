package com.helpetuser.model

import java.util.Date

data class PromocionUsuario(
    val id: String,
    val usuarioId: String,
    val veterinariaId: String,
    val titulo: String,
    val descripcion: String,
    val fechaExpiracion: Date,
    val estado: String
)
