package com.helpetuser.ui.navigation

object Routes {
    const val HOME_SCREEN = "home_screen"
    const val ADD_SCREEN = "add_screen"
    const val PROFILE_SCREEN = "profile_screen"
    const val MASCOTA_DETAIL_SCREEN = "mascota_detail_screen/{mascotaId}"

    fun createMascotaDetailRoute(mascotaId: String) = "mascota_detail_screen/$mascotaId"
}
