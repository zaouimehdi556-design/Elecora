package com.elecora.app

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elecora.app.calculator.Conductor
import com.elecora.app.calculator.ElectricalCalculations
import com.elecora.app.calculator.PhaseType
import com.elecora.app.data.ElecoraHistory
import com.elecora.app.data.HistoryItem
import com.elecora.app.pdf.PdfAnalyzer
import java.io.File

// ============================================================
// ELECORA COLORS
// ============================================================

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

// ============================================================
// MAIN ACTIVITY
// ============================================================

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ElecoraApp()
        }
    }
}

// ============================================================
// APP
// ============================================================

@Composable
fun ElecoraApp() {

    var currentPage by remember {
        mutableStateOf("Accueil")
    }

    var selectedTool by remember {
        mutableStateOf<String?>(null)
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = ElectricBlue,
            secondary = Cyan,
            background = Navy,
            surface = Navy2,
            surfaceVariant = CardBlue,
            onPrimary = Navy,
            onSecondary = Navy,
            onBackground = White,
            onSurface = White,
            onSurfaceVariant = White
        )
    ) {

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

                            "Accueil" -> {
                                HomeScreen(
                                    onToolClick = {
                                        selectedTool = it
                                    }
                                )
                            }

                            "Outils" -> {
                                ToolsScreen(
                                    onToolClick = {
                                        selectedTool = it
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

// ============================================================
// HOME
// ============================================================

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

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "⚡ Elecora",
                color = White,
                fontSize = 30.sp,
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

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Votre assistant électrique",
                        color = White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

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
                description = "Déterminer la section du conducteur",
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
                description = "Choisir le calibre adapté",
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

            InfoCard(
                title = "À propos d'Elecora",
                text = "Un assistant destiné au pré-dimensionnement des installations électriques du bâtiment."
            )
        }

        item {

            WarningCard()
        }
    }
}

// ============================================================
// TOOL CARD
// ============================================================

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
            .clickable {
                onClick()
            },

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
                    .size(52.dp)
                    .background(
                        color.copy(alpha = 0.15f),
                        RoundedCornerShape(16.dp)
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = icon,
                    fontSize = 25.sp
                )
            }

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
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
                fontSize = 30.sp
            )
        }
    }
}

// ============================================================
// BOTTOM NAVIGATION
// ============================================================

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
            onClick = {
                onSelected("Accueil")
            },

            icon = {
                Text(
                    text = "⌂",
                    color = if (selected == "Accueil")
                        ElectricBlue
                    else
                        Gray,
                    fontSize = 22.sp
                )
            },

            label = {
                Text(
                    text = "Accueil",
                    color = if (selected == "Accueil")
                        ElectricBlue
                    else
                        Gray
                )
            }
        )

        NavigationBarItem(
            selected = selected == "Outils",
            onClick = {
                onSelected("Outils")
            },

            icon = {
                Text(
                    text = "⚙",
                    color = if (selected == "Outils")
                        ElectricBlue
                    else
                        Gray,
                    fontSize = 20.sp
                )
            },

            label = {
                Text(
                    text = "Outils",
                    color = if (selected == "Outils")
                        ElectricBlue
                    else
                        Gray
                )
            }
        )

        NavigationBarItem(
            selected = selected == "Historique",
            onClick = {
                onSelected("Historique")
            },

            icon = {
                Text(
                    text = "◷",
                    color = if (selected == "Historique")
                        ElectricBlue
                    else
                        Gray,
                    fontSize = 22.sp
                )
            },

            label = {
                Text(
                    text = "Historique",
                    color = if (selected == "Historique")
                        ElectricBlue
                    else
                        Gray
                )
            }
        )

        NavigationBarItem(
            selected = selected == "Profil",
            onClick = {
                onSelected("Profil")
            },

            icon = {
                Text(
                    text = "●",
                    color = if (selected == "Profil")
                        ElectricBlue
                    else
                        Gray,
                    fontSize = 18.sp
                )
            },

            label = {
                Text(
                    text = "Profil",
                    color = if (selected == "Profil")
                        ElectricBlue
                    else
                        Gray
                )
            }
        )
    }
}

// ============================================================
// TOOLS SCREEN
// ============================================================

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

            Spacer(
                modifier = Modifier.height(10.dp)
            )

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
                description = "Analyse automatique du plan",
                color = Purple,
                onClick = {
                    onToolClick("Analyse PDF")
                }
            )
        }
    }
}

