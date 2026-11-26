package com.example.pruebasubicacion.presentation.ui.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebasubicacion.presentation.view.ThemeColors
import com.example.pruebasubicacion.util.colors.getAdaptiveThemeColors

@Composable
fun TopDashboardBar(
    theme: ThemeColors,
    isLoggedIn: Boolean,
    showNotifications: Boolean,
    showProfile: Boolean,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = theme.accentBrand,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Box(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text(
                        "AERO",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
            Text(
                "Track",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = theme.textPrimary,
                letterSpacing = (-0.5).sp
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        if (showNotifications) theme.accentBrand.copy(0.2f) else theme.bgCard,
                        CircleShape
                    )

            ) {
                Icon(
                    Icons.Outlined.NotificationsActive,
                    contentDescription = "Notificaciones",
                    tint = if (showNotifications) theme.accentBrand else theme.textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Botón de Cuenta / Perfil de Usuario
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        if (showProfile || isLoggedIn) theme.accentBrand.copy(0.2f) else theme.bgCard,
                        CircleShape
                    )


            ) {
                Icon(
                    Icons.Outlined.AccountCircle,
                    contentDescription = "Perfil de usuario",
                    tint = if (showProfile || isLoggedIn) theme.accentBrand else theme.textPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TopDashboardBarPreview() {
    TopDashboardBar(
        theme = getAdaptiveThemeColors(isDark = false),
        isLoggedIn = true,
        showNotifications = true,
        showProfile = true,
        onNotificationClick = {},
        onProfileClick = {}
    )
}
