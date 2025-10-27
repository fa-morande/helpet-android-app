package com.helpetuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helpetuser.model.Reserva
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reserva: Reserva)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reservas: List<Reserva>)
    @Query("SELECT * FROM reservas WHERE mascotaId = :mascotaId ORDER BY fechaHora DESC LIMIT 1")
    fun getProximaByMascotaId(mascotaId: Int): Flow<Reserva?>
    @Query("SELECT * FROM reservas")
    fun getAll(): Flow<List<Reserva>>
    @Query("SELECT * FROM reservas WHERE usuarioId = :usuarioId ORDER BY fechaHora DESC")
    fun getHistorialByUsuarioId(usuarioId: Int): Flow<List<Reserva>>
}