package com.helpetuser.utils

import android.location.Location

object LocationUtils {

    /**
     * Function 2: Calcula la distancia entre dos coordenadas geográficas.
     * Retorna el resultado en Kilómetros.
     */
    fun calcularDistanciaKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val result = FloatArray(1)
        // Usamos la API nativa de Android para máxima precisión
        Location.distanceBetween(lat1, lon1, lat2, lon2, result)
        // distanceBetween retorna metros, dividimos por 1000 para kms
        return result[0] / 1000.0
    }

    /**
     * Formatea un valor double a un String amigable (ej: "2.5 km")
     */
    fun formatDistancia(distanciaKm: Double): String {
        return "%.1f km".format(distanciaKm)
    }
}