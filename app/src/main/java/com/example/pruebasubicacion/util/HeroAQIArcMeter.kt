package com.example.pruebasubicacion.util

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebasubicacion.data.model.ClimaEstado
import com.example.pruebasubicacion.presentation.view.getRiskIconForPM25
import com.example.pruebasubicacion.util.colors.getRiskInfoAdaptive


@Composable
fun HeroAQIArcMeter(estado: ClimaEstado, selectedTab: Int, isDark: Boolean) {
    val pm25 = estado.clima?.hourly?.pm25?.firstOrNull() ?: 0f
    val listaPm25 = listOf(pm25, 10f, 35f, 55f, 150f, 250f, 300f)
    var currentIndex by remember { mutableIntStateOf(0) }

    val activePm25 = listaPm25[currentIndex]
    val risk = getRiskInfoAdaptive(activePm25, isDark)

    val recommendation = if (selectedTab == 0) {
        when {
            activePm25 <= 12f -> "Condiciones óptimas para realizar entrenamientos de alto rendimiento al aire libre."
            activePm25 <= 35.4f -> "Calidad aceptable. Si realizas rutinas extensas o de alta intensidad, mantente hidratado."
            else -> "Altos niveles de contaminantes. Se aconseja trasladar la sesión a espacios cerrados."
        }
    } else {
        when {
            activePm25 <= 12f -> "Entorno seguro y óptimo para vías respiratorias sensibles."
            activePm25 <= 35.4f -> "Sensibilidad leve detectada. Ten a la mano tus medicamentos preventivos."
            else -> "Calidad desfavorable. Minimiza la exposición al aire libre y usa protección."
        }
    }

    val animatedColor by animateColorAsState(
        targetValue = risk.color,
        animationSpec = tween(durationMillis = 350),
        label = "riskColorAnimation"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = animatedColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.18f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.12f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(26.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Header con insignia Elite
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(0.25f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    getRiskIconForPM25(activePm25),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                "CALIDAD DEL AIRE",
                                color = Color.White.copy(0.85f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.5.sp
                            )
                            Text(
                                "MODO ATLETA PRO",
                                color = Color.White.copy(0.7f),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White.copy(0.3f),
                        border = BorderStroke(1.dp, Color.White.copy(0.4f))
                    ) {
                        Text(
                            text = risk.category.uppercase(),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Métrica Central PM2.5 (Interactiva)
                TextButton(
                    onClick = {
                        currentIndex = (currentIndex + 1) % listaPm25.size
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "$activePm25",
                                color = Color.White,
                                fontSize = 52.sp,
                                fontWeight = FontWeight.Black,
                                lineHeight = 52.sp
                            )
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.padding(bottom = 6.dp)) {
                                Text(
                                    text = "µg/m³",
                                    color = Color.White.copy(0.9f),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "PM2.5 (Toca para simular)",
                                    color = Color.White.copy(0.7f),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // Tarjeta de Recomendación / Mensaje Combinado Glassmorphism Elite Refinado
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = Color.White.copy(0.18f),
                    border = BorderStroke(1.dp, Color.White.copy(0.35f))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(0.25f),
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Outlined.FitnessCenter,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Text(
                                text = "ESTADO Y RECOMENDACIÓN ATLETA PRO",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }

                        Text(
                            text = risk.message,
                            color = Color.White,
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = recommendation,
                            color = Color.White.copy(0.95f),
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
