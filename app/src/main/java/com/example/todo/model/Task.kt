package com.example.todo.model

data class Task(
    val description: String,
    val date: String,
    val createdAt: String,
    var isDone: Boolean = false
)
