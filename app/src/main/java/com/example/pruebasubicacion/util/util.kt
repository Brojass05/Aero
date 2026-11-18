package com.example.pruebasubicacion.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebasubicacion.data.model.HourlyData
import com.example.pruebasubicacion.presentation.view.RiskInfo
import com.example.pruebasubicacion.presentation.view.ThemeColors
import com.google.android.gms.location.FusedLocationProviderClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Función de utilidad que recibe el contexto y el cliente
@SuppressLint("MissingPermission")
fun getLastKnownLocation(context: Context, fusedLocationClient: FusedLocationProviderClient) {
    try {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude
                    val msg = "Lat: ${lat}, Lon: ${lon}"
                    Log.d("LOCATION", msg)
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Ubicación nula. ¿GPS activo?", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    } catch (e: SecurityException) {
        Log.e("LOCATION", "Error de seguridad: ${e.message}")
    }
}

fun Log(tag: String = "ProyectoA", mensaje: String) {
    Log.d(tag, mensaje)
}

fun getTime() {
    val actual = LocalDateTime.now()
    Log.d("Hora", actual.toString())
}

object ChequeadorBackground {
    var segundoPlano: Boolean = false
}

/**
 * Suma horas a una lista de strings en formato ISO (con o sin zona horaria).
 * Si el formato es "yyyy-MM-dd'T'HH:mm", usa LocalDateTime.
 */
fun sumarHoras(listaHoras: List<String>, horasASumar: Int): List<String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val horasASumarN = horasASumar.toLong()

    return listaHoras.map { horaStr ->
        try {
            // Intentamos parsear con el formato específico (sin zona)
            LocalDateTime.parse(horaStr.trim(), formatter)
                .plusHours(horasASumarN)
                .format(formatter)
        } catch (e: Exception) {
            // Si falla, intentamos con el formato ISO estándar por si trae segundos o zona
            try {
                LocalDateTime.parse(horaStr.trim())
                    .plusHours(horasASumarN)
                    .format(formatter)
            } catch (e2: Exception) {
                horaStr // Devolvemos el original si  falla
            }
        }
    }
}


@Composable
fun GenerateReportButton(
    theme: ThemeColors,
    isLoggedIn: Boolean,
    onGenerate: () -> Unit
) {
    Button(
        onClick = onGenerate,
        colors = ButtonDefaults.buttonColors(containerColor = theme.accentBrand),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                Icons.Outlined.Assessment,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Text(
                "Generar Reporte Próximo Día",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}


// Mantengo esta por compatibilidad si se usa en otros sitios, pero la corrijo
fun sumarHorasUtc(listaHoras: List<String>, horasASumar: Int): List<String> {
    return sumarHoras(listaHoras, horasASumar)
}

// TODO: Generación de Informes y Recomendaciones Preventivas
/*
Criticas:
Particulado Fino (PM2.5)
Ozono (O3)
Particulado Respirable (PM10)
Indice UV (UV Index)
Segun diagnostico alergico o entorno especifico
Polenes
Alder
Birch
Grass
Mugwort
Olive
Ragweed
Monoxido de carbono (CO), Dioxido de nitrogeno (NO2)
tipicos en zonas urbanas y de alto trafico vehicular, usar en caso de estar en ciudad
Baja relevancia
Dioxido de carbono (CO2)
Aerosol Optical Depth (AOD)
Metano (CH4), Amoniaco (NH3)
* */
fun creaInforme(datosPorHora: HourlyData) {
    // TODO: Crear informe, usar plantilla de Ricardo para mostrar el informe
    Log("Boton Informe", "Creando informe")


}




