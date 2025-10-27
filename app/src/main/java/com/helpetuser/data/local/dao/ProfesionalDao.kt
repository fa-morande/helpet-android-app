package com.helpetuser.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.helpetuser.model.Profesional

@Dao
interface ProfesionalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(profesionales: List<Profesional>)
}