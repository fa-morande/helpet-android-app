package com.helpetuser.data.local

import com.helpetuser.model.*

object Data {
//------------------------------------------------------------
// LOS DATOS FAKE SON GENERADOS ARTIFICIALMENTE
//------------------------------------------------------------
    fun getVeterinarias(): List<Veterinaria> {
        return listOf(
            Veterinaria(nombre = "Clínica VetSalud", rut = "76.123.456-1", sitioWeb = "www.vetsalud.cl", logoUrl = "https://picsum.photos/200/200?random=1.webp"), // id=1
            Veterinaria(nombre = "Doctor Pet", rut = "77.234.567-2", sitioWeb = "www.doctorpet.cl", logoUrl = "https://picsum.photos/200/200?random=2.webp"), // id=2
            Veterinaria(nombre = "Animalia Center", rut = "78.345.678-3", sitioWeb = "www.animalia.cl", logoUrl = "https://picsum.photos/200/200?random=3.webp"), // id=3
            Veterinaria(nombre = "Patitas Felices", rut = "79.456.789-4", sitioWeb = "www.patitasfelices.cl", logoUrl = "https://picsum.photos/200/200?random=4.webp"), // id=4
            Veterinaria(nombre = "Mundo Animal", rut = "76.567.890-5", sitioWeb = "www.mundoanimal.cl", logoUrl = "https://picsum.photos/200/200?random=5.webp"), // id=5
            Veterinaria(nombre = "VetExpress", rut = "77.678.901-6", sitioWeb = "www.vetexpress.cl", logoUrl = "https://picsum.photos/200/200?random=6.webp"), // id=6
            Veterinaria(nombre = "Arca Vet", rut = "78.789.012-7", sitioWeb = "www.arcavet.cl", logoUrl = "https://picsum.photos/200/200?random=7.webp"), // id=7
            Veterinaria(nombre = "Sanitos", rut = "79.890.123-8", sitioWeb = "www.sanitos.cl", logoUrl = "https://picsum.photos/200/200?random=8.webp"), // id=8
            Veterinaria(nombre = "Best Friends Vet", rut = "76.901.234-9", sitioWeb = "www.bestfriends.cl", logoUrl = "https://picsum.photos/200/200?random=9.webp"), // id=9
            Veterinaria(nombre = "Cuidado Total", rut = "77.012.345-K", sitioWeb = "www.cuidadototal.cl", logoUrl = "https://picsum.photos/200/200?random=10.webp") // id=10
        )
    }
    fun getSucursales(): List<Sucursal> {
        return listOf(
            Sucursal(veterinariaId = 1, nombre = "VetSalud Centro", comuna = "Cerrillos", direccion = "Av. Principal 123", telefono = "+5622111111", latitud = -33.4446, longitud = -70.6506, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 1, nombre = "VetSalud Norte", comuna = "Lo Espejo", direccion = "Calle Norte 456", telefono = "+5622111112", latitud = -33.4246, longitud = -70.6306, horario = "L-S 9-17h"),
            Sucursal(veterinariaId = 1, nombre = "VetSalud Oriente", comuna = "La Cisterna", direccion = "Pasaje Oriental 789", telefono = "+5622111113", latitud = -33.4446, longitud = -70.6106, horario = "L-V 10-19h"),
            Sucursal(veterinariaId = 2, nombre = "Doctor Pet Plaza", comuna = "Maipú", direccion = "Plaza Central 50", telefono = "+5622222221", latitud = -33.4510, longitud = -70.6400, horario = "L-S 10-20h"),
            Sucursal(veterinariaId = 2, nombre = "Doctor Pet El Roble", comuna = "El Bosque", direccion = "Av. El Roble 332", telefono = "+5622222222", latitud = -33.4550, longitud = -70.6150, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 2, nombre = "Doctor Pet Sur", comuna = "Cerrillos", direccion = "Calle Sur 880", telefono = "+5622222223", latitud = -33.4750, longitud = -70.6550, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 2, nombre = "Doctor Pet Urgencias", comuna = "Lo Espejo", direccion = "Av. Emergencia 911", telefono = "+5622222224", latitud = -33.4490, longitud = -70.6420, horario = "24/7"),
            Sucursal(veterinariaId = 3, nombre = "Animalia Gran Avenida", comuna = "La Cisterna", direccion = "Gran Avenida 1000", telefono = "+5622333331", latitud = -33.4370, longitud = -70.6110, horario = "L-V 8-17h"),
            Sucursal(veterinariaId = 3, nombre = "Animalia Parque", comuna = "Maipú", direccion = "Frente al Parque 202", telefono = "+5622333332", latitud = -33.4670, longitud = -70.6710, horario = "L-S 10-16h"),
            Sucursal(veterinariaId = 3, nombre = "Animalia Río", comuna = "El Bosque", direccion = "Costanera 505", telefono = "+5622333333", latitud = -33.4310, longitud = -70.6210, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 4, nombre = "Patitas Felices Central", comuna = "Cerrillos", direccion = "Calle Feliz 123", telefono = "+5622444441", latitud = -33.4420, longitud = -70.6520, horario = "L-S 9-19h"),
            Sucursal(veterinariaId = 4, nombre = "Patitas Felices Cordillera", comuna = "Lo Espejo", direccion = "Camino al Este 456", telefono = "+5622444442", latitud = -33.4520, longitud = -70.5920, horario = "L-V 10-19h"),
            Sucursal(veterinariaId = 4, nombre = "Patitas Felices Oeste", comuna = "La Cisterna", direccion = "Av. Poniente 789", telefono = "+5622444443", latitud = -33.4620, longitud = -70.6920, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 5, nombre = "Mundo Animal Intercomunal", comuna = "Maipú", direccion = "Calle Larga 560", telefono = "+5622555551", latitud = -33.4150, longitud = -70.6350, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 5, nombre = "Mundo Animal El Bosque", comuna = "El Bosque", direccion = "Pasaje Los Árboles 111", telefono = "+5622555552", latitud = -33.4580, longitud = -70.6180, horario = "L-S 10-17h"),
            Sucursal(veterinariaId = 5, nombre = "Mundo Animal Mascotas", comuna = "Cerrillos", direccion = "Av. Las Mascotas 222", telefono = "+5622555553", latitud = -33.4480, longitud = -70.6480, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 6, nombre = "VetExpress 24h", comuna = "Lo Espejo", direccion = "Calle Urgencia 001", telefono = "+5622666661", latitud = -33.4460, longitud = -70.6560, horario = "24/7"),
            Sucursal(veterinariaId = 6, nombre = "VetExpress Vacunas", comuna = "La Cisterna", direccion = "Av. Inmunidad 303", telefono = "+5622666662", latitud = -33.4780, longitud = -70.6580, horario = "L-V 10-19h"),
            Sucursal(veterinariaId = 6, nombre = "VetExpress PetShop", comuna = "Maipú", direccion = "Mall del Este, Local 15", telefono = "+5622666663", latitud = -33.4390, longitud = -70.6090, horario = "L-D 10-21h"),
            Sucursal(veterinariaId = 7, nombre = "Arca Vet Principal", comuna = "El Bosque", direccion = "Paseo Central 404", telefono = "+5622777771", latitud = -33.4430, longitud = -70.6530, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 7, nombre = "Arca Vet Lago", comuna = "Cerrillos", direccion = "Vista al Lago 505", telefono = "+5622777772", latitud = -33.4330, longitud = -70.6230, horario = "L-V 9-17h"),
            Sucursal(veterinariaId = 7, nombre = "Arca Vet Montaña", comuna = "Lo Espejo", direccion = "Refugio 606", telefono = "+5622777773", latitud = -33.4530, longitud = -70.5930, horario = "S-D 10-16h"),
            Sucursal(veterinariaId = 8, nombre = "Sanitos Centro", comuna = "La Cisterna", direccion = "Calle Sanidad 10", telefono = "+5622888881", latitud = -33.4500, longitud = -70.6500, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 8, nombre = "Sanitos Kids (Cachorros)", comuna = "Maipú", direccion = "Parque Alegría 20", telefono = "+5622888882", latitud = -33.4650, longitud = -70.6750, horario = "L-S 10-18h"),
            Sucursal(veterinariaId = 8, nombre = "Sanitos Móvil", comuna = "El Bosque", direccion = "Ruta 5, Km 30", telefono = "+5622888883", latitud = -33.4650, longitud = -70.6950, horario = "L-V 9-17h"),
            Sucursal(veterinariaId = 8, nombre = "Sanitos Felinos", comuna = "Cerrillos", direccion = "Pasaje Gatos 40", telefono = "+5622888884", latitud = -33.4350, longitud = -70.6150, horario = "L-V 11-19h"),
            Sucursal(veterinariaId = 9, nombre = "Best Friends Alameda", comuna = "Lo Espejo", direccion = "Av. Principal 2000", telefono = "+5622999991", latitud = -33.4470, longitud = -70.6570, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 9, nombre = "Best Friends Acuatic", comuna = "La Cisterna", direccion = "Muelle 30", telefono = "+5622999992", latitud = -33.4300, longitud = -70.6200, horario = "L-V 10-17h"),
            Sucursal(veterinariaId = 9, nombre = "Best Friends Boutique", comuna = "Maipú", direccion = "Calle Lujo 400", telefono = "+5622999993", latitud = -33.4380, longitud = -70.6080, horario = "L-S 11-20h"),
            Sucursal(veterinariaId = 10, nombre = "Cuidado Total 24/7", comuna = "El Bosque", direccion = "Siempre Abierto 1", telefono = "+5622000001", latitud = -33.4400, longitud = -70.6500, horario = "24/7"),
            Sucursal(veterinariaId = 10, nombre = "Cuidado Total Exóticos", comuna = "Cerrillos", direccion = "Calle Rara 55", telefono = "+5622000002", latitud = -33.4200, longitud = -70.6300, horario = "L-V 9-18h"),
            Sucursal(veterinariaId = 10, nombre = "Cuidado Total Poniente", comuna = "Lo Espejo", direccion = "Av. Oeste 999", telefono = "+5622000003", latitud = -33.4600, longitud = -70.6900, horario = "L-S 9-17h")
        )
    }


