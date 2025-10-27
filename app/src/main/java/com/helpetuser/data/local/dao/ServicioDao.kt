package com.helpetuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helpetuser.model.Servicio
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(servicios: List<Servicio>)
    @Query("SELECT * FROM servicios ORDER BY nombre ASC")
    fun getAll(): Flow<List<Servicio>>
}