package com.example.pruebasubicacion.util.colors

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.pruebasubicacion.presentation.view.RiskInfo
import com.example.pruebasubicacion.presentation.view.ThemeColors

@Composable
fun getAdaptiveThemeColors(isDark: Boolean): ThemeColors {
    return if (isDark) {
        ThemeColors(
            bgPrimary = Color(0xFF090D16),     // Midnight Dark
            bgCard = Color(0xFF131C2E),        // Slate Card Dark
            bgSubtle = Color(0xFF1E293B),      // Surface Subtle Dark
            borderCard = Color(0xFF1E2D4A),    // Slate Border Dark
            textPrimary = Color(0xFFF8FAFC),   // Bright White
            textMuted = Color(0xFF94A3B8),     // Muted Slate
            accentBrand = Color(0xFF10B981),   // Radiant Emerald
            headerGradient = Brush.verticalGradient(listOf(Color(0xFF064E3B), Color(0xFF0F766E))),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White

        )
    } else {
        ThemeColors(
            bgPrimary = Color(0xFFF1F5F9),     // Slate 100 Light
            bgCard = Color(0xFFFFFFFF),        // Pure White
            bgSubtle = Color(0xFFF8FAFC),      // Soft Light Surface
            borderCard = Color(0xFFE2E8F0),    // Light Border
            textPrimary = Color(0xFF0F172A),   // Deep Dark Slate
            textMuted = Color(0xFF64748B),     // Cool Grey Muted
            accentBrand = Color(0xFF059669),   // Deep Emerald
            headerGradient = Brush.verticalGradient(listOf(Color(0xFF0D9488), Color(0xFF0284C7))),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    }
}

@Composable
fun getRiskInfoAdaptive(pm25: Float, isDark: Boolean): RiskInfo {
    return when {
        pm25 <= 12.0f -> RiskInfo(
            "Bueno",
            if (isDark) Color(0xFF10B981) else Color(0xFF059669),
            "La calidad del aire es satisfactoria y el riesgo es mínimo."
        )

        pm25 <= 35.4f -> RiskInfo(
            "Mala",
            if (isDark) Color(0xFFF59E0B) else Color(0xFFD97706),
            "Calidad aceptable, representa riesgo leve para personas sensibles."
        )

        pm25 <= 55.4f -> RiskInfo(
            "Riesgosa",
            if (isDark) Color(0xFFF97316) else Color(0xFFEA580C),
            "Grupos sensibles pueden experimentar problemas de salud."
        )

        pm25 <= 150.4f -> RiskInfo(
            "Insalubre",
            if (isDark) Color(0xFFEF4444) else Color(0xFFDC2626),
            "Riesgo aumentado de efectos respiratorios y cardíacos."
        )

        pm25 <= 250.4f -> RiskInfo(
            "Muy dañino",
            if (isDark) Color(0xFFA855F7) else Color(0xFF9333EA),
            "Alerta de salud; es probable que todos se vean afectados."
        )

        else -> RiskInfo(
            "Peligroso",
            if (isDark) Color(0xFFB91C1C) else Color(0xFF991B1B),
            "Situación de emergencia. Riesgos severos para todos."
        )
    }
}
