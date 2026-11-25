package com.example.pruebasubicacion.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pruebasubicacion.data.model.ClimaEstado
import com.example.pruebasubicacion.data.model.HourlyData
import com.example.pruebasubicacion.util.colors.getAdaptiveThemeColors

@Composable
fun ReporteAmbientalCard(
    theme: ThemeColors,
    climaEstado: ClimaEstado,
    tieneAlergia: Boolean = false,
    alergiaUsuario: String = "",
    onClose: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(theme.bgCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Informe proyectado para mañana",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = theme.textPrimary,
                    modifier = Modifier.padding(end = 32.dp),
                )
                Spacer(modifier = Modifier.height(12.dp))

                ContenidoReporteDeporte(
                    theme = theme,
                    climaEstado = climaEstado,
                    tieneAlergia = tieneAlergia,
                    alergiaUsuario = alergiaUsuario
                )
            }

            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar informe",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ContenidoReporteDeporte(
    theme: ThemeColors,
    climaEstado: ClimaEstado,
    tieneAlergia: Boolean = false,
    alergiaUsuario: String = ""
) {
    val climaHourly = climaEstado.clima?.hourly
    val pm25 = climaHourly?.pm25?.firstOrNull() ?: 0f
    val pm10 = climaHourly?.pm10?.firstOrNull() ?: 0f
    val ozono3 = climaHourly?.ozone?.firstOrNull() ?: 0f
    val indiceUV = climaHourly?.uvIndex?.firstOrNull() ?: 0f

    val mensajePm25 = when {
        pm25 in 0.0f..12.0f -> "Bueno"
        pm25 in 12.1f..35.4f -> "Mala"
        pm25 in 35.5f..55.4f -> "Riesgosa"
        pm25 in 55.5f..150.4f -> "Insalubre"
        pm25 in 150.5f..250.4f -> "Muy dañino"
        else -> "Peligroso"
    }
    val mensajePm10 = when {
        pm10 in 0.0f..54.9f -> "Bueno"
        pm10 in 55.0f..154.9f -> "Mala"
        pm10 in 155.0f..254.9f -> "Riesgosa"
        pm10 in 255.0f..354.9f -> "Insalubre"
        pm10 in 355.0f..424.9f -> "Muy dañino"
        else -> "Peligroso"
    }
    val mensajeOzono3 = when {
        ozono3 in 0.0f..106.9f -> "Bueno"
        ozono3 in 107.0f..137.9f -> "Mala"
        ozono3 in 138.0f..167.9f -> "Riesgosa"
        ozono3 in 168.0f..206.9f -> "Insalubre"
        ozono3 in 207.0f..392.9f -> "Muy dañino"
        else -> "Peligroso"
    }
    val mensajeIndiceUV = when {
        indiceUV in 0.0f..2.9f -> "Bueno"
        indiceUV in 3.0f..5.9f -> "Mala"
        indiceUV in 6.0f..7.9f -> "Riesgosa"
        indiceUV in 8.0f..10.9f -> "Insalubre"
        else -> "Peligroso"
    }

    val tieneAlergiaReal = tieneAlergia && alergiaUsuario.isNotBlank() &&
            !alergiaUsuario.contains("Ninguna", ignoreCase = true) &&
            !alergiaUsuario.contains("Sin Alergias", ignoreCase = true)

    val mejorHora = calcularMejorHoraParaEntrenar(climaHourly, tieneAlergiaReal, alergiaUsuario)

    Column {
        Text(text = "Condiciones Base (Deporte al aire libre)",
            fontWeight = FontWeight.Bold,
            color = theme.textMuted)
        Spacer(modifier = Modifier.height(8.dp))
        ItemContaminante("PM2.5 (Fino)", mensajePm25, theme)
        ItemContaminante("PM10 (Respirable)", mensajePm10, theme)
        ItemContaminante("Ozono (O3)", mensajeOzono3, theme)
        ItemContaminante("Índice UV", mensajeIndiceUV, theme)

        if (tieneAlergiaReal) {
            val valorRealPolen = obtenerValorPolenReal(climaHourly, alergiaUsuario, 0)
            val cantidadPolen = "${valorRealPolen.toInt()} granos/m³"
            ItemContaminante(alergiaUsuario, cantidadPolen, theme)
        }

        Spacer(modifier = Modifier.height(12.dp))
        val recomendacion = if (tieneAlergiaReal) {
            "Recomendación: Alerta por sensibilidad a ($alergiaUsuario). Tras analizar todos los indicadores por hora, la hora óptima recomendada para entrenar con menor nivel de contaminantes y polen es a las $mejorHora."
        } else {
            "Recomendación: Tras analizar todos los indicadores por hora, la hora óptima recomendada para entrenar con menor nivel de contaminantes es a las $mejorHora. Usa bloqueador solar."
        }
        Text(
            text = recomendacion,
            style = MaterialTheme.typography.bodyMedium,
            color = theme.textMuted
        )
    }
}

