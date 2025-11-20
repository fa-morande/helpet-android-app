package com.helpetuser.ui.components

import com.helpetuser.model.Sucursal
import com.helpetuser.model.Veterinaria

// Wrapper para pasar datos combinados a la UI de forma limpia
data class VeterinariaConDistancia(
    val veterinaria: Veterinaria,
    val sucursal: Sucursal,
    val distanciaKm: Double
)