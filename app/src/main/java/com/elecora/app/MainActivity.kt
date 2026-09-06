package com.elecora.app

import android.os.Bundle
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elecora.app.calculator.Conductor
import com.elecora.app.calculator.ElectricalCalculations
import com.elecora.app.calculator.PhaseType
import com.elecora.app.data.ElecoraHistory
import com.elecora.app.data.HistoryItem

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

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ElecoraApp()
        }
    }
}

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

@Composable
fun HomeScreen(
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

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "⚡ Elecora",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Assistant professionnel • Électricité bâtiment",
                color = Gray,
                fontSize = 13.sp
            )
        }

        item {

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
                        text = "Bonjour 👋",
                        color = Gray,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Technicien électricien",
                        color = White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Calculer • Vérifier • Dimensionner",
                        color = Cyan,
                        fontSize = 13.sp
                    )
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
            ToolCard(
                icon = "⚡",
                title = "Chute de tension",
                description = "Calculer la chute de tension",
                color = ElectricBlue,
                onClick = {
                    onToolClick("Chute de tension")
                }
            )
        }

        item {
            ToolCard(
                icon = "🔌",
                title = "Section de câble",
                description = "Déterminer la section",
                color = Green,
                onClick = {
                    onToolClick("Section de câble")
                }
            )
        }

        item {
            ToolCard(
                icon = "🛡️",
                title = "Calibre disjoncteur",
                description = "Choisir la protection",
                color = Orange,
                onClick = {
                    onToolClick("Calibre disjoncteur")
                }
            )
        }

        item {
            ToolCard(
                icon = "📄",
                title = "Analyse PDF",
                description = "Analyser un plan électrique",
                color = Purple,
                onClick = {
                    onToolClick("Analyse PDF")
                }
            )
        }

        item {

            Text(
                text = "⚠️ Les résultats sont destinés au pré-dimensionnement et doivent être vérifiés selon les normes et les conditions réelles.",
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
        }
    }
}

@Composable
fun ToolCard(
    icon: String,
    title: String,
    description: String,
    color: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBlue
        )
    ) {

        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color.copy(alpha = 0.15f),
                        RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = icon,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = White,
                    fontSize = 15.sp,
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
                color = color,
                fontSize = 28.sp
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    selected: String,
    onSelected: (String) -> Unit
) {

    NavigationBar(
        containerColor = Navy2
    ) {

        NavigationBarItem(
            selected = selected == "Accueil",
            onClick = { onSelected("Accueil") },
            icon = { Text("⌂", fontSize = 22.sp) },
            label = { Text("Accueil") }
        )

        NavigationBarItem(
            selected = selected == "Outils",
            onClick = { onSelected("Outils") },
            icon = { Text("⚙", fontSize = 20.sp) },
            label = { Text("Outils") }
        )

        NavigationBarItem(
            selected = selected == "Historique",
            onClick = { onSelected("Historique") },
            icon = { Text("◷", fontSize = 22.sp) },
            label = { Text("Historique") }
        )

        NavigationBarItem(
            selected = selected == "Profil",
            onClick = { onSelected("Profil") },
            icon = { Text("●", fontSize = 18.sp) },
            label = { Text("Profil") }
        )
    }
}

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

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Outils",
                color = White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Tous les outils techniques Elecora",
                color = Gray,
                fontSize = 13.sp
            )
        }

        item {
            ToolCard(
                icon = "⚡",
                title = "Chute de tension",
                description = "Calcul monophasé ou triphasé",
                color = ElectricBlue,
                onClick = {
                    onToolClick("Chute de tension")
                }
            )
        }

        item {
            ToolCard(
                icon = "🔌",
                title = "Section de câble",
                description = "Dimensionnement du conducteur",
                color = Green,
                onClick = {
                    onToolClick("Section de câble")
                }
            )
        }

        item {
            ToolCard(
                icon = "🛡️",
                title = "Calibre disjoncteur",
                description = "Choix du calibre de protection",
                color = Orange,
                onClick = {
                    onToolClick("Calibre disjoncteur")
                }
            )
        }

        item {
            ToolCard(
                icon = "📄",
                title = "Analyse PDF",
                description = "Préparation de l'analyse des plans",
                color = Purple,
                onClick = {
                    onToolClick("Analyse PDF")
                }
            )
        }
    }
}

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

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(onClick = onBack) {
                    Text(
                        text = "← Retour",
                        color = Cyan
                    )
                }

                Text(
                    text = tool,
                    color = White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {

                when (tool) {

                    "Chute de tension" ->
                        VoltageDropContent()

                    "Section de câble" ->
                        CableSectionContent()

                    "Calibre disjoncteur" ->
                        BreakerContent()

                    "Analyse PDF" ->
                        PdfContent()
                }
            }

            item {

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "⚠️ Vérifiez les résultats selon les normes en vigueur et les conditions réelles de l'installation.",
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
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = true
    )
}