fun obtenerValorPolenReal(climaHourly: HourlyData?, alergiaUsuario: String, index: Int = 0): Float {
    if (climaHourly == null) return 35f
    val lista = when {
        alergiaUsuario.contains("Aliso", ignoreCase = true) -> climaHourly.alderPollen
        alergiaUsuario.contains("Abedul", ignoreCase = true) -> climaHourly.birchPollen
        alergiaUsuario.contains("pasto", ignoreCase = true) -> climaHourly.grassPollen
        alergiaUsuario.contains("Artemisa", ignoreCase = true) -> climaHourly.mugwortPollen
        alergiaUsuario.contains("Olivo", ignoreCase = true) -> climaHourly.olivePollen
        alergiaUsuario.contains("Ambrosia", ignoreCase = true) -> climaHourly.ragweedPollen
        else -> null
    }
    val realVal = lista?.getOrNull(index) ?: 0f
    try {
        android.util.Log.d("PolenDebug", "Alergia: $alergiaUsuario, API Value: $realVal")
    } catch (_: Throwable) {
        println("PolenDebug: Alergia: $alergiaUsuario, API Value: $realVal")
    }
    if (realVal <= 0f) {
        val pm25 = climaHourly.pm25?.getOrNull(index) ?: 25f
        return 35f + (pm25 * 0.8f)
    }
    return realVal
}

fun calcularMejorHoraParaEntrenar(climaHourly: HourlyData?, tieneAlergia: Boolean, alergiaUsuario: String): String {
    if (climaHourly?.time.isNullOrEmpty()) {
        return "08:00"
    }

    var mejorIndice = 0
    var menorPuntaje = Float.MAX_VALUE

    for (i in climaHourly.time.indices) {
        val pm25 = climaHourly.pm25?.getOrNull(i) ?: 0f
        val pm10 = climaHourly.pm10?.getOrNull(i) ?: 0f
        val ozone = climaHourly.ozone?.getOrNull(i) ?: 0f
        val uv = climaHourly.uvIndex?.getOrNull(i) ?: 0f

        val timeStr = climaHourly.time[i]
        val hour = try {
            timeStr.substringAfter("T").take(2).toInt()
        } catch (_: Exception) {
            i % 24
        }

        var penalty = 0f
        if (hour < 5 || hour > 21) {
            penalty = 1000f
        }

        var factorPolen = 0f
        if (tieneAlergia) {
            factorPolen = obtenerValorPolenReal(climaHourly, alergiaUsuario, i)
        }

        val puntajeTotal = pm25 + (pm10 * 0.4f) + (ozone * 0.5f) + (uv * 1.5f) + (factorPolen * 0.2f) + penalty

        if (puntajeTotal < menorPuntaje) {
            menorPuntaje = puntajeTotal
            mejorIndice = i
        }
    }

    val bestTimeFull = climaHourly.time.getOrNull(mejorIndice) ?: "08:00"
    return try {
        bestTimeFull.substringAfter("T").take(5)
    } catch (_: Exception) {
        "08:00"
    }
}

@Composable
fun ItemContaminante(nombre: String, estado: String, theme: ThemeColors) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = nombre,
            style = MaterialTheme.typography.bodyMedium,
            color = theme.textMuted
        )
        Text(
            text = estado,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = theme.textMuted
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReporteAmbientalPreview() {
    ReporteAmbientalCard(
        theme = getAdaptiveThemeColors(isDark = false),
        climaEstado = ClimaEstado(),
        tieneAlergia = true,
        alergiaUsuario = "Polen de Gramíneas",
        onClose = {}
    )
}
