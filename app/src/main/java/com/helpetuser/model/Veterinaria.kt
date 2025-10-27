package com.helpetuser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "veterinarias")
data class Veterinaria(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val rut: String,
    val sitioWeb: String? = null,
    val logoUrl: String? = null
)