// ============================================================
// TOOL SCREEN
// ============================================================

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

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

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

                WarningCard()

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }
    }
}

// ============================================================
// INPUT
// ============================================================

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
            Text(
                text = label,
                color = Gray
            )
        },

        placeholder = {
            Text(
                text = placeholder,
                color = Gray
            )
        },

        singleLine = true,

        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = White,
            unfocusedTextColor = White,

            focusedBorderColor = ElectricBlue,
            unfocusedBorderColor = Gray,

            focusedLabelColor = ElectricBlue,
            unfocusedLabelColor = Gray,

            cursorColor = ElectricBlue,

            focusedPlaceholderColor = Gray,
            unfocusedPlaceholderColor = Gray
        )
    )
}

// ============================================================
// CHOICE ROW
// ============================================================

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

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            FilterChip(
                selected = selectedFirst,
                onClick = onFirst,

                label = {
                    Text(
                        text = first
                    )
                },

                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ElectricBlue,
                    containerColor = CardBlue,
                    selectedLabelColor = Navy,
                    labelColor = White
                )
            )

            FilterChip(
                selected = !selectedFirst,
                onClick = onSecond,

                label = {
                    Text(
                        text = second
                    )
                },

                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ElectricBlue,
                    containerColor = CardBlue,
                    selectedLabelColor = Navy,
                    labelColor = White
                )
            )
        }
    }
}

// ============================================================
// VOLTAGE DROP
// ============================================================

@Composable
fun VoltageDropContent() {

    var current by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var cosPhi by remember { mutableStateOf("0.90") }

    var phase by remember {
        mutableStateOf(PhaseType.SINGLE)
    }

    var material by remember {
        mutableStateOf(Conductor.COPPER)
    }

    var result by remember {
        mutableStateOf<String?>(null)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        SectionTitle(
            icon = "⚡",
            title = "Calcul de chute de tension"
        )

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
            onValueChange = {
                current = it
            },
            placeholder = "Ex: 20"
        )

        ElecoraInput(
            label = "Longueur (m)",
            value = length,
            onValueChange = {
                length = it
            },
            placeholder = "Ex: 30"
        )

        ElecoraInput(
            label = "Section (mm²)",
            value = section,
            onValueChange = {
                section = it
            },
            placeholder = "Ex: 4"
        )

        ElecoraInput(
            label = "Cos φ",
            value = cosPhi,
            onValueChange = {
                cosPhi = it
            },
            placeholder = "Ex: 0.90"
        )

        PrimaryButton(
            text = "⚡ Calculer la chute",
            color = ElectricBlue,
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
                            result = "%.2f %%"
                                .format(calculated.percent),
                            detail = "ΔU = %.2f V"
                                .format(calculated.volts)
                        )
                    )
                }
            }
        )

        ErrorText(error)

        result?.let {

            ResultCard(
                title = "Résultat",
                value = it,
                description = "Chute de tension estimée"
            )
        }
    }
}

// ============================================================
// CABLE SECTION
// ============================================================

@Composable
fun CableSectionContent() {

    var current by remember {
        mutableStateOf("")
    }

    var length by remember {
        mutableStateOf("")
    }

    var cosPhi by remember {
        mutableStateOf("0.90")
    }

    var maxDrop by remember {
        mutableStateOf("5")
    }

    var phase by remember {
        mutableStateOf(PhaseType.SINGLE)
    }

    var material by remember {
        mutableStateOf(Conductor.COPPER)
    }

    var result by remember {
        mutableStateOf<String?>(null)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        SectionTitle(
            icon = "🔌",
            title = "Dimensionnement du câble"
        )

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
            onValueChange = {
                current = it
            },
            placeholder = "Ex: 25"
        )

        ElecoraInput(
            label = "Longueur (m)",
            value = length,
            onValueChange = {
                length = it
            },
            placeholder = "Ex: 40"
        )

        ElecoraInput(
            label = "Cos φ",
            value = cosPhi,
            onValueChange = {
                cosPhi = it
            },
            placeholder = "Ex: 0.90"
        )

        ElecoraInput(
            label = "Chute maximale (%)",
            value = maxDrop,
            onValueChange = {
                maxDrop = it
            },
            placeholder = "Ex: 5"
        )

        PrimaryButton(
            text = "🔌 Dimensionner le câble",
            color = Green,
            onClick = {

                val calculation =
                    ElectricalCalculations.sizeCable(
                        current = current.toDoubleOrNull(),
                        length = length.toDoubleOrNull(),
                        cosPhi = cosPhi.toDoubleOrNull(),
                        maxDrop = maxDrop.toDoubleOrNull(),
                        phase = phase,
                        material = material
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
                            result =
                                "${calculated.section} mm²",
                            detail =
                                "ΔU = %.2f %%"
                                    .format(
                                        calculated.voltageDropPercent
                                    )
                        )
                    )
                }
            }
        )

        ErrorText(error)

        result?.let {

            ResultCard(
                title = "Section recommandée",
                value = it,
                description = "Pré-dimensionnement indicatif"
            )
        }
    }
}

