package com.example.myciclo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myciclo.data.repository.SesionRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InicioUiState(
    val nombreUsuario: String = "Ana",
    val diaCiclo: Int = 29,
    val totalDiasCiclo: Int = 28,
    val fechaInicio: String = "1 sep 2026",
    val faseCiclo: String = "fase lútea",
    val mostrarDialogoSalir: Boolean = false
)

sealed interface InicioEvento {
    data object SesionCerrada : InicioEvento
}

class InicioViewModel(
    private val sesionRepository: SesionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InicioUiState())
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()

    private val _eventos = Channel<InicioEvento>(Channel.BUFFERED)
    val eventos: Flow<InicioEvento> = _eventos.receiveAsFlow()

    init {
        viewModelScope.launch {
            sesionRepository.emailActual.collect { email ->
                if (email != null) {
                    val nombre = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    _uiState.update { it.copy(nombreUsuario = if (nombre.isBlank()) "Ana" else nombre) }
                }
            }
        }
    }

    fun pedirCerrarSesion() {
        _uiState.update { it.copy(mostrarDialogoSalir = true) }
    }

    fun cancelarCerrarSesion() {
        _uiState.update { it.copy(mostrarDialogoSalir = false) }
    }

    fun confirmarCerrarSesion() {
        _uiState.update { it.copy(mostrarDialogoSalir = false) }
        sesionRepository.cerrar()
        viewModelScope.launch {
            _eventos.send(InicioEvento.SesionCerrada)
        }
    }
}
