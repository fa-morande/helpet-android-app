package com.helpetuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helpetuser.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(usuarios: List<Usuario>)
    @Query("SELECT * FROM usuarios")
    fun getAll(): Flow<List<Usuario>>
    @Query("SELECT * FROM usuarios WHERE id = :usuarioId")
    fun getById(usuarioId: Int): Flow<Usuario>
}