package com.example.todo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.todo.model.Task
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreationSheet(
    onSave: (Task) -> Unit
) {
    var description by remember { mutableStateOf(TextFieldValue("")) }

    val datePickerState = rememberDatePickerState()

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        Text("Nova Tarefa", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descrição da Tarefa") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        DatePicker(
            state = datePickerState,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val dateString = datePickerState.selectedDateMillis?.let { millis ->
                    Instant.ofEpochMilli(millis)
                        .plusSeconds(3 * 60 * 60)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                } ?: "Sem data"
                val now = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                val newTask = Task(
                    description = description.text,
                    date = dateString,
                    createdAt = now
                )

                onSave(newTask)
            },
            enabled = description.text.isNotBlank()
        ) {
            Text("Salvar Tarefa")
        }
    }
}
