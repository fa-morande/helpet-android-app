package com.helpetuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helpetuser.model.PromocionUsuario
import kotlinx.coroutines.flow.Flow

@Dao
interface PromocionUsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(promociones: List<PromocionUsuario>)
    @Query("SELECT * FROM promociones_usuario WHERE usuarioId = :usuarioId")
    fun getByUsuarioId(usuarioId: Int): Flow<List<PromocionUsuario>>
}