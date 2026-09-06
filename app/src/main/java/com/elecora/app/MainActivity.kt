package com.elecora.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ====================================================
// ELECORA COLORS
// ====================================================

val Navy = Color(0xFF06182B)
val Navy2 = Color(0xFF0A2340)
val CardBlue = Color(0xFF0D3155)
val ElectricBlue = Color(0xFF19BFFF)
val Cyan = Color(0xFF20D9FF)
val Green = Color(0xFF18D99B)
val Orange = Color(0xFFFFB31A)
val Purple = Color(0xFF8B5CF6)
val White = Color(0xFFF5FAFF)
val Gray = Color(0xFF9FB3C8)

// ====================================================
// MAIN ACTIVITY
// ====================================================

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ElecoraApp()
        }
    }
}

// ====================================================
// APP
// ====================================================

@Composable
fun ElecoraApp() {

    var currentPage by remember { mutableStateOf("Accueil") }
    var selectedTool by remember { mutableStateOf<String?>(null) }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Navy
        ) {

            if (selectedTool != null) {

                ToolScreen(
                    tool = selectedTool!!,
                    onBack = {
                        selectedTool = null
                    }
                )

            } else {

                Scaffold(
                    containerColor = Navy,

                    bottomBar = {

                        BottomNavigationBar(
                            selected = currentPage,
                            onSelected = { page ->
                                currentPage = page
                            }
                        )
                    }

                ) { padding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {

                        when (currentPage) {

                            "Accueil" -> {
                                HomeScreen(
                                    onToolClick = { tool ->
                                        selectedTool = tool
