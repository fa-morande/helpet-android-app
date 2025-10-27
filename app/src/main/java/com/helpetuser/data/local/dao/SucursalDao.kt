package com.helpetuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helpetuser.model.Sucursal
import kotlinx.coroutines.flow.Flow

@Dao
interface SucursalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sucursal: Sucursal)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sucursales: List<Sucursal>)
    @Query("SELECT * FROM sucursales ORDER BY id ASC")
    fun getAll(): Flow<List<Sucursal>>
    @Query("SELECT * FROM sucursales WHERE id = :sucursalId LIMIT 1")
    fun getById(sucursalId: Int): Flow<Sucursal?>
    @Query("SELECT * FROM sucursales WHERE id IN (:sucursalIds)")
    fun getByIds(sucursalIds: List<Int>): Flow<List<Sucursal>>
}