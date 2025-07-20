package com.example.todo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.model.Task
import com.example.todo.ui.components.TaskItem

@Composable
fun CompletedTasksScreen(
    tasks: List<Task>,
    onCheckedChange: (Task, Boolean) -> Unit,
    onDelete: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    var taskToDelete by remember { mutableStateOf<Task?>(null) }


    Box(modifier = modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Logo do aplicativo",
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally)
            )

            if (tasks.isEmpty()) {
                Text("Nenhuma tarefa concluída ainda!")
            } else {
                LazyColumn (
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tasks, key = { it.hashCode() }) { task ->
                        TaskItem(
                            task = task,
                            onCheckedChange = { isChecked -> onCheckedChange(task, isChecked) },
                            onDeleteClick = { taskToDelete = task },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }

            taskToDelete?.let { task ->
                AlertDialog(
                    onDismissRequest = { taskToDelete = null },
                    title = { Text("Excluir Tarefa") },
                    text = { Text("Tem certeza que deseja excluir esta tarefa?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                onDelete(task)
                                taskToDelete = null
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Excluir")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { taskToDelete = null }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
