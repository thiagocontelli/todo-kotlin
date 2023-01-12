package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.ToDoTheme
import java.util.*

data class Task(val id: UUID, val description: String, var isSelected: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                ToDoScreen()
            }
        }
    }
}

@Composable
fun ToDoScreen() {
    val tasks = remember { mutableStateListOf<Task>() }
    var task by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.text_title),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.padding(16.dp))
        EditTaskField(task) { if (it.length <= 100) task = it }
        Spacer(Modifier.padding(8.dp))
        Button(
            {
                if (task.trim() !== "") {
                    val id = UUID.randomUUID()
                    val newTask = Task(id, task, false)
                    tasks.add(newTask)
                    task = ""
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Task")
        }
        Spacer(Modifier.padding(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            itemsIndexed(tasks) { index, item ->
                TaskCard(item.description, selected = item.isSelected, onClick = {
                    val taskToBeRemoved = tasks.find { it.id == item.id }
                    tasks.remove(taskToBeRemoved)
                }, onSelect = {
                    val newTask = Task(item.id, item.description, it)
                    tasks[index] = newTask
                })
            }

        }

    }
}

@Composable()
fun EditTaskField(
    value: String, onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.text_placeholder)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
    Row(
        horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
    ) { Text(value.length.toString() + "/100", fontSize = 14.sp) }
}

@Composable
fun TaskCard(
    task: String, onClick: () -> Unit, selected: Boolean, onSelect: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = if (selected) Color.LightGray else Color.White,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(0.8f)
            ) {
                Checkbox(selected, onSelect)
                Text(
                    task,
                    fontSize = 16.sp,
                    style = if (selected) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle(
                        textDecoration = TextDecoration.None
                    ),
                )
            }
            IconButton(
                onClick, modifier = Modifier.weight(0.2f)
            ) {
                Icon(
                    painterResource(R.drawable.ic_trash), null, tint = Color(0xFFBE1F1F)
                )
            }
        }

    }
}