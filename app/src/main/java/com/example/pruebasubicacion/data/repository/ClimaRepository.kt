package com.example.pruebasubicacion.data.repository

import com.example.pruebasubicacion.data.model.ClimaModel
import com.example.pruebasubicacion.data.model.NominatimServicioAPI
import com.example.pruebasubicacion.data.model.ServicioAPI
import com.example.pruebasubicacion.data.model.WeatherServicioAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ClimaRepository {
    private val servicioAir = ServicioAPI.getInstance()
    private val servicioWeather = WeatherServicioAPI.getInstance()
    private val servicioNominatim = NominatimServicioAPI.getInstance()

    suspend fun getClimaCompleto(lat: Double, lon: Double): ClimaData {
        return withContext(Dispatchers.IO) {
            val airQualityDeferred = async { servicioAir.getAirQuality(lat, lon) }
            val weatherDeferred = async { servicioWeather.getWeatherData(lat, lon) }
            val locationDeferred = async { servicioNominatim.obtenerDireccion(lat, lon) }

            val airData = airQualityDeferred.await()
            val weatherData = weatherDeferred.await()
            val locationData = locationDeferred.await()

            android.util.Log.d("ClimaRepository", "=== DATOS OBTENIDOS DE LA API ===")
            android.util.Log.d("ClimaRepository", "Ubicación: ${locationData.address?.getNombreUbicacion()}")
            android.util.Log.d("ClimaRepository", "PM2.5: ${airData.hourly.pm25?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "PM10: ${airData.hourly.pm10?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Ozono: ${airData.hourly.ozone?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Índice UV: ${airData.hourly.uvIndex?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Polen Aliso (alder): ${airData.hourly.alderPollen?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Polen Abedul (birch): ${airData.hourly.birchPollen?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Polen Pasto (grass): ${airData.hourly.grassPollen?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Polen Artemisa (mugwort): ${airData.hourly.mugwortPollen?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Polen Olivo (olive): ${airData.hourly.olivePollen?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Polen Ambrosia (ragweed): ${airData.hourly.ragweedPollen?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Temperatura: ${weatherData.hourly.temperature?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "Humedad: ${weatherData.hourly.humidity?.firstOrNull()}")
            android.util.Log.d("ClimaRepository", "=================================")

            val combinedClima = airData.copy(
                hourly = airData.hourly.copy(
                    humidity = weatherData.hourly.humidity,
                    temperature = weatherData.hourly.temperature
                )
            )

            ClimaData(
                clima = combinedClima,
                nombreUbicacion = locationData.address?.getNombreUbicacion()
                    ?: "Ubicación desconocida",
                pm25 = airData.hourly.pm25?.firstOrNull() ?: 0f
            )
        }
    }

    suspend fun getPm25Only(lat: Double, lon: Double): Float {
        return withContext(Dispatchers.IO) {
            val airData = servicioAir.getAirQuality(lat, lon)
            airData.hourly.pm25?.firstOrNull() ?: 0f
        }
    }
}

data class ClimaData(
    val clima: ClimaModel,
    val nombreUbicacion: String,
    val pm25: Float
)
