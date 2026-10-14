package com.example.myciclo.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey val email: String,
    val password: String
)
