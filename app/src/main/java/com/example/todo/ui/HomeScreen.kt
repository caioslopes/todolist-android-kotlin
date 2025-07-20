package com.example.todo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.data.MockTaskRepository
import com.example.todo.model.Task
import com.example.todo.ui.components.TaskCreationSheet
import com.example.todo.ui.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val tasks = remember { mutableStateListOf<Task>().apply { addAll(MockTaskRepository.getMockTasks()) } }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Tarefas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheet = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Nova tarefa")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (tasks.isEmpty()) {
                    Text("Nenhuma tarefa ainda. Adicione uma nova!")
                } else {
                    LazyColumn {
                        items(tasks) { task ->
                            TaskItem(
                                task = task,
                                onCheckedChange = { isChecked ->
                                    task.isDone = isChecked

                                    val index = tasks.indexOf(task)
                                    tasks.removeAt(index)

                                    if (isChecked) {
                                        tasks.add(task)
                                    } else {
                                        tasks.add(0, task)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    TaskCreationSheet(
                        onSave = { newTask ->
                            tasks.add(newTask)
                            showBottomSheet = false
                        }
                    )
                }
            }
        }
    )
}
