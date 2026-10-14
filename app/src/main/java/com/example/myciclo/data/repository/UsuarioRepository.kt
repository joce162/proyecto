package com.example.myciclo.data.repository

import com.example.myciclo.data.room.UsuarioDao
import com.example.myciclo.data.room.UsuarioEntity

class UsuarioRepository(private val dao: UsuarioDao) {
    suspend fun registrar(email: String, password: String): Boolean {
        return dao.insertar(UsuarioEntity(email, password)) != -1L
    }

    suspend fun autenticar(email: String, password: String): Boolean {
        val usuario = dao.obtenerPorEmail(email) ?: return false
        return usuario.password == password
    }
}
