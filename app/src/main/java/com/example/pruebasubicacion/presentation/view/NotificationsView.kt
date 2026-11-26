package com.example.pruebasubicacion.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebasubicacion.data.repository.UserPreferences
import com.example.pruebasubicacion.data.repository.dataStore
import com.example.pruebasubicacion.util.colors.getAdaptiveThemeColors

@Composable
fun NotificationsView(theme: ThemeColors, onClose: () -> Unit) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context.applicationContext.dataStore) }
    val sendedNotifications by userPreferences.sendedNotificationFlow.collectAsState(initial = emptyList())

    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.dp, theme.borderCard)
    ) {
        Column(
            Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = theme.accentBrand,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Notificaciones Enviadas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = theme.textPrimary
                    )
                }
                IconButton(onClick = onClose) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = theme.textPrimary
                    )
                }
            }

            if (sendedNotifications.isEmpty()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No tienes notificaciones registradas",
                        color = theme.textMuted,
                        fontSize = 14.sp
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    for (id in sendedNotifications) {
                        when (id) {
                            "n1" -> NotificationItemAdaptive(
                                "Alerta de Contaminación",
                                "El nivel de contaminación ha subido. Ten cuidado si vas a realizar actividades al aire libre.",
                                theme
                            )

                            "n2" -> NotificationItemAdaptive(
                                "Mejora del Aire",
                                "El nivel de contaminación ha bajado favorablemente.",
                                theme
                            )

                            "n3" -> NotificationItemAdaptive(
                                "Estado Estable",
                                "El nivel de contaminación se mantiene sin cambios significativos.",
                                theme
                            )
                        }
                    }
                }
            }
        }
    }
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

@Preview(showBackground = true)
@Composable
fun NotificationsViewPreview() {
    val theme = getAdaptiveThemeColors(isDark = true)
    var showProfile by remember { mutableStateOf(false) }
    NotificationsView(
        theme = theme,
        onClose = { showProfile = false }
    )
}