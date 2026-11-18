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
import com.example.pruebasubicacion.util.colors.getAdaptiveThemeColors

// TODO funcion para evaluar las mejores horas del dia para hacer ejercicio (horas con menos contaminantes)

@Composable
fun ReporteAmbientalCard(
    theme: ThemeColors,
    climaEstado: ClimaEstado,
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

                // Aquí llamas al contenido interno pasándole el estado
                ContenidoReporteDeporte(theme = theme,climaEstado = climaEstado)
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

// Vista interna con la lista de contaminantes
@Composable
fun ContenidoReporteDeporte(
    theme: ThemeColors,
    climaEstado: ClimaEstado
) {
    val climaHourly = climaEstado.clima?.hourly
    val pm25 = climaHourly?.pm25?.firstOrNull() ?: 0f
    val pm10 = climaHourly?.pm10?.firstOrNull() ?: 0f
    val ozono3 = climaHourly?.ozone?.firstOrNull() ?: 0f
    val indiceUV = climaHourly?.uvIndex?.firstOrNull() ?: 0f
    //val indicePolen = climaHourly?.?.firstOrNull() ?: 0f

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

    Column {
        Text(text = "Condiciones Base (Deporte al aire libre)",
            fontWeight = FontWeight.Bold,
            color=theme.textMuted)
        Spacer(modifier = Modifier.height(8.dp))
        ItemContaminante("PM2.5 (Fino)", mensajePm25,theme)
        ItemContaminante("PM10 (Respirable)", mensajePm10,theme)
        ItemContaminante("Ozono (O3)", mensajeOzono3,theme)
        ItemContaminante("Índice UV", mensajeIndiceUV,theme)
        ItemContaminante("Polen", "Alto (Protégete)",theme)

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Recomendación: Ideal para entrenar antes de las 11:00 AM para evitar altos niveles de UV. Usa bloqueador solar.",
            style = MaterialTheme.typography.bodyMedium,
            color = theme.textMuted

        )
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
        onClose = {}
    )
}

