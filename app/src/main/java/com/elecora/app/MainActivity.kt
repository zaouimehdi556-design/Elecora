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
                                    }
                                )
                            }

                            "Outils" -> {
                                ToolsScreen(
                                    onToolClick = { tool ->
                                        selectedTool = tool
                                    }
                                )
                            }

                            "Historique" -> {
                                HistoryScreen()
                            }

                            "Profil" -> {
                                ProfileScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

// ====================================================
// HOME SCREEN
// ====================================================

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
        }

        item {

            Text(
                text = "Elecora • Version 1.0",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color(0xFF607890),
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

// ====================================================
// TOOL CARD
// ====================================================

@Composable
fun ToolCard(
    modifier: Modifier = Modifier,
    icon: String,
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBlue
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(
                        color.copy(alpha = 0.16f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = icon,
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                color = White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = description,
                color = Gray,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Ouvrir →",
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ====================================================
// QUICK CARD
// ====================================================

@Composable
fun QuickCard(
    modifier: Modifier = Modifier,
    icon: String,
    title: String
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Navy2
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = icon,
                fontSize = 23.sp
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = title,
                color = White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ====================================================
// BOTTOM NAVIGATION
// ====================================================

@Composable
fun BottomNavigationBar(
    selected: String,
    onSelected: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        shape = RoundedCornerShape(
            topStart = 22.dp,
            topEnd = 22.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Navy2
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 10.dp
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            NavigationItem(
                icon = "⌂",
                title = "Accueil",
                selected = selected == "Accueil",
                onClick = {
                    onSelected("Accueil")
                }
            )

            NavigationItem(
                icon = "⚙",
                title = "Outils",
                selected = selected == "Outils",
                onClick = {
                    onSelected("Outils")
                }
            )

            NavigationItem(
                icon = "◷",
                title = "Historique",
                selected = selected == "Historique",
                onClick = {
                    onSelected("Historique")
                }
            )

            NavigationItem(
                icon = "●",
                title = "Profil",
                selected = selected == "Profil",
                onClick = {
                    onSelected("Profil")
                }
            )
        }
    }
}

// ====================================================
// NAVIGATION ITEM
// ====================================================

@Composable
fun NavigationItem(
    icon: String,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(
                horizontal = 10.dp,
                vertical = 4.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = icon,
            color = if (selected) Cyan else Gray,
            fontSize = 22.sp
        )

        Text(
            text = title,
            color = if (selected) Cyan else Gray,
            fontSize = 10.sp,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
        )
    }
}

// ====================================================
// TOOLS SCREEN
// ====================================================

@Composable
fun ToolsScreen(
    onToolClick: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Outils",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Tous vos outils techniques",
                color = Gray,
                fontSize = 13.sp
            )
        }

        item {

            ToolCard(
                modifier = Modifier.fillMaxWidth(),
                icon = "⚡",
                title = "Chute de tension",
                description = "Calculer la chute de tension d'une ligne.",
                color = ElectricBlue,
                onClick = {
                    onToolClick("Chute de tension")
                }
            )
        }

        item {

            ToolCard(
                modifier = Modifier.fillMaxWidth(),
                icon = "🔌",
                title = "Section de câble",
                description = "Déterminer la section du conducteur.",
                color = Green,
                onClick = {
                    onToolClick("Section de câble")
                }
            )
        }

        item {

            ToolCard(
                modifier = Modifier.fillMaxWidth(),
                icon = "🛡️",
                title = "Calibre disjoncteur",
                description = "Choisir le calibre de protection adapté.",
                color = Orange,
                onClick = {
                    onToolClick("Calibre disjoncteur")
                }
            )
        }

        item {

            ToolCard(
                modifier = Modifier.fillMaxWidth(),
                icon = "📄",
                title = "Analyse PDF",
                description = "Analyser intelligemment un plan électrique.",
                color = Purple,
                onClick = {
                    onToolClick("Analyse PDF")
                }
            )
        }
    }
}

// ====================================================
// TOOL SCREEN
// ====================================================

@Composable
fun ToolScreen(
    tool: String,
    onBack: () -> Unit
) {

    Scaffold(
        containerColor = Navy
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onBack
                ) {

                    Text(
                        text = "← Retour",
                        color = Cyan,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = tool,
                    color = White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "Outil technique Elecora",
                    color = Gray,
                    fontSize = 13.sp
                )
            }

            item {

                when (tool) {

                    "Chute de tension" -> {
                        VoltageDropContent()
                    }

                    "Section de câble" -> {
                        CableSectionContent()
                    }

                    "Calibre disjoncteur" -> {
                        BreakerContent()
                    }

                    "Analyse PDF" -> {
                        PdfContent()
                    }
                }
            }

            item {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "⚠️ Vérifiez toujours les résultats selon les normes en vigueur et les conditions réelles de l'installation.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFF30250D),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(15.dp),
                    color = Orange,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }
}

// ====================================================
// INPUT FIELD
// ====================================================

@Composable
fun ElecoraInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(label)
        },
        placeholder = {
            Text(placeholder)
        },
        singleLine = true
    )
}

// ====================================================
// VOLTAGE DROP
// ====================================================

