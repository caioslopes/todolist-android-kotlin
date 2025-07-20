package com.example.todo.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.formatToBrazilianDate(inputPattern: String = "yyyy-MM-dd"): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern(inputPattern, Locale("pt", "BR"))
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))
        LocalDate.parse(this, inputFormatter).format(outputFormatter)
    } catch (e: Exception) {
        this
    }
}
