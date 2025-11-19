package com.helpetuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.helpetuser.data.local.dao.*
import com.helpetuser.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Veterinaria::class, Sucursal::class, Usuario::class, Mascota::class,
        Servicio::class, Profesional::class, Reserva::class, PromocionUsuario::class
    ],
    version = 2 // <--- CAMBIO A VERSIÓN 2
)
abstract class HelpetDatabase : RoomDatabase() {

    abstract fun veterinariaDao(): VeterinariaDao
    abstract fun sucursalDao(): SucursalDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun mascotaDao(): MascotaDao
    abstract fun reservaDao(): ReservaDao
    abstract fun promocionUsuarioDao(): PromocionUsuarioDao
    abstract fun servicioDao(): ServicioDao
    abstract fun profesionalDao(): ProfesionalDao

    companion object {
        @Volatile private var INSTANCE: HelpetDatabase? = null

        fun getDatabase(context: Context): HelpetDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HelpetDatabase::class.java,
                    "helpet_db"
                )
                    .fallbackToDestructiveMigration() // <--- IMPORTANTE: Permite recrear la BD
                    .addCallback(HelpetDatabaseCallback(context))
                    .build()
                    .also { INSTANCE = it }
            }
        }

        private class HelpetDatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        database.veterinariaDao().insertAll(Data.getVeterinarias())
                        database.usuarioDao().insertAll(Data.getUsuarios())
                        database.servicioDao().insertAll(Data.getServicios())
                        database.sucursalDao().insertAll(Data.getSucursales())
                        database.mascotaDao().insertAll(Data.getMascotas())
                        database.promocionUsuarioDao().insertAll(Data.getPromociones())
                        database.profesionalDao().insertAll(Data.getProfesionales())
                        database.reservaDao().insertAll(Data.getReservas())
                    }
                }
            }
        }
    }
}