// ============================================================
// BREAKER
// ============================================================

@Composable
fun BreakerContent() {

    var current by remember {
        mutableStateOf("")
    }

    var cableAmpacity by remember {
        mutableStateOf("")
    }

    var result by remember {
        mutableStateOf<String?>(null)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        SectionTitle(
            icon = "🛡️",
            title = "Calibre du disjoncteur"
        )

        ElecoraInput(
            label = "Courant Ib (A)",
            value = current,
            onValueChange = {
                current = it
            },
            placeholder = "Ex: 27"
        )

        ElecoraInput(
            label = "Courant admissible Iz (A)",
            value = cableAmpacity,
            onValueChange = {
                cableAmpacity = it
            },
            placeholder = "Ex: 32"
        )

        PrimaryButton(
            text = "🛡️ Calculer le calibre",
            color = Orange,
            onClick = {

                val calculation =
                    ElectricalCalculations.breaker(
                        current = current.toDoubleOrNull(),
                        cableAmpacity =
                            cableAmpacity.toDoubleOrNull()
                    )

                val calculated = calculation.first

                error = calculation.second

                if (calculated != null) {

                    result =
                        "${calculated.rating} A"

                    ElecoraHistory.add(
                        HistoryItem(
                            icon = "🛡️",
                            title = "Calibre disjoncteur",
                            result =
                                "${calculated.rating} A",
                            detail = calculated.note
                        )
                    )
                }
            }
        )

        ErrorText(error)

        result?.let {

            ResultCard(
                title = "Calibre conseillé",
                value = it,
                description =
                    "Vérifier la protection et la capacité de coupure"
            )
        }
    }
}

// ============================================================
// PDF RENDER
// ============================================================

fun renderFirstPdfPage(
    uri: Uri,
    context: android.content.Context
): Pair<Bitmap?, Int> {

    return try {

        val file = File(
            context.cacheDir,
            "elecora_preview.pdf"
        )

        context.contentResolver
            .openInputStream(uri)
            ?.use { input ->

                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

        val descriptor =
            ParcelFileDescriptor.open(
                file,
                ParcelFileDescriptor.MODE_READ_ONLY
            )

        val renderer =
            PdfRenderer(descriptor)

        val pageCount =
            renderer.pageCount

        if (pageCount == 0) {

            renderer.close()
            descriptor.close()

            return null to 0
        }

        val page =
            renderer.openPage(0)

        val width = 1200

        val height =
            (
                width.toFloat() *
                    page.height /
                    page.width
            ).toInt()

        val bitmap =
            Bitmap.createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888
            )

        bitmap.eraseColor(
            android.graphics.Color.WHITE
        )

        page.render(
            bitmap,
            null,
            null,
            PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
        )

        page.close()
        renderer.close()
        descriptor.close()

        bitmap to pageCount

    } catch (e: Exception) {

        null to 0
    }
}

// ============================================================
// PDF CONTENT
// ============================================================

