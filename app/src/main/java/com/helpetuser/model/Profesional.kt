package com.helpetuser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profesionales")
data class Profesional(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sucursalId: Int,
    val nombre: String,
    val especialidad: String
)