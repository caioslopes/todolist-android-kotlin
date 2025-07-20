package com.example.todo.data

import com.example.todo.model.Task

object MockTaskRepository {

    fun getMockTasks(): List<Task> {
        return listOf(
            Task(
                description = "Comprar pão",
                date = "2025-07-22",
                createdAt = "2025-07-19"
            ),
            Task(
                description = "Estudar Kotlin",
                date = "2025-07-23",
                createdAt = "2025-07-19"
            )
        )
    }
}