package com.terminalcyber.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.terminalcyber.app.ui.AIAssistantActivity
import com.terminalcyber.app.ui.PackageManagerActivity
import com.terminalcyber.app.ui.TerminalActivity
import com.terminalcyber.app.ui.theme.TerminalCyberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerminalCyberTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0D0D0D)
                ) {
                    MainScreen { navigateTo(it) }
                }
            }
        }
    }

    private fun navigateTo(destination: String) {
        val intent = when (destination) {
            "terminal" -> Intent(this, TerminalActivity::class.java)
            "ai" -> Intent(this, AIAssistantActivity::class.java)
            "packages" -> Intent(this, PackageManagerActivity::class.java)
            else -> return
        }
        startActivity(intent)
    }
}

@Composable
fun MainScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(
                "TerminalCyber",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00FF00)
            )
            Text(
                "Advanced Terminal & AI Assistant",
                fontSize = 14.sp,
                color = Color(0xFF80DEEA),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Feature Cards
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureCard(
                icon = Icons.Default.Terminal,
                title = "Terminal",
                description = "Linux-like terminal interface",
                onClick = { onNavigate("terminal") }
            )
            FeatureCard(
                icon = Icons.Default.SmartToy,
                title = "AI Assistant",
                description = "Smart AI chat & code generation",
                onClick = { onNavigate("ai") }
            )
            FeatureCard(
                icon = Icons.Default.Storage,
                title = "Package Manager",
                description = "Download & manage tools",
                onClick = { onNavigate("packages") }
            )
        }
    }
}

@Composable
fun FeatureCard(
    icon: androidx.compose.material.icons.materialIcon,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1F1F1F)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF00BCD4),
                modifier = Modifier.size(32.dp)
            )
            Column {
                Text(
                    title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00FF00)
                )
                Text(
                    description,
                    fontSize = 12.sp,
                    color = Color(0xFF80DEEA)
                )
            }
        }
    }
}
