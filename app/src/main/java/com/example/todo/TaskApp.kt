package com.example.todo

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.todo.model.Task
import com.example.todo.data.MockTaskRepository
import com.example.todo.ui.screens.CompletedTasksScreen
import com.example.todo.ui.screens.PendingTasksScreen

enum class TaskTab(val label: String, val icon: ImageVector) {
    Pending("Pendentes", Icons.Default.List),
    Completed("Concluídas", Icons.Default.CheckCircle)
}

@Composable
fun TaskApp() {
    var selectedTab by remember { mutableStateOf(TaskTab.Pending) }
    val tasks = remember { mutableStateListOf<Task>().apply { addAll(MockTaskRepository.getMockTasks()) } }

    fun deleteTask(task: Task) {
        tasks.remove(task)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                TaskTab.entries.forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab }
                    )
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            TaskTab.Pending -> PendingTasksScreen(
                tasks = tasks.filter { !it.isDone },
                onCheckedChange = { task, checked ->
                    task.isDone = checked
                    tasks.remove(task)
                    tasks.add(if (checked) tasks.size else 0, task)
                },
                onDelete = { deleteTask(it) },
                modifier = Modifier.padding(padding)
            )

            TaskTab.Completed -> CompletedTasksScreen(
                tasks = tasks.filter { it.isDone },
                onCheckedChange = { task, checked ->
                    task.isDone = checked
                    tasks.remove(task)
                    tasks.add(if (checked) tasks.size else 0, task)
                },
                onDelete = { deleteTask(it) },
                modifier = Modifier.padding(padding)
            )
        }
    }
}
