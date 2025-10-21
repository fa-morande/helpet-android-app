package com.helpetuser.repository

import com.helpetuser.model.*
import java.util.Date
import java.util.concurrent.TimeUnit

object FakeData {
    val usuarioActivo = Usuario(
        id = "1",
        nombre = "Fabián Morande",
        correo = "fa.morande@duocuc.cl",
        telefono = "+56912345678",
        foto = "",
        estado = true
    )

    val mascota1 = Mascota(
        id = "1",
        usuarioId = "1",
        nombre = "Kiara",
        especie = "Perra",
        raza = "Alaskan Malamute",
        fechaNacimiento = Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(365 * 4)),
        activo = true
    )
    val mascota2 = Mascota(
        id = "2",
        usuarioId = "1",
        nombre = "kimba",
        especie = "Perra",
        raza = "Boxer",
        fechaNacimiento = Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(365 * 10)),
        activo = false
    )


    //ESTO ES GENERADO ARTIFICIALMENTE
    val todasLasMascotas = listOf(mascota1, mascota2)

    val vetAndes = Veterinaria(id = "1", nombre = "VetAndes", rut = "76.123.456-7")
    val animalia = Veterinaria(id = "2", nombre = "Clínica Animalia", rut = "77.987.654-3")
    val todasLasVeterinarias = listOf(vetAndes, animalia)

    val sucursalVetAndes = Sucursal(id = "1", veterinariaId = "1", nombre = "VetAndes Providencia", comuna = "Providencia", direccion = "Av. Providencia 200", telefono = "+56222912824", latitud = -33.4318, longitud = -70.6092, horario = "L-V 09:00-19:00")
    val sucursalAnimalia = Sucursal(id = "2", veterinariaId = "2", nombre = "Animalia Las Condes", comuna = "Las Condes", direccion = "Apoquindo 300", telefono = "+56222272679", latitud = -33.4150, longitud = -70.5730, horario = "L-S 10:00-18:00")
    val todasLasSucursales = listOf(sucursalVetAndes, sucursalAnimalia)

    // --- SERVICIOS Y PROFESIONALES ---
    val servicioConsulta = Servicio(id = "1", nombre = "Consulta General", descripcion = "Evaluación clínica general", precio = 15000, duracionMinutos = 30)
    val servicioVacuna = Servicio(id = "2", nombre = "Vacuna Antirrábica", descripcion = "Inmunización antirrábica", precio = 12000, duracionMinutos = 15)
    val todosLosServicios = listOf(servicioConsulta, servicioVacuna)

    val profesionalCamila = Profesional(id = "1", sucursalId = "1", nombre = "Camila Rojas", especialidad = "Cirugía Menor")
    val todosLosProfesionales = listOf(profesionalCamila)

    // --- RESERVAS (HISTORIAL Y PRÓXIMAS) ---
    val reservaPasada = Reserva(id = "1", usuarioId = "1", mascotaId = "1", sucursalId = "1", servicioId = "2", profesionalId = "1", fechaHora = Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30)), estado = "ATENDIDA", comentarios = "Excelente atencion!!")
    val reservaProxima = Reserva(id = "2", usuarioId = "1", mascotaId = "1", sucursalId = "1", servicioId = "1", profesionalId = "1", fechaHora = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10)), estado = "CONFIRMADA", comentarios = null)
    val todasLasReservas = listOf(reservaPasada, reservaProxima)

    // --- PROMOCIONES GANADAS ---
    val promoVetAndes = PromocionUsuario(id = "1", usuarioId = "1", veterinariaId = "1", titulo = "¡20% dscto. en tu próxima consulta!", descripcion = "Gracias por tu lealtad, te regalamos un descuento.", fechaExpiracion = Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)), estado = "PENDIENTE")
    val todasLasPromociones = listOf(promoVetAndes)
}
