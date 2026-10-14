package com.example.myciclo.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.myciclo.utils.MIN_PASSWORD

@Composable
fun CampoPassword(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Contraseña"
) {
    val esValida = value.length >= MIN_PASSWORD

    val verde = if (isSystemInDarkTheme()) Color(0xFF66BB6A) else Color(0xFF2E7D32)
    val rojo = MaterialTheme.colorScheme.error

    val colorActual by animateColorAsState(
        targetValue = if (esValida) verde else rojo,
        animationSpec = tween(durationMillis = 300),
        label = "colorPassword"
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        supportingText = {
            Text(
                if (esValida) "${value.length}/$MIN_PASSWORD caracteres ✓"
                else "${value.length}/$MIN_PASSWORD caracteres"
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorActual,
            unfocusedBorderColor = colorActual,
            focusedLabelColor = colorActual,
            unfocusedLabelColor = colorActual,
            cursorColor = colorActual,
            focusedSupportingTextColor = colorActual,
            unfocusedSupportingTextColor = colorActual
        ),
        modifier = modifier
    )
}
