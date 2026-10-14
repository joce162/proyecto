package com.example.myciclo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myciclo.data.repository.SesionRepository
import com.example.myciclo.data.repository.UsuarioRepository
import com.example.myciclo.utils.MIN_PASSWORD
import com.example.myciclo.utils.esEmailValido
import com.example.myciclo.utils.esPasswordValida
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val usuario: String = "",
    val password: String = "",
    val error: String = ""
) {
    val passwordValida: Boolean get() = esPasswordValida(password)
}

sealed interface LoginEvento {
    data class LoginExitoso(val email: String) : LoginEvento
}

class LoginViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val sesionRepository: SesionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _eventos = Channel<LoginEvento>(Channel.BUFFERED)
    val eventos: Flow<LoginEvento> = _eventos.receiveAsFlow()

    fun onUsuarioChange(valor: String) {
        _uiState.update { it.copy(usuario = valor) }
    }

    fun onPasswordChange(valor: String) {
        _uiState.update { it.copy(password = valor) }
    }

    fun ingresar() {
        val actual = _uiState.value

        val mensajeError: String? = when {
            actual.usuario.isBlank() || actual.password.isBlank() ->
                "Debe ingresar usuario y contraseña"
            !esEmailValido(actual.usuario) ->
                "El formato de email NO es válido"
            !actual.passwordValida ->
                "La contraseña debe tener al menos $MIN_PASSWORD caracteres."
            else -> null
        }
        if (mensajeError != null) {
            _uiState.update { it.copy(error = mensajeError) }
            return
        }

        viewModelScope.launch {
            val email = actual.usuario.trim()
            if (usuarioRepository.autenticar(email, actual.password)) {
                sesionRepository.iniciar(email)
                _uiState.update { it.copy(error = "") }
                _eventos.send(LoginEvento.LoginExitoso(email))
            } else {
                _uiState.update { it.copy(error = "Usuario y/o Contraseña incorrectos") }
            }
        }
    }
}
