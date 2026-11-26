package com.example.pruebasubicacion.util

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.outlined.Place
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

@Composable
fun LocationCardAdaptive(estado: ClimaEstado, theme: ThemeColors) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = theme.bgCard,
        border = BorderStroke(1.dp, theme.borderCard),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = theme.accentBrand.copy(0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Outlined.Place,
                            contentDescription = null,
                            tint = theme.accentBrand,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        "ESTACIÓN ACTIVA",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textMuted,
                        letterSpacing = 1.sp
                    )
                    Text(
                        estado.nombreUbicacion ?: "Obteniendo ubicación...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary
                    )
                }
            }

            Surface(
                shape = CircleShape,
                color = theme.accentBrand.copy(0.12f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = theme.accentBrand,
                        modifier = Modifier.size(6.dp)
                    ) {}
                    Text(
                        "En vivo",
                        fontSize = 11.sp,
                        color = theme.accentBrand,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
