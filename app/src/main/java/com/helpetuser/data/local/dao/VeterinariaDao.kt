package com.helpetuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helpetuser.model.Veterinaria
import kotlinx.coroutines.flow.Flow

@Dao
interface VeterinariaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(veterinaria: Veterinaria)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(veterinarias: List<Veterinaria>)
    @Query("SELECT * FROM veterinarias ORDER BY id ASC")
    fun getAll(): Flow<List<Veterinaria>>
    @Query("SELECT * FROM veterinarias WHERE id = :veterinariaId LIMIT 1")
    fun getById(veterinariaId: Int): Flow<Veterinaria?>
    @Query("SELECT * FROM veterinarias WHERE id IN (:veterinariaIds)")
    fun getByIds(veterinariaIds: List<Int>): Flow<List<Veterinaria>>
}