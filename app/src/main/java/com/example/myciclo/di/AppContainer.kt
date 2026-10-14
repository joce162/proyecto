package com.example.myciclo.di

import android.content.Context
import com.example.myciclo.data.repository.SesionRepository
import com.example.myciclo.data.repository.UsuarioRepository
import com.example.myciclo.data.room.AppDatabase

class AppContainer(context: Context) {
    private val database = AppDatabase.getInstance(context)

    val usuarioRepository = UsuarioRepository(database.usuarioDao())
    val sesionRepository = SesionRepository()
}
