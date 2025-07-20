package com.example.todo.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todo.model.Task
import com.example.todo.utils.formatToBrazilianDate

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        //elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
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
                        text = "Fazer em: ${task.date.formatToBrazilianDate()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Criada em: ${task.createdAt.formatToBrazilianDate()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Excluir",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
