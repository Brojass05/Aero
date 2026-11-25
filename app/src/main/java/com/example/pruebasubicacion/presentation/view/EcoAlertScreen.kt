package com.example.pruebasubicacion.presentation.view



import android.app.Dialog
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Dangerous
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
// ---- Imports Propios ----

import com.example.pruebasubicacion.data.model.ClimaEstado
import com.example.pruebasubicacion.presentation.ui.misc.TopDashboardBar
import com.example.pruebasubicacion.util.GenerateReportButton
import com.example.pruebasubicacion.util.HeroAQIArcMeter
import com.example.pruebasubicacion.util.LocationCardAdaptive
import com.example.pruebasubicacion.util.WeeklyForecast
import com.example.pruebasubicacion.util.colors.getAdaptiveThemeColors
import com.example.pruebasubicacion.util.colors.getRiskInfoAdaptive
import com.example.pruebasubicacion.util.creaInforme
import com.example.pruebasubicacion.util.sumarHorasUtc
// ---- Imports Propios ----

import java.time.LocalDateTime

// --- Modelo de Datos de Perfil de Usuario ---
data class UserProfileData(
    val isLoggedIn: Boolean = false,
    val nombre: String = "Juan",
    val apellido: String = "Pérez",
    val correo: String = "juan.perez@aerotrack.com",
    val peso: String = "75 kg",
    val sexo: String = "Masculino",
    val tieneAlergia: Boolean = false,
    val alergias: String = "Polen de Gramíneas",
    val tipoCiudad: String = "Ciudad Valle Central (Santiago, Rancagua, Talca)"
)

// --- Paleta de Colores Adaptativa (Modo Claro & Modo Oscuro) ---
data class ThemeColors(
    val bgPrimary: Color,
    val bgCard: Color,
    val bgSubtle: Color,
    val borderCard: Color,
    val textPrimary: Color,
    val textMuted: Color,
    val accentBrand: Color,
    val headerGradient: Brush,
    val focusedTextColor: Color,
    val unfocusedTextColor: Color
)

