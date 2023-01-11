package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.ToDoTheme
import java.util.UUID

data class Task(val id: UUID, val description: String)

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
    var tasks by remember { mutableStateListOf<Task>() }
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
        EditTaskField(task, { task = it })
        Spacer(Modifier.padding(8.dp))
        Button(
            {
                if (task.trim() !== "") {
                    val id = UUID.randomUUID()
                    val newTask = Task(id, task)
                    tasks.add(newTask)
                    println(tasks)
                }
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Task")
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}