package com.example.todo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.model.Task
import com.example.todo.ui.components.TaskCreationSheet
import com.example.todo.ui.components.TaskItem
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingTasksScreen(
    tasks: List<Task>,
    onCheckedChange: (Task, Boolean) -> Unit,
    onDelete: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var taskToDelete by remember { mutableStateOf<Task?>(null) }

    val today = remember { LocalDate.now() }
    val next7Days = remember {
        List(7) { today.plusDays(it.toLong()) }
    }

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val filteredTasks = selectedDate?.let {
        tasks.filter { LocalDate.parse(it.date) == selectedDate }
    } ?: tasks

    Box(modifier = modifier.fillMaxSize()) {
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

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    val isSelected = selectedDate == null
                    Button(
                        onClick = { selectedDate = null },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Todas",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                items(next7Days) { date ->
                    val isSelected = date == selectedDate
                    Button(
                        onClick = { selectedDate = date },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("pt", "BR")),
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredTasks.isEmpty()) {
                Text("Nenhuma tarefa pendente!")
            } else {
                LazyColumn (
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredTasks, key = { it.hashCode() }) { task ->
                        TaskItem(
                            task = task,
                            onCheckedChange = { isChecked -> onCheckedChange(task, isChecked) },
                            onDeleteClick = { taskToDelete = task },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showBottomSheet = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Nova tarefa")
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                TaskCreationSheet(
                    onSave = { newTask ->
                        onCheckedChange(newTask, false)
                        showBottomSheet = false
                    }
                )
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
