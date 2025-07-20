package com.example.todo.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todo.model.Task

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = onCheckedChange,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = task.description,
                        style = if (task.isDone)
                            MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough)
                        else
                            MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Data: ${task.date}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Criada em: ${task.createdAt}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}