data class RiskInfo(
    val category: String,
    val color: Color,
    val message: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoAlertScreen(
    estado: ClimaEstado = ClimaEstado()
) {
    val isDark = isSystemInDarkTheme()
    val theme = getAdaptiveThemeColors(isDark)
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Deportista, 1 = Sensible/Asma
    var showNotifications by remember { mutableStateOf(false) }
    var showProfile by remember { mutableStateOf(false) }
    var showReportCard by remember { mutableStateOf(false) }

    // Estado de Usuario (Perfil y Login)
    var userProfile by remember { mutableStateOf(UserProfileData()) }
    var activeMetricInfo by remember { mutableStateOf<Triple<String, String, ImageVector>?>(null) }

    val reportAction: () -> Unit = {
        if (!userProfile.isLoggedIn) {
            showProfile = true
            showReportCard = false
        } else {
            showReportCard = true
            showProfile = false
            showNotifications = false
        }
    }

    CompositionLocalProvider(LocalContentColor provides theme.textPrimary) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = theme.bgPrimary
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header Dashboard Adaptativo con Botón de Perfil
                    TopDashboardBar(
                        theme = theme,
                        isLoggedIn = userProfile.isLoggedIn,
                        showNotifications = showNotifications,
                        showProfile = showProfile,
                        onNotificationClick = {
                            showNotifications = !showNotifications
                            showProfile = false
                        },
                        onProfileClick = {
                            showProfile = !showProfile
                            showNotifications = false
                        }
                    )

                    if (showNotifications) {
                        NotificationsView(theme = theme, onClose = { showNotifications = false })
                    } else if (showProfile) {
                        ProfileAccountView(
                            theme = theme,
                            userProfile = userProfile,
                            onLoginSuccess = { updatedProfile ->
                                userProfile = updatedProfile.copy(isLoggedIn = true)
                            },
                            onLogout = {
                                userProfile = userProfile.copy(isLoggedIn = false)
                            },
                            onClose = { showProfile = false }
                        )
                    } else {
                        if (isLandscape) {
                            // MODO HORIZONTAL: Distribución Optimizada en 2 Paneles
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                // Panel Izquierdo: Ubicación + Hero AQI Arc + Reporte/Detalle
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    LocationCardAdaptive(estado = estado, theme = theme)
                                    HeroAQIArcMeter(
                                        estado = estado,
                                        selectedTab = selectedTab,
                                        isDark = isDark
                                    )
                                    ProfileSwitchSegmented(
                                        selectedTab = selectedTab,
                                        onTabSelected = { selectedTab = it },
                                        theme = theme
                                    )
                                }

                                // Panel Derecho: Métricas Ambientales + Pronóstico 24h + Vista Semanal o Detalle
                                Column(
                                    modifier = Modifier.weight(1.1f),
                                    verticalArrangement = Arrangement.spacedBy(14.dp)
                                ) {
                                    if (selectedTab == 0) {
                                        EnvironmentalMetricsGrid(
                                            estado = estado,
                                            theme = theme,
                                            onMetricClick = { title, desc, icon ->
                                                activeMetricInfo = Triple(title, desc, icon)
                                            }
                                        )
                                        HourlyForecast24h(
                                            estado = estado,
                                            theme = theme,
                                            isDark = isDark
                                        )
                                        WeeklyForecast(
                                            estado = estado,
                                            theme = theme,
                                            isDark = isDark
                                        )
                                        GenerateReportButton(
                                            theme = theme,
                                            isLoggedIn = userProfile.isLoggedIn,
                                            onGenerate = reportAction
                                        )
                                    } else {
                                        MainPollutantsCardExtended(
                                            estado = estado,
                                            userProfile = userProfile,
                                            theme = theme,
                                            isDark = isDark
                                        )
                                        GenerateReportButton(
                                            theme = theme,
                                            isLoggedIn = userProfile.isLoggedIn,
                                            onGenerate = reportAction
                                        )
                                    }
                                }
                            }
                        } else {
                            // MODO RETRATO: Layout Vertical Fluido
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                LocationCardAdaptive(estado = estado, theme = theme)
                                HeroAQIArcMeter(
                                    estado = estado,
                                    selectedTab = selectedTab,
                                    isDark = isDark
                                )

                                ProfileSwitchSegmented(
                                    selectedTab = selectedTab,
                                    onTabSelected = { selectedTab = it },
                                    theme = theme
                                )

                                if (selectedTab == 0) {
                                    EnvironmentalMetricsGrid(
                                        estado = estado,
                                        theme = theme,
                                        onMetricClick = { title, desc, icon ->
                                            activeMetricInfo = Triple(title, desc, icon)
                                        }
                                    )
                                    HourlyForecast24h(
                                        estado = estado,
                                        theme = theme,
                                        isDark = isDark
                                    )
                                    WeeklyForecast(estado = estado, theme = theme, isDark = isDark)
                                    GenerateReportButton(
                                        theme = theme,
                                        isLoggedIn = userProfile.isLoggedIn,
                                        onGenerate = reportAction
                                    )
                                } else {
                                    MainPollutantsCardExtended(
                                        estado = estado,
                                        userProfile = userProfile,
                                        theme = theme,
                                        isDark = isDark
                                    )
                                    GenerateReportButton(
                                        theme = theme,
                                        isLoggedIn = userProfile.isLoggedIn,
                                        onGenerate = reportAction
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (showReportCard) {
                Dialog(
                    onDismissRequest = { showReportCard = false }
                ) {
                    ReporteAmbientalCard(
                        theme = theme,
                        climaEstado = estado,
                        tieneAlergia = userProfile.tieneAlergia,
                        alergiaUsuario = userProfile.alergias,
                        onClose = { showReportCard = false }
                    )
                }
            }

            // Diálogo emergente interactivo con transparencia glassmorphism e icono específico
            activeMetricInfo?.let { (title, description, icon) ->
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = { activeMetricInfo = null },
                    confirmButton = {
                        TextButton(onClick = { activeMetricInfo = null }) {
                            Text(
                                "Entendido",
                                fontWeight = FontWeight.Bold,
                                color = theme.accentBrand
                            )
                        }
                    },
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = theme.accentBrand.copy(0.15f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        icon,
                                        contentDescription = null,
                                        tint = theme.accentBrand,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Text(
                                title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = theme.textPrimary
                            )
                        }
                    },
                    text = {
                        Text(
                            description,
                            fontSize = 14.sp,
                            color = theme.textPrimary,
                            lineHeight = 21.sp
                        )
                    },
                    containerColor = theme.bgCard.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(24.dp),
                    tonalElevation = 8.dp
                )
            }
        }
    }
}


@Composable
fun ProfileSwitchSegmented(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    theme: ThemeColors
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = theme.bgCard,
        border = BorderStroke(1.dp, theme.borderCard),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfilePillItem(
                title = "Reporte",
                icon = Icons.Outlined.Assessment,
                isSelected = selectedTab == 0,
                theme = theme,
                onClick = { onTabSelected(0) },
                modifier = Modifier.weight(1f)
            )
            ProfilePillItem(
                title = "Detalle contaminantes",
                icon = Icons.Outlined.Air,
                isSelected = selectedTab == 1,
                theme = theme,
                onClick = { onTabSelected(1) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProfilePillItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    theme: ThemeColors,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(if (isSelected) theme.accentBrand else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else theme.textMuted,
                modifier = Modifier.size(18.dp)
            )
            Text(
                title,
                color = if (isSelected) Color.White else theme.textMuted,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 13.sp
            )
        }
    }
}