@Composable
fun PdfContent() {

    val context = LocalContext.current

    var selectedPdf by remember {
        mutableStateOf<Uri?>(null)
    }

    var previewBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var pageCount by remember {
        mutableStateOf(0)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    var analysisText by remember {
        mutableStateOf<String?>(null)
    }

    var isAnalyzing by remember {
        mutableStateOf(false)
    }

    val pdfLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->

            if (uri != null) {

                selectedPdf = uri
                error = null
                analysisText = null

                val result =
                    renderFirstPdfPage(
                        uri = uri,
                        context = context
                    )

                previewBitmap = result.first
                pageCount = result.second

                if (result.second == 0) {

                    error =
                        "Impossible de lire ce fichier PDF."
                }
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
                .padding(20.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = "📄",
                fontSize = 50.sp
            )

            Text(
                text = "Analyse d'un plan électrique",
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    "Sélectionnez votre plan PDF pour commencer.",
                color = Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            PrimaryButton(
                text = "📂 Choisir un PDF",
                color = Purple,
                onClick = {

                    pdfLauncher.launch(
                        arrayOf("application/pdf")
                    )
                }
            )

            selectedPdf?.let {

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                Text(
                    text = "✓ PDF chargé",
                    color = Green,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                if (pageCount > 0) {

                    Text(
                        text = "$pageCount page(s)",
                        color = Gray,
                        fontSize = 12.sp
                    )
                }
            }

            previewBitmap?.let { bitmap ->

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Text(
                    text = "Aperçu de la première page",
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Image(
                        bitmap =
                            bitmap.asImageBitmap(),

                        contentDescription =
                            "Aperçu du plan PDF",

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                PrimaryButton(
                    text =
                        if (isAnalyzing)
                            "⏳ Analyse..."
                        else
                            "🔍 Analyser le plan",

                    color = ElectricBlue,

                    onClick = {

                        if (selectedPdf != null) {

                            isAnalyzing = true
                            error = null
                            analysisText = null

                            try {

                                val result =
                                    PdfAnalyzer.analyze(
                                        context = context,
                                        uri = selectedPdf!!
                                    )

                                val detected =
                                    detectElectricalElements(
                                        result.extractedText
                                    )

                                analysisText =
                                    buildPdfAnalysisText(
                                        result.pageCount,
                                        result.hasText,
                                        detected,
                                        result.extractedText
                                    )

                                ElecoraHistory.add(
                                    HistoryItem(
                                        icon = "📄",
                                        title = "Analyse PDF",
                                        result =
                                            "${result.pageCount} page(s)",
                                        detail =
                                            if (result.hasText)
                                                "Texte et éléments détectés"
                                            else
                                                "Aucun texte exploitable"
                                    )
                                )

                            } catch (e: Exception) {

                                error =
                                    "Erreur pendant l'analyse du PDF."
                            }

                            isAnalyzing = false
                        }
                    }
                )
            }

            analysisText?.let { text ->

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                PdfAnalysisCard(
                    text = text
                )
            }

            ErrorText(error)
        }
    }
}

// ============================================================
// PDF DETECTION
// ============================================================

data class ElectricalDetection(
    val lighting: Int,
    val sockets: Int,
    val breakers: Int,
    val cables: Int,
    val sections: List<String>
)

fun detectElectricalElements(
    text: String
): ElectricalDetection {

    val normalized =
        text.lowercase()

    fun countWords(
        words: List<String>
    ): Int {

        return words.sumOf { word ->

            Regex(
                Regex.escape(word)
            ).findAll(normalized).count()
        }
    }

    val lighting =
        countWords(
            listOf(
                "éclairage",
                "eclairage",
                "lighting",
                "luminaire",
                "lampe",
                "light"
            )
        )

    val sockets =
        countWords(
            listOf(
                "prise",
                "prises",
                "socket",
                "power outlet"
            )
        )

    val breakers =
        countWords(
            listOf(
                "disjoncteur",
                "breaker",
                "mcb",
                "dij"
            )
        )

    val cables =
        countWords(
            listOf(
                "câble",
                "cable",
                "conducteur",
                "conductor"
            )
        )

    val sectionRegex =
        Regex(
            """\b\d+(?:[.,]\d+)?\s*mm(?:²|2)\b"""
        )

    val sections =
        sectionRegex
            .findAll(normalized)
            .map {
                it.value
                    .replace(",", ".")
                    .replace(" ", "")
            }
            .distinct()
            .toList()

    return ElectricalDetection(
        lighting = lighting,
        sockets = sockets,
        breakers = breakers,
        cables = cables,
        sections = sections
    )
}

fun buildPdfAnalysisText(
    pageCount: Int,
    hasText: Boolean,
    detection: ElectricalDetection,
    originalText: String
): String {

    val builder =
        StringBuilder()

    builder.append(
        "📊 RÉSULTAT DE L'ANALYSE\n\n"
    )

    builder.append(
        "📄 Pages : $pageCount\n\n"
    )

    builder.append(
        "⚡ Éléments détectés\n\n"
    )

    builder.append(
        "💡 Éclairage : ${detection.lighting}\n"
    )

    builder.append(
        "🔌 Prises : ${detection.sockets}\n"
    )

    builder.append(
        "🛡️ Disjoncteurs : ${detection.breakers}\n"
    )

    builder.append(
        "🔗 Câbles / conducteurs : ${detection.cables}\n"
    )

    builder.append("\n")

    if (detection.sections.isNotEmpty()) {

        builder.append(
            "📏 Sections détectées :\n"
        )

        detection.sections.forEach {

            builder.append(
                "• $it\n"
            )
        }

    } else {

        builder.append(
            "📏 Sections détectées : aucune\n"
        )
    }

    builder.append("\n")

    if (!hasText) {

        builder.append(
            "⚠️ Aucun texte exploitable n'a été trouvé.\n"
        )

        builder.append(
            "Ce PDF peut être un plan scanné ou composé principalement d'images.\n"
        )

    } else {

        builder.append(
            "📝 Texte extrait :\n\n"
        )

        val preview =
            originalText.take(3000)

        builder.append(preview)

        if (originalText.length > 3000) {

            builder.append(
                "\n\n… texte tronqué dans l'affichage."
            )
        }
    }

    builder.append(
        "\n\n⚠️ Cette analyse est une pré-analyse automatique. Elle ne remplace pas la vérification du plan par un professionnel."
    )

    return builder.toString()
}

// ============================================================
// PDF ANALYSIS CARD
// ============================================================

@Composable
fun PdfAnalysisCard(
    text: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBlue
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = "🔎 Analyse Elecora",
                color = Cyan,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = text,
                color = White,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

// ============================================================
// RESULT CARD
// ============================================================

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

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = value,
                color = Cyan,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = description,
                color = Gray,
                fontSize = 11.sp
            )
        }
    }
}

// ============================================================
// PRIMARY BUTTON
// ============================================================

@Composable
fun PrimaryButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = Modifier.fillMaxWidth(),

        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Navy
        ),

        shape = RoundedCornerShape(14.dp)
    ) {

        Text(
            text = text,
            color = Navy,
            fontWeight = FontWeight.Bold
        )
    }
}

