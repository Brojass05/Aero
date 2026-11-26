package com.example.pruebasubicacion.data.model

import com.google.gson.annotations.SerializedName

data class ClimaModel(
    val latitude: Double,
    val longitude: Double,
    val hourly: HourlyData
)

data class HourlyData(
    val time: List<String>,
    @SerializedName("pm2_5") val pm25: List<Float>?,
    @SerializedName("pm10") val pm10: List<Float>?,
    @SerializedName("nitrogen_dioxide") val nitrogenDioxide: List<Float>?,
    @SerializedName("ozone") val ozone: List<Float>?,
    @SerializedName("sulphur_dioxide") val sulphurDioxide: List<Float>?,
    @SerializedName("carbon_monoxide") val carbonMonoxide: List<Float>?,
    @SerializedName("carbon_dioxide") val carbonDioxide: List<Float>?,
    @SerializedName("uv_index") val uvIndex: List<Float>?,
    @SerializedName("relative_humidity_2m") val humidity: List<Float>?,
    @SerializedName("temperature_2m") val temperature: List<Float>?,
    @SerializedName("surface_pressure") val surfacePressure: List<Float>? = null,
    @SerializedName("alder_pollen") val alderPollen: List<Float>? = null,
    @SerializedName("birch_pollen") val birchPollen: List<Float>? = null,
    @SerializedName("grass_pollen") val grassPollen: List<Float>? = null,
    @SerializedName("mugwort_pollen") val mugwortPollen: List<Float>? = null,
    @SerializedName("olive_pollen") val olivePollen: List<Float>? = null,
    @SerializedName("ragweed_pollen") val ragweedPollen: List<Float>? = null
)