@Composable
fun HourlyForecast24h(estado: ClimaEstado, theme: ThemeColors, isDark: Boolean) {
    val clima = estado.clima ?: return
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Outlined.Thermostat,
                        contentDescription = null,
                        tint = theme.accentBrand,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        "Próximas 24 Horas",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = theme.textPrimary
                    )
                }
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                val currentHour = LocalDateTime.now().hour
                val horas = sumarHorasUtc(clima.hourly.time, currentHour)
                val data = horas.zip(clima.hourly.pm25 ?: emptyList()).take(24)

                items(data) { (time, pm) ->
                    val risk = getRiskInfoAdaptive(pm, isDark)
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = risk.color.copy(0.12f),
                        border = BorderStroke(1.5.dp, risk.color.copy(0.4f)),
                        modifier = Modifier.width(78.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                time.substringAfter("T").take(5),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = theme.textMuted
                            )
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = risk.color.copy(0.25f),
                                border = BorderStroke(1.dp, risk.color.copy(0.5f))
                            ) {
                                Box(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${pm.toInt()} µg",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = risk.color
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainPollutantsCardExtended(
    estado: ClimaEstado,
    userProfile: UserProfileData,
    theme: ThemeColors,
    isDark: Boolean
) {
    val h = estado.clima?.hourly ?: return
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Detalle de Contaminantes",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = theme.textPrimary
                    )
                    Text(
                        "Incluye polinizantes y alérgenos de tu perfil",
                        fontSize = 11.sp,
                        color = theme.accentBrand,
                        fontWeight = FontWeight.Bold
                    )
                }

                TextButton(
                    onClick = { creaInforme(h) },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        "Generar Informe",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.accentBrand
                    )
                }
            }

            val list = listOf(
                Triple("PM2.5", (h.pm25?.firstOrNull() ?: 0f), "µg/m³") to 250f,
                Triple("PM10", (h.pm10?.firstOrNull() ?: 0f), "µg/m³") to 500f,
                Triple("NO2", (h.nitrogenDioxide?.firstOrNull() ?: 0f), "µg/m³") to 200f,
                Triple("O3", (h.ozone?.firstOrNull() ?: 0f), "µg/m³") to 180f,
                Triple("SO2", (h.sulphurDioxide?.firstOrNull() ?: 0f), "µg/m³") to 350f,
                Triple("CO", (h.carbonMonoxide?.firstOrNull() ?: 0f), "mg/m³") to 10000f,
                Triple("Polen / Alérgenos (${userProfile.alergias})", 38f, "granos/m³") to 100f
            )

            list.forEach { (data, max) ->
                val (name, value, unit) = data
                PollutantRowMinimalAdaptive(name, value, unit, max, theme, isDark)
            }
        }
    }
}

@Composable
fun PollutantRowMinimalAdaptive(
    name: String,
    value: Float,
    unit: String,
    maxValue: Float,
    theme: ThemeColors,
    isDark: Boolean
) {
    val progress = (value / maxValue).coerceIn(0f, 1f)
    val barColor = if (name.contains("PM2.5") || name.contains("Polen")) getRiskInfoAdaptive(
        value,
        isDark
    ).color else theme.accentBrand

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, color = theme.textMuted, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    "${value.toInt()}",
                    color = theme.textPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    unit,
                    color = theme.textMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
            }
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = barColor,
            trackColor = theme.bgSubtle,
        )
    }
}

fun getRiskIconForPM25(pm25: Float): ImageVector = when {
    pm25 <= 12.0f -> Icons.Outlined.CheckCircle
    pm25 <= 35.4f -> Icons.Outlined.Info
    pm25 <= 55.4f -> Icons.Outlined.WarningAmber
    else -> Icons.Outlined.Dangerous
}


@Composable
fun NotificationItemAdaptive(title: String, desc: String, theme: ThemeColors) {
    Surface(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = theme.bgSubtle,
        border = BorderStroke(1.dp, theme.accentBrand.copy(0.2f))
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = theme.accentBrand)
            Spacer(Modifier.height(4.dp))
            Text(desc, fontSize = 13.sp, color = theme.textMuted, lineHeight = 18.sp)
        }
    }
}

@Composable
fun EnvironmentalMetricsGrid(
    estado: ClimaEstado,
    theme: ThemeColors,
    onMetricClick: (String, String, ImageVector) -> Unit
) {
    val hum = estado.clima?.hourly?.humidity?.firstOrNull() ?: 0f
    val temp = estado.clima?.hourly?.temperature?.firstOrNull() ?: 0f
    var co2 = estado.clima?.hourly?.carbonDioxide?.firstOrNull() ?: 0f
    co2 /= 10
    val pm10 = estado.clima?.hourly?.pm10?.firstOrNull() ?: 0f

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            "MÉTRICAS AMBIENTALES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = theme.textMuted,
            letterSpacing = 1.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricTileAdaptive(
                icon = Icons.Outlined.WaterDrop,
                label = "Humedad",
                value = "${hum.toInt()}%",
                theme = theme,
                modifier = Modifier.weight(1f),
                onClick = {
                    onMetricClick(
                        "Humedad del Aire",
                        "Es la cantidad de vapor de agua presente en la atmósfera.\n\nImpacto en el deporte al aire libre:\nValores superiores al 70-80% dificultan la evaporación del sudor, elevando la temperatura corporal y provocando fatiga prematura y deshidratación acelerada.",
                        Icons.Outlined.WaterDrop
                    )
                }
            )
            MetricTileAdaptive(
                icon = Icons.Outlined.Thermostat,
                label = "Temperatura",
                value = "${temp.toInt()}°C",
                theme = theme,
                modifier = Modifier.weight(1f),
                onClick = {
                    onMetricClick(
                        "Temperatura Ambiental",
                        "El nivel de calor o frío medido en el entorno.\n\nImpacto en el deporte al aire libre:\nTemperaturas elevadas aumentan el estrés cardiovascular y el riesgo de golpes de calor. En frío extremo, se requiere mayor calentamiento para evitar lesiones musculares.",
                        Icons.Outlined.Thermostat
                    )
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricTileAdaptive(
                icon = Icons.Outlined.Air,
                label = "Dióxido Carbono",
                value = "${co2.toInt()}% CO₂",
                theme = theme,
                modifier = Modifier.weight(1f),
                onClick = {
                    onMetricClick(
                        "Dióxido de Carbono (CO₂)",
                        "Concentración de gas CO₂ en el entorno urbano o natural.\n\nImpacto en el deporte al aire libre:\nConcentraciones altas en zonas de tráfico intenso reducen la oxigenación óptima durante entrenamientos aeróbicos de alta intensidad.",
                        Icons.Outlined.Air
                    )
                }
            )
            MetricTileAdaptive(
                icon = Icons.Outlined.Assessment,
                label = "PM10",
                value = "${pm10.toInt()} µg/m³",
                theme = theme,
                modifier = Modifier.weight(1f),
                onClick = {
                    onMetricClick(
                        "Material Particulado (PM10)",
                        "Partículas sólidas o líquidas de polvo, polen y hollín menores a 10 micrómetros.\n\nImpacto en el deporte al aire libre:\nPenetran en las vías respiratorias superiores, causando irritación, congestión y reducción de la capacidad pulmonar durante carreras o ciclismo.",
                        Icons.Outlined.Assessment
                    )
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun MetricTileAdaptive(
    icon: ImageVector,
    label: String,
    value: String,
    theme: ThemeColors,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = theme.bgCard,
        border = BorderStroke(1.dp, theme.borderCard),
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = theme.accentBrand.copy(0.12f),
                modifier = Modifier.size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = theme.accentBrand,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column {
                Text(
                    label,
                    fontSize = 11.sp,
                    color = theme.textMuted,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    value,
                    fontSize = 14.sp,
                    color = theme.textPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun AeroTrackDropdown(
    label: String,
    selectedValue: String,
    options: List<String>,
    onItemSelected: (String) -> Unit,
    theme: ThemeColors
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = theme.textMuted, fontSize = 13.sp) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        Icons.Outlined.Place,
                        contentDescription = null,
                        tint = theme.accentBrand
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = theme.accentBrand,
                unfocusedBorderColor = theme.borderCard,
                focusedLabelColor = theme.accentBrand,
                focusedTextColor = theme.textPrimary,
                unfocusedTextColor = theme.textPrimary,
                cursorColor = theme.accentBrand,
                focusedContainerColor = theme.bgCard,
                unfocusedContainerColor = theme.bgCard
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            shape = RoundedCornerShape(16.dp),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { expanded = true }
        ) {}

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(theme.bgCard)
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            color = if (item == selectedValue) theme.accentBrand else theme.textPrimary,
                            fontWeight = if (item == selectedValue) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = theme.textPrimary
                    ),
                    modifier = Modifier.background(
                        if (item == selectedValue) theme.accentBrand.copy(
                            0.12f
                        ) else Color.Transparent
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EcoAlertScreenPreview() {
    EcoAlertScreen()
}