    fun getUsuarios(): List<Usuario> {
        return listOf(
            Usuario(nombre = "Usuario de Prueba", correo = "usuario@helpet.cl", telefono = "+56912345678", foto = null, estado = true)
        )
    }

    fun getMascotas(): List<Mascota> {
        return listOf(
            Mascota(usuarioId = 1, nombre = "Kiara", especie = "Perro", sexo = "Hembra", raza = "Mestiza", fechaNacimiento = "2018-05-10", foto = null, activo = true),
            Mascota(usuarioId = 1, nombre = "Chuco", especie = "Gato", sexo = "Macho", raza = "Gacu", fechaNacimiento = "2020-11-01", foto = null, activo = false)
        )
    }

    fun getServicios(): List<Servicio> {
        return listOf(
            Servicio(nombre = "Consulta General", descripcion = "Chequeo de rutina", precio = 15000, duracionMinutos = 30),
            Servicio(nombre = "Vacunación", descripcion = "Vacuna antirrábica", precio = 10000, duracionMinutos = 15),
            Servicio(nombre = "Desparasitación", descripcion = "Interna y externa", precio = 8000, duracionMinutos = 15)
        )
    }

    fun getProfesionales(): List<Profesional> {
        return listOf(
            Profesional(sucursalId = 1, nombre = "Dr. Juan Pérez", especialidad = "Medicina General"),
            Profesional(sucursalId = 4, nombre = "Dra. Ana Gómez", especialidad = "Cirugía")
        )
    }

    fun getReservas(): List<Reserva> {
        return listOf(
            Reserva(
                usuarioId = 1,
                mascotaId = 1,
                sucursalId = 1,
                servicioId = 1,
                profesionalId = 1,
                fechaHora = "2025-10-30 10:00",
                estado = "CONFIRMADA",
                comentarios = "Chequeo anual"
            )
        )
    }

    fun getPromociones(): List<PromocionUsuario> {
        return listOf(
            PromocionUsuario(
                usuarioId = 1,
                veterinariaId = 1,
                titulo = "20% Descuento en Vacunas",
                descripcion = "Presenta este cupón en tu próxima visita.",
                fechaExpiracion = "2025-12-31",
                estado = "VIGENTE"
            )
        )
    }
}