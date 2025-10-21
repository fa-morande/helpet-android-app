package com.helpetuser.model

data class Veterinaria(
    val id: String,
    val nombre: String,
    val rut: String,
    val sitioWeb: String? = null,
    val logoUrl: String? = null
)
