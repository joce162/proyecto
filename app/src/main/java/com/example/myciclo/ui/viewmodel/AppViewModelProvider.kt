package com.example.myciclo.ui.viewmodel

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myciclo.MyApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(
                usuarioRepository = miApp().container.usuarioRepository,
                sesionRepository = miApp().container.sesionRepository
            )
        }
        initializer {
            RegistroViewModel(
                usuarioRepository = miApp().container.usuarioRepository
            )
        }
        initializer {
            InicioViewModel(
                sesionRepository = miApp().container.sesionRepository
            )
        }
    }
}

private fun CreationExtras.miApp(): MyApplication =
    this[APPLICATION_KEY] as MyApplication