@Composable
fun ChoiceRow(
    title: String,
    first: String,
    second: String,
    selectedFirst: Boolean,
    onFirst: () -> Unit,
    onSecond: () -> Unit
) {

    Column {

        Text(
            text = title,
            color = White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            FilterChip(
                selected = selectedFirst,
                onClick = onFirst,
                label = { Text(first) }
            )

            FilterChip(
                selected = !selectedFirst,
                onClick = onSecond,
                label = { Text(second) }
            )
        }
    }
}

@Composable
fun VoltageDropContent() {

    var current by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var cosPhi by remember { mutableStateOf("0.90") }

    var phase by remember { mutableStateOf(PhaseType.SINGLE) }
    var material by remember { mutableStateOf(Conductor.COPPER) }

    var result by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        ChoiceRow(
            title = "Phase",
            first = "Monophasé",
            second = "Triphasé",
            selectedFirst = phase == PhaseType.SINGLE,
            onFirst = {
                phase = PhaseType.SINGLE
            },
            onSecond = {
                phase = PhaseType.THREE
            }
        )

        ChoiceRow(
            title = "Conducteur",
            first = "Cuivre",
            second = "Aluminium",
            selectedFirst = material == Conductor.COPPER,
            onFirst = {
                material = Conductor.COPPER
            },
            onSecond = {
                material = Conductor.ALUMINIUM
            }
        )

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

        ElecoraInput(
            label = "Cos φ",
            value = cosPhi,
            onValueChange = { cosPhi = it },
            placeholder = "Ex: 0.90"
        )

        Button(
            onClick = {

                val calculation =
                    ElectricalCalculations.voltageDrop(
                        current = current.toDoubleOrNull(),
                        length = length.toDoubleOrNull(),
                        section = section.toDoubleOrNull(),
                        cosPhi = cosPhi.toDoubleOrNull(),
                        phase = phase,
                        material = material
                    )

                val calculated = calculation.first
                error = calculation.second

                if (calculated != null) {

                    result =
                        "ΔU = %.2f V\nΔU = %.2f %%"
                            .format(
                                calculated.volts,
                                calculated.percent
                            )

                    ElecoraHistory.add(
                        HistoryItem(
                            icon = "⚡",
                            title = "Chute de tension",
                            result = "%.2f %%".format(calculated.percent),
                            detail = "ΔU = %.2f V".format(calculated.volts)
                        )
                    )
                }

            },
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

        error?.let {
            Text(
                text = it,
                color = Orange
            )
        }

        result?.let {
            ResultCard(
                title = "Résultat",
                value = it,
                description = "Chute de tension estimée"
            )
        }
    }
}

@Composable
fun CableSectionContent() {

    var current by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var cosPhi by remember { mutableStateOf("0.90") }
    var maxDrop by remember { mutableStateOf("5") }

    var result by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

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

        ElecoraInput(
            label = "Chute maximale (%)",
            value = maxDrop,
            onValueChange = { maxDrop = it },
            placeholder = "Ex: 5"
        )

        Button(
            onClick = {

                val calculation =
                    ElectricalCalculations.sizeCable(
                        current = current.toDoubleOrNull(),
                        length = length.toDoubleOrNull(),
                        cosPhi = cosPhi.toDoubleOrNull(),
                        maxDrop = maxDrop.toDoubleOrNull(),
                        phase = PhaseType.SINGLE,
                        material = Conductor.COPPER
                    )

                val calculated = calculation.first
                error = calculation.second

                if (calculated != null) {

                    result =
                        "${calculated.section} mm²"

                    ElecoraHistory.add(
                        HistoryItem(
                            icon = "🔌",
                            title = "Section de câble",
                            result = "${calculated.section} mm²",
                            detail = "ΔU = %.2f %%".format(
                                calculated.voltageDropPercent
                            )
                        )
                    )
                }

            },
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

        error?.let {
            Text(
                text = it,
                color = Orange
            )
        }

        result?.let {
            ResultCard(
                title = "Section recommandée",
                value = it,
                description = "Pré-dimensionnement indicatif"
            )
        }
    }
}

