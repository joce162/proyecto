package com.example.myciclo.utils

private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

const val MIN_PASSWORD = 6

fun esEmailValido(email: String): Boolean {
    return EMAIL_REGEX.matches(email.trim())
}

fun esPasswordValida(password: String): Boolean {
    return password.length >= MIN_PASSWORD
}
