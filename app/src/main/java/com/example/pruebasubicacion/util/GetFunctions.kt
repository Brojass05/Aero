package com.example.pruebasubicacion.util

import com.google.firebase.dataconnect.generated.ExampleConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance

/**
 * Función para obtener la lista de alergias desde Firebase Data Connect (PostgreSQL).
 */
suspend fun fetchAlergias(): List<String> {
    return try {
        val connector = ExampleConnector.instance
        val result = connector.getAlergias.execute()
        result.data.alergias.map { it.nombrealergia }
    } catch (_: Exception) {
        emptyList()
    }
}

/**
 * Función para obtener la lista de tipos de entorno desde Firebase Data Connect (PostgreSQL).
 */
suspend fun fetchTiposEntorno(): List<String> {
    return try {
        val connector = ExampleConnector.instance
        val result = connector.getTipoEntornos.execute()
        result.data.tipoEntornos.map { it.descripcion }
    } catch (_: Exception) {
        emptyList()
    }
}

data class UsuarioPerfil(
    val userid: String,
    val nombre: String,
    val apellido: String,
    val tieneAlergia: Boolean,
    val alergia: String?,
    val tipoEntorno: String?,
    val correo: String?
)

suspend fun fetchUsarioPorId(uid: String): UsuarioPerfil? {
    return try {
        val connector = ExampleConnector.instance
        val respuesta = connector.getUsuarioById.execute(userid = uid)
        val usuario = respuesta.data.usuario

        if (usuario != null) {
            android.util.Log.d(
                "FetchUsuario",
                "Usuario: ${usuario.nombre}, AlergiaObj: ${usuario.alergia}, EntornoObj: ${usuario.tipoEntorno}"
            )
            UsuarioPerfil(
                userid = usuario.userid,
                nombre = usuario.nombre,
                apellido = usuario.apellido,
                tieneAlergia = usuario.tienealergia,
                alergia = if (usuario.tienealergia) (usuario.alergia?.nombrealergia
                    ?: "Ninguna") else "Ninguna",
                tipoEntorno = usuario.tipoEntorno?.descripcion ?: "Urbana",
                correo = usuario.correo
            )
        } else {
            android.util.Log.w("FetchUsuario", "Usuario con uid $uid no encontrado en Data Connect")
            null
        }
    } catch (e: Exception) {
        android.util.Log.e("FetchUsuario", "Error al obtener usuario por id: ${e.message}", e)
        null
    }
}

suspend fun fetchFullUsarioPorId(uid: String): UsuarioPerfil? {
    return try {
        val connector = ExampleConnector.instance
        val respuesta = connector.getFullUsuarioById.execute(userid = uid)
        val usuario = respuesta.data.usuario

        if (usuario != null) {
            val listaAlergias = listOf(
                "Polvo de Aliso",
                "Polen de Abedul",
                "Polen de pasto",
                "Polen de Artemisa",
                "Polen de Olivo",
                "Polen de Ambrosia",
                "Sin Alergias / Ninguna"
            )
            val listaEntornos = listOf(
                "Zona Rural / Campo",
                "Zona Semiurbana / Pueblo",
                "Ciudad Media (Baja polución)",
                "Gran Ciudad / Metrópoli (Alta polución)",
                "Zona Industrial / Polución crítica"
            )

            val alergiaStr = if (usuario.tienealergia) {
                usuario.fkIdAlergia?.let { id -> listaAlergias.getOrNull(id - 1) } ?: "Ninguna"
            } else {
                "Ninguna"
            }
            val entornoStr =
                usuario.fkIdTipoCiudad?.let { id -> listaEntornos.getOrNull(id - 1) } ?: "Urbana"

            android.util.Log.d(
                "FetchUsuario",
                "FullUsuario: ${usuario.nombre}, fkAlergia: ${usuario.fkIdAlergia}, fkEntorno: ${usuario.fkIdTipoCiudad}"
            )
            UsuarioPerfil(
                userid = usuario.userid,
                nombre = usuario.nombre,
                apellido = usuario.apellido,
                tieneAlergia = usuario.tienealergia,
                alergia = alergiaStr,
                tipoEntorno = entornoStr,
                correo = usuario.correo
            )
        } else {
            android.util.Log.w("FetchUsuario", "Usuario con uid $uid no encontrado en Data Connect")
            null
        }
    } catch (e: Exception) {
        android.util.Log.e("FetchUsuario", "Error al obtener full usuario por id: ${e.message}", e)
        null
    }
}

