package com.terminalcyber.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.terminalcyber.app.ui.theme.TerminalCyberTheme

class TerminalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerminalCyberTheme {
                TerminalScreen { finish() }
            }
        }
    }
}

@Composable
fun TerminalScreen(onBackPressed: () -> Unit) {
    var commandHistory by remember { mutableStateOf<List<TerminalCommand>>(emptyList()) }
    var currentInput by remember { mutableStateOf("") }
    var isExecuting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
    ) {
        // Top Bar
        TopAppBar(
            title = {
                Text(
                    "Terminal",
                    color = Color(0xFF00FF00),
                    fontSize = 18.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF00BCD4)
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        commandHistory = emptyList()
                        currentInput = ""
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Clear",
                        tint = Color(0xFF00BCD4)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF1F1F1F)
            )
        )

        // Terminal Output
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
                .background(Color(0xFF0D0D0D))
        ) {
            for (command in commandHistory) {
                TerminalOutput(command)
            }
        }

        Divider(color = Color(0xFF2D2D2D))

        // Input Area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = currentInput,
                onValueChange = { currentInput = it },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                placeholder = {
                    Text(
                        "$ ",
                        color = Color(0xFF00FF00),
                        fontSize = 12.sp
                    )
                },
                textStyle = androidx.compose.material3.LocalTextStyle.current.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = Color(0xFF00FF00)
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1F1F1F),
                    unfocusedContainerColor = Color(0xFF1F1F1F),
                    focusedTextColor = Color(0xFF00FF00),
                    unfocusedTextColor = Color(0xFF00FF00),
                    focusedIndicatorColor = Color(0xFF00BCD4),
                    unfocusedIndicatorColor = Color(0xFF2D2D2D)
                )
            )

            Button(
                onClick = {
                    if (currentInput.isNotEmpty()) {
                        val command = TerminalCommand(
                            input = currentInput,
                            output = executeCommand(currentInput),
                            isError = false
                        )
                        commandHistory = commandHistory + command
                        currentInput = ""
                    }
                },
                enabled = !isExecuting,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00BCD4)
                )
            ) {
                Text(
                    "Execute",
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun TerminalOutput(command: TerminalCommand) {
    Text(
        "$ ${command.input}",
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace,
        color = Color(0xFF00FF00),
        modifier = Modifier.padding(vertical = 4.dp)
    )
    Text(
        command.output,
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace,
        color = if (command.isError) Color(0xFFFF5252) else Color(0xFF80DEEA),
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

data class TerminalCommand(
    val input: String,
    val output: String,
    val isError: Boolean
)

fun executeCommand(command: String): String {
    return when {
        command.startsWith("ls") -> "file1.txt\nfile2.txt\ndirectory/\napp.apk"
        command.startsWith("pwd") -> "/data/user/0/com.terminalcyber.app"
        command.startsWith("echo") -> command.removePrefix("echo ").trim()
        command.startsWith("date") -> java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(java.util.Date())
        command.startsWith("whoami") -> "terminalcyber"
        command.startsWith("uname") -> "Linux localhost 5.10.0 #1 SMP PREEMPT Android"
        command.startsWith("help") -> "Available commands: ls, pwd, echo, date, whoami, uname, help\nType 'command --help' for more info"
        command.startsWith("clear") -> ""
        else -> "Command not found: $command\nType 'help' for available commands"
    }
}
