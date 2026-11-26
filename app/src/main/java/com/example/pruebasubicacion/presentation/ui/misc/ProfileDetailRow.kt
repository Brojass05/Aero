package com.example.pruebasubicacion.presentation.ui.misc

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebasubicacion.presentation.view.ThemeColors


@Composable
fun ProfileDetailRow(
    label: String,
    value: String,
    icon: ImageVector,
    theme: ThemeColors
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = theme.bgSubtle,
        border = BorderStroke(1.dp, theme.borderCard),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = theme.accentBrand,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    label,
                    fontSize = 13.sp,
                    color = theme.textMuted,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(value, fontSize = 14.sp, color = theme.textPrimary, fontWeight = FontWeight.Bold)
        }
    }
}