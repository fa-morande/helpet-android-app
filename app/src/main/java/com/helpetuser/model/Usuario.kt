package com.helpetuser.model

data class Usuario(
    val id: String,
    val nombre: String,
    val correo: String,
    val telefono: String,
    val foto: String? = null,
    val estado: Boolean = true
)
