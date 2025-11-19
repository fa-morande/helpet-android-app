package com.helpetuser.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val telefono: String,
    val contrasena: String,
    val foto: String? = null,
    val estado: Boolean = true
)