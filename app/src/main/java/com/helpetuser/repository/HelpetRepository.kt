package com.helpetuser.repository

import com.helpetuser.model.*
class HelpetRepository {

    //funcion obtener usuario
    fun getUsuarioActual(): Usuario {
        return FakeData.usuarioActivo
    }

    //funcion para obtener mascotas activas del usuario
    fun getMascotasDelUsuario(usuarioId: String): List<Mascota> {
        // Filtra solo las mascotas activas para mostrarlas en la app
        return FakeData.todasLasMascotas.filter { it.usuarioId == usuarioId && it.activo }
    }




    fun getMascotaPorId(mascotaId: String): Mascota? {
        return FakeData.todasLasMascotas.find { it.id == mascotaId }
    }

    //funcion para reserva de citas
    fun getProximaReserva(mascotaId: String): Reserva? {
        return FakeData.todasLasReservas
            .filter { it.mascotaId == mascotaId && it.estado == "CONFIRMADA" }
            .minByOrNull { it.fechaHora } // La fecha más cercana en el futuro
    }

    fun getHistorialDeReservas(usuarioId: String): List<Reserva> {
        return FakeData.todasLasReservas.filter { it.usuarioId == usuarioId }
    }

    //funcion de promociones y favoritos
    fun getPromocionesDelUsuario(usuarioId: String): List<PromocionUsuario> {
        return FakeData.todasLasPromociones.filter { it.usuarioId == usuarioId }
    }

    // La lógica de "Favoritos" se calcula, no se guarda.
    // Una veterinaria es "favorita" si el usuario tiene 3 o más citas en la misma.
    fun getVeterinariasFavoritas(usuarioId: String): List<Veterinaria> {
        val historial = getHistorialDeReservas(usuarioId).filter { it.estado == "ATENDIDA" }
        val sucursalesVisitadas = historial.map { it.sucursalId }
        val veterinariasVisitadas = FakeData.todasLasSucursales
            .filter { sucursalesVisitadas.contains(it.id) }
            .map { it.veterinariaId }

        // Contamos cuantas veces se repite cada id de veterinaria
        val conteoVisitas = veterinariasVisitadas.groupingBy { it }.eachCount()

        // filtramos las que tienen 3 o más visitas
        val idsFavoritas = conteoVisitas.filter { it.value >= 3 }.keys

        return FakeData.todasLasVeterinarias.filter { idsFavoritas.contains(it.id) }
    }
}