@Composable
fun BreakerContent() {

    var current by remember { mutableStateOf("") }
    var cableAmpacity by remember { mutableStateOf("") }

    var result by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        ElecoraInput(
            label = "Courant Ib (A)",
            value = current,
            onValueChange = { current = it },
            placeholder = "Ex: 27"
        )

        ElecoraInput(
            label = "Courant admissible Iz (A)",
            value = cableAmpacity,
            onValueChange = { cableAmpacity = it },
            placeholder = "Ex: 32"
        )

        Button(
            onClick = {

                val calculation =
                    ElectricalCalculations.breaker(
                        current = current.toDoubleOrNull(),
                        cableAmpacity = cableAmpacity.toDoubleOrNull()
                    )

                val calculated = calculation.first
                error = calculation.second

                if (calculated != null) {

                    result = "${calculated.rating} A"

                    ElecoraHistory.add(
                        HistoryItem(
                            icon = "🛡️",
                            title = "Calibre disjoncteur",
                            result = "${calculated.rating} A",
                            detail = calculated.note
                        )
                    )
                }

            },
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

        error?.let {
            Text(
                text = it,
                color = Orange
            )
        }

        result?.let {
            ResultCard(
                title = "Calibre conseillé",
                value = it,
                description = "Vérifier la protection et la capacité de coupure"
            )
        }
    }
}

@Composable
fun PdfContent() {

    var selectedPdf by remember {
        mutableStateOf<Uri?>(null)
    }

    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->

        if (uri != null) {
            selectedPdf = uri

            ElecoraHistory.add(
                HistoryItem(
                    icon = "📄",
                    title = "Analyse PDF",
                    result = "PDF sélectionné",
                    detail = "Plan électrique prêt pour analyse"
                )
            )
        }
    }

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
                text = "Sélectionnez un plan électrique au format PDF.",
                color = Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    pdfLauncher.launch(
                        arrayOf("application/pdf")
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple
                )
            ) {

                Text(
                    text = "📂 Choisir un fichier PDF",
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }

            selectedPdf?.let {

                Spacer(modifier = Modifier.height(16.dp))

                ResultCard(
                    title = "Fichier sélectionné",
                    value = "✓ PDF prêt",
                    description = "Le plan est chargé et prêt pour l'étape d'analyse."
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        // Analyse intelligente du PDF — prochaine étape
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricBlue
                    )
                ) {

                    Text(
                        text = "🔍 Analyser le plan",
                        color = Navy,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

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

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                color = Cyan,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = description,
                color = Gray,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun HistoryScreen() {

    val history = ElecoraHistory.items

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
                text = "Vos calculs récents",
                color = Gray,
                fontSize = 13.sp
            )
        }

        if (history.isEmpty()) {

            item {

                Text(
                    text = "Aucun calcul enregistré.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    color = Gray,
                    textAlign = TextAlign.Center
                )
            }

        } else {

            items(history.size) { index ->

                val item = history[index]

                HistoryCard(
                    icon = item.icon,
                    title = item.title,
                    result = item.result,
                    detail = item.detail
                )
            }

            item {

                TextButton(
                    onClick = {
                        ElecoraHistory.clear()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Effacer l'historique",
                        color = Orange
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryCard(
    icon: String,
    title: String,
    result: String,
    detail: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                    text = detail,
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

            Spacer(modifier = Modifier.height(15.dp))

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

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "⚡ Elecora",
                        color = Cyan,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Assistant professionnel • Électricité bâtiment",
                        color = Gray,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Version 2.0",
                        color = White,
                        fontSize = 13.sp
                    )
                }
            }
        }

        item {
            SettingCard(
                icon = "⚙️",
                title = "Paramètres",
                description = "Configuration de l'application"
            )
        }

        item {
            SettingCard(
                icon = "📐",
                title = "Unités",
                description = "A • V • W • mm² • m"
            )
        }

        item {
            SettingCard(
                icon = "ℹ️",
                title = "À propos",
                description = "Elecora V2.0"
            )
        }
    }
}

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
