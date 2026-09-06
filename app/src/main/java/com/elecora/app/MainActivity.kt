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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ----------------------------------------------------
// ELECORA COLORS
// ----------------------------------------------------

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

// ----------------------------------------------------
// MAIN ACTIVITY
// ----------------------------------------------------

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ElecoraApp()
        }
    }
}

// ----------------------------------------------------
// APP
// ----------------------------------------------------

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
                            onSelected = {
                                currentPage = it
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

                            "Accueil" -> HomeScreen(
                                onToolClick = {
                                    selectedTool = it
                                }
                            )

                            "Outils" -> ToolsScreen(
                                onToolClick = {
                                    selectedTool = it
                                }
                            )

                            "Historique" -> HistoryScreen()

                            "Profil" -> ProfileScreen()
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// HOME
// ----------------------------------------------------

@Composable
fun HomeScreen(
    onToolClick: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "⚡",
                            fontSize = 30.sp
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Elecora",
                            color = White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Assistant professionnel",
                        color = Gray,
                        fontSize = 13.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            CardBlue,
                            RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "🔔",
                        fontSize = 19.sp
                    )
                }
            }
        }

        item {

            Spacer(modifier = Modifier.height(4.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Navy2
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Bonjour,",
                        color = Gray,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Technicien électricien 👋",
                        color = White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Des outils précis pour vos installations électriques.",
                        color = Gray,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(115.dp)
                            .background(
                                Color(0xFF0B2C4B),
                                RoundedCornerShape(18.dp)
                            )
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(17.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = "L'électricité,",
                                color = White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "une énergie maîtrisée.",
                                color = Cyan,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Calculer • Vérifier • Dimensionner",
                                color = Gray,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        item {

            Text(
                text = "Outils principaux",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ToolCard(
                    modifier = Modifier.weight(1f),
                    icon = "⚡",
                    title = "Chute de tension",
                    description = "Calculer la chute de tension",
                    color = ElectricBlue,
                    onClick = {
                        onToolClick("Chute de tension")
                    }
                )

                ToolCard(
                    modifier = Modifier.weight(1f),
                    icon = "🔌",
                    title = "Section de câble",
                    description = "Choisir la section adaptée",
                    color = Green,
                    onClick = {
                        onToolClick("Section de câble")
                    }
                )
            }
        }

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ToolCard(
                    modifier = Modifier.weight(1f),
                    icon = "🛡️",
                    title = "Calibre disjoncteur",
                    description = "Choisir la protection",
                    color = Orange,
                    onClick = {
                        onToolClick("Calibre disjoncteur")
                    }
                )

                ToolCard(
                    modifier = Modifier.weight(1f),
                    icon = "📄",
                    title = "Analyse PDF",
                    description = "Analyser vos plans",
                    color = Purple,
                    onClick = {
                        onToolClick("Analyse PDF")
                    }
                )
            }
        }

        item {

            Text(
                text = "Outils rapides",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                QuickCard(
                    modifier = Modifier.weight(1f),
                    icon = "🧮",
                    title = "Calculatrice"
                )

                QuickCard(
                    modifier = Modifier.weight(1f),
                    icon = "📚",
                    title = "Formules"
                )

                QuickCard(
                    modifier = Modifier.weight(1f),
                    icon = "📖",
                    title = "Normes"
                )
            }
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF082744)
                )
            ) {

                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "💡",
                        fontSize = 30.sp
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = "Un bon calcul aujourd'hui",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Text(
                            text = "évite un problème demain.",
                            color = Gray,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "⚡ Elecora",
                            color = Cyan,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
