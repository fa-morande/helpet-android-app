package com.helpetuser.ui.navigation

object Routes {
    const val HOME_SCREEN = "home_screen"
    const val SUCURSAL_SCREEN = "sucursal_screen"
    const val PROFILE_SCREEN = "profile_screen"
    const val MASCOTA_DETAIL_SCREEN = "mascota_detail_screen/{mascotaId}"
    const val WELCOME_SCREEN = "welcome_screen"
    const val LOGIN_SCREEN = "login_screen"

    const val REGISTER_SCREEN = "register_screen"
    const val AGENDAR_CITA_SCREEN = "agendar_cita/{sucursalId}"

    const val PROMOTIONS_SCREEN = "promociones"

    const val QUIENES_SOMOS = "quienes_somos"

    const val GESTIONAR_MASCOTAS = "gestionar_mascotas"

    const val AGREGAR_MASCOTA_SCREEN = "agregar_mascota_screen"
    const val HISTORIAL = "historial"
    fun createMascotaDetailRoute(mascotaId: String) = "mascota_detail_screen/$mascotaId"

    fun createAgendarCitaRoute(sucursalId: Int) = "agendar_cita/$sucursalId"
}
