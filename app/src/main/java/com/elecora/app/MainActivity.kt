package com.elecora.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ElecoraApp()
        }
    }
}

data class ToolItem(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElecoraApp() {

    var selectedTool by remember { mutableStateOf<String?>(null) }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F7FA)
        ) {

            if (selectedTool == null) {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ElectricalServices,
                                        contentDescription = null
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        text = "Elecora",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        )
                    }
                ) { padding ->

                    HomeScreen(
                        modifier = Modifier.padding(padding),
                        onToolClick = { selectedTool = it }
                    )
                }

            } else {

                ToolScreen(
                    title = selectedTool!!,
                    onBack = { selectedTool = null }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onToolClick: (String) -> Unit
) {

    val tools = listOf(
        ToolItem(
            "Section de câble",
            "Dimensionner la section du conducteur.",
            Icons.Default.Calculate
        ),
        ToolItem(
            "Chute de tension",
            "Calculer la chute de tension d'une ligne.",
            Icons.Default.ElectricalServices
        ),
        ToolItem(
            "Calibre disjoncteur",
            "Choisir une protection adaptée.",
            Icons.Default.Security
        ),
        ToolItem(
            "Bilan de puissance",
            "Calculer la puissance totale d'une installation.",
            Icons.Default.Home
        ),
        ToolItem(
            "Moteurs électriques",
            "Aide au dimensionnement des circuits moteurs.",
            Icons.Default.Engineering
        ),
        ToolItem(
            "Analyser un plan PDF",
            "Préparer l'analyse intelligente d'un plan électrique.",
            Icons.Default.PictureAsPdf
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF1565C0),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp)
            ) {

                Column {

                    Text(
                        text = "⚡ Elecora",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Assistant technique • Électricité bâtiment",
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Dimensionnez vos installations plus simplement.",
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Outils",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))
        }

        items(tools) { tool ->

            ToolCard(
                tool = tool,
                onClick = {
                    onToolClick(tool.title)
                }
            )
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp)
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF1565C0)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "À propos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Elecora est un outil d'aide au pré-dimensionnement des installations électriques du bâtiment."
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "⚠️ Les résultats doivent être vérifiés selon les normes et les conditions réelles de l'installation.",
                        color = Color(0xFF8A5A00),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Elecora • Version 1.0",
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ToolCard(
    tool: ToolItem,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = Color(0xFFE3F2FD),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = tool.icon,
                    contentDescription = null,
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = tool.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = tool.description,
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onClick
            ) {
                Text("Ouvrir")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolScreen(
    title: String,
    onBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(title)
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Module Elecora",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Retour à l'accueil")
            }
        }
    }
}