// ============================================================
// SECTION TITLE
// ============================================================

@Composable
fun SectionTitle(
    icon: String,
    title: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = icon,
            fontSize = 25.sp
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Text(
            text = title,
            color = White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ============================================================
// ERROR
// ============================================================

@Composable
fun ErrorText(
    error: String?
) {

    error?.let {

        Text(
            text = it,
            color = Orange,
            fontSize = 12.sp
        )
    }
}

// ============================================================
// INFO CARD
// ============================================================

@Composable
fun InfoCard(
    title: String,
    text: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Navy2
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = title,
                color = Cyan,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(7.dp)
            )

            Text(
                text = text,
                color = Gray,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

// ============================================================
// WARNING CARD
// ============================================================

@Composable
fun WarningCard() {

    Text(
        text = "⚠️ Les résultats sont destinés au pré-dimensionnement et doivent être vérifiés selon les normes et les conditions réelles de l'installation.",

        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF30250D),
                RoundedCornerShape(16.dp)
            )
            .padding(15.dp),

        color = Orange,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
}

// ============================================================
// HISTORY
// ============================================================

@Composable
fun HistoryScreen() {

    val history =
        ElecoraHistory.items

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(18.dp),

        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        item {

            Spacer(
                modifier = Modifier.height(10.dp)
            )

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

                InfoCard(
                    title = "Aucun historique",
                    text = "Vos calculs apparaîtront ici automatiquement."
                )
            }

        } else {

            items(history.size) { index ->

                val item =
                    history[index]

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

// ============================================================
// HISTORY CARD
// ============================================================

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

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
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

// ============================================================
// PROFILE
// ============================================================

@Composable
fun ProfileScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(18.dp),

        verticalArrangement =
            Arrangement.spacedBy(14.dp)
    ) {

        item {

            Spacer(
                modifier = Modifier.height(10.dp)
            )

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

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text =
                            "Assistant professionnel • Électricité bâtiment",
                        color = Gray,
                        fontSize = 12.sp
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

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

// ============================================================
// SETTING CARD
// ============================================================

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

            Spacer(
                modifier = Modifier.width(14.dp)
            )

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