@Composable
fun VoltageDropContent() {

    var current by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        ElecoraInput(
            label = "Courant (A)",
            value = current,
            onValueChange = { current = it },
            placeholder = "Ex: 20"
        )

        ElecoraInput(
            label = "Longueur (m)",
            value = length,
            onValueChange = { length = it },
            placeholder = "Ex: 30"
        )

        ElecoraInput(
            label = "Section (mm²)",
            value = section,
            onValueChange = { section = it },
            placeholder = "Ex: 4"
        )

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = ElectricBlue
            )
        ) {

            Text(
                text = "Calculer",
                color = Navy,
                fontWeight = FontWeight.Bold
            )
        }

        ResultCard(
            title = "Résultat",
            value = "1,92 %",
            description = "Chute de tension estimée"
        )
    }
}

// ====================================================
// CABLE SECTION
// ====================================================

@Composable
fun CableSectionContent() {

    var current by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var cosPhi by remember { mutableStateOf("0.90") }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        ElecoraInput(
            label = "Courant (A)",
            value = current,
            onValueChange = { current = it },
            placeholder = "Ex: 25"
        )

        ElecoraInput(
            label = "Longueur (m)",
            value = length,
            onValueChange = { length = it },
            placeholder = "Ex: 40"
        )

        ElecoraInput(
            label = "Cos φ",
            value = cosPhi,
            onValueChange = { cosPhi = it },
            placeholder = "Ex: 0.90"
        )

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            )
        ) {

            Text(
                text = "Dimensionner",
                color = Navy,
                fontWeight = FontWeight.Bold
            )
        }

        ResultCard(
            title = "Section recommandée",
            value = "6 mm²",
            description = "Valeur indicative de pré-dimensionnement"
        )
    }
}

// ====================================================
// BREAKER
// ====================================================

@Composable
fun BreakerContent() {

    var current by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        ElecoraInput(
            label = "Courant de charge (A)",
            value = current,
            onValueChange = { current = it },
            placeholder = "Ex: 27"
        )

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            )
        ) {

            Text(
                text = "Calculer le calibre",
                color = Navy,
                fontWeight = FontWeight.Bold
            )
        }

        ResultCard(
            title = "Calibre conseillé",
            value = "32 A",
            description = "À vérifier selon la protection et le circuit"
        )
    }
}

// ====================================================
// PDF
// ====================================================

@Composable
fun PdfContent() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Navy2
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "📄",
                fontSize = 55.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Analyse d'un plan électrique",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sélectionnez un fichier PDF pour préparer son analyse.",
                color = Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple
                )
            ) {

                Text(
                    text = "Choisir un PDF",
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ====================================================
// RESULT CARD
// ====================================================

@Composable
fun ResultCard(
    title: String,
    value: String,
    description: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF082744)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                color = Gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = value,
                color = Cyan,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                color = Gray,
                fontSize = 11.sp
            )
        }
    }
}

// ====================================================
// HISTORY
// ====================================================

@Composable
fun HistoryScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Historique",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Vos derniers calculs",
                color = Gray,
                fontSize = 13.sp
            )
        }

        item {

            HistoryCard(
                icon = "⚡",
                title = "Chute de tension",
                result = "1,92 %",
                date = "Aujourd'hui"
            )
        }

        item {

            HistoryCard(
                icon = "🔌",
                title = "Section de câble",
                result = "6 mm²",
                date = "Hier"
            )
        }

        item {

            HistoryCard(
                icon = "🛡️",
                title = "Calibre disjoncteur",
                result = "32 A",
                date = "Hier"
            )
        }
    }
}

// ====================================================
// HISTORY CARD
// ====================================================

@Composable
fun HistoryCard(
    icon: String,
    title: String,
    result: String,
    date: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Navy2
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = date,
                    color = Gray,
                    fontSize = 11.sp
                )
            }

            Text(
                text = result,
                color = Cyan,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ====================================================
// PROFILE
// ====================================================

@Composable
fun ProfileScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Profil",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Configuration de votre assistant",
                color = Gray,
                fontSize = 13.sp
            )
        }

        item {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Navy2
                )
            ) {

                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                ElectricBlue.copy(alpha = 0.15f),
                                RoundedCornerShape(18.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "⚡",
                            fontSize = 30.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Column {

                        Text(
                            text = "Elecora",
                            color = White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Assistant électricité bâtiment",
                            color = Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        item {

            SettingCard(
                icon = "⚙️",
                title = "Paramètres",
                description = "Configurer l'application"
            )
        }

        item {

            SettingCard(
                icon = "📐",
                title = "Unités",
                description = "A, V, W, mm², m..."
            )
        }

        item {

            SettingCard(
                icon = "ℹ️",
                title = "À propos",
                description = "Elecora • Version 1.0"
            )
        }
    }
}

// ====================================================
// SETTING CARD
// ====================================================

@Composable
fun SettingCard(
    icon: String,
    title: String,
    description: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Navy2
        )
    ) {

        Row(
            modifier = Modifier.padding(17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                fontSize = 25.sp
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = description,
                    color = Gray,
                    fontSize = 11.sp
                )
            }

            Text(
                text = "›",
                color = Gray,
                fontSize = 25.sp
            )
        }
    }
}
