package com.helpetuser.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helpetuser.model.Mascota
import kotlinx.coroutines.flow.Flow

@Dao
interface MascotaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mascota: Mascota)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mascotas: List<Mascota>)
    @Query("SELECT * FROM mascotas WHERE usuarioId = :usuarioId")
    fun getByUsuarioId(usuarioId: Int): Flow<List<Mascota>>
    @Query("SELECT * FROM mascotas WHERE id = :mascotaId LIMIT 1")
    fun getById(mascotaId: Int): Flow<Mascota?>
    @Delete
    suspend fun delete(mascota: Mascota)
}