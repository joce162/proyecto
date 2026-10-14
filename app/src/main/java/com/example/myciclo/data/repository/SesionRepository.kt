package com.example.myciclo.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SesionRepository {
    private val _emailActual = MutableStateFlow<String?>(null)
    val emailActual: StateFlow<String?> = _emailActual.asStateFlow()

    fun iniciar(email: String) {
        _emailActual.value = email
    }

    fun cerrar() {
        _emailActual.value = null
    }
}
