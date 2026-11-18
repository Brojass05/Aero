package com.example.pruebasubicacion.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebasubicacion.data.model.ClimaEstado
import com.example.pruebasubicacion.presentation.view.ThemeColors
import com.example.pruebasubicacion.util.colors.getRiskInfoAdaptive
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WeeklyForecast(estado: ClimaEstado, theme: ThemeColors, isDark: Boolean) {
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
                        "Pronóstico Próximos Días",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = theme.textPrimary
                    )
                }
            }

            val hourlyTimes = clima.hourly.time
            val pm25Values = clima.hourly.pm25 ?: emptyList()
            val tempValues = clima.hourly.temperature ?: emptyList()
            val humValues = clima.hourly.humidity ?: emptyList()

            val dayGroups = hourlyTimes.indices.groupBy { index ->
                hourlyTimes[index].substringBefore("T")
            }.values.toList()

            dayGroups.take(7).forEachIndexed { i, indices ->
                val dayPm = indices.map { pm25Values.getOrNull(it) ?: 0f }
                val dayTemp = indices.map { tempValues.getOrNull(it) ?: 28f }
                val dayHum = indices.map { humValues.getOrNull(it) ?: 50f }

                val avgPm = if (dayPm.isNotEmpty()) dayPm.average().toFloat() else 0f
                val maxTemp = if (dayTemp.isNotEmpty()) dayTemp.maxOrNull()?.toInt() ?: 28 else 28
                val minTemp = if (dayTemp.isNotEmpty()) dayTemp.minOrNull()?.toInt() ?: 14 else 14
                val avgHum = if (dayHum.isNotEmpty()) dayHum.average().toInt() else 50

                val date = LocalDateTime.parse(
                    hourlyTimes[indices.first()],
                    DateTimeFormatter.ISO_DATE_TIME
                )
                val label = if (i == 0) "Hoy" else date.format(
                    DateTimeFormatter.ofPattern("EEE")
                )
                val risk = getRiskInfoAdaptive(avgPm, isDark)

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = theme.bgSubtle,
                    border = BorderStroke(1.dp, theme.borderCard.copy(0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Día
                        Text(
                            text = label.uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = theme.textPrimary,
                            modifier = Modifier.width(45.dp)
                        )

                        // Humedad / Condición
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                Icons.Outlined.WaterDrop,
                                contentDescription = null,
                                tint = theme.accentBrand,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "$avgHum%",
                                fontSize = 12.sp,
                                color = theme.textMuted,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Badge con valor y unidad integrados
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = risk.color.copy(0.2f),
                            border = BorderStroke(1.dp, risk.color.copy(0.5f))
                        ) {
                            Box(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${avgPm.toInt()} µg",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = risk.color
                                )
                            }
                        }

                        // Rango de Temperaturas (Máx / Mín estilo app clima pro)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$maxTemp°",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = theme.textPrimary
                            )
                            Text(
                                text = "$minTemp°",
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                color = theme.textMuted
                            )
                        }
                    }
                }
            }
        }
    }
}
