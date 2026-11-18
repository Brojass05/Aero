package com.example.pruebasubicacion

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.google.firebase.dataconnect.generated.ExampleConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataConnectTest {

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        if (FirebaseApp.getApps(appContext).isEmpty()) {
            FirebaseApp.initializeApp(appContext)
        }
    }

    @Test
    fun testGetAlergias() = runBlocking {
        try {
            val connector = ExampleConnector.instance
            val result = connector.getAlergias.execute()
            val alergias = result.data.alergias
            android.util.Log.d("DataConnectTest", "Alergias encontradas: ${alergias.size}")
            alergias.forEach { android.util.Log.d("DataConnectTest", " - ${it.idalergia}: ${it.nombrealergia}") }
            assertNotNull(alergias)
        } catch (e: Exception) {
            android.util.Log.e("DataConnectTest", "Error en testGetAlergias: ${e.message}", e)
            throw e
        }
    }

    @Test
    fun testGetTipoEntornos() = runBlocking {
        try {
            val connector = ExampleConnector.instance
            val result = connector.getTipoEntornos.execute()
            val entornos = result.data.tipoEntornos
            android.util.Log.d("DataConnectTest", "Tipos de Entorno encontrados: ${entornos.size}")
            entornos.forEach { android.util.Log.d("DataConnectTest", " - ${it.idtipoentorno}: ${it.descripcion}") }
            assertNotNull(entornos)
        } catch (e: Exception) {
            android.util.Log.e("DataConnectTest", "Error en testGetTipoEntornos: ${e.message}", e)
            throw e
        }
    }
    @Test
    fun testGetUsuarioById() = runBlocking<Unit> {
        val uid = "FDS317G1aYQGSixO74V81HLiUoS2"
        try {
            val connector = ExampleConnector.instance
            val result = connector.getUsuarioById.execute(userid = uid)
            val usuario = result.data.usuario

            assertNotNull(usuario)

            if (usuario != null) {
                android.util.Log.d("DataConnectTest", "Usuario encontrado: ${usuario.nombre} ${usuario.apellido}")
                android.util.Log.d("DataConnectTest", " - Correo: ${usuario.correo}")
                android.util.Log.d("DataConnectTest", " - Alergia: ${usuario.alergia?.nombrealergia ?: "Sin registro"}")
                android.util.Log.d("DataConnectTest", " - Entorno: ${usuario.tipoEntorno?.descripcion ?: "Sin registro"}")
            } else {
                android.util.Log.w("DataConnectTest", "No se encontró ningún usuario con el ID: $uid")
            }
        } catch (e: Exception) {
            android.util.Log.e("DataConnectTest", "Error en testGetUsuarioById: ${e.message}", e)
            throw e
        }
    }
    @Test
    fun testGetUsuarios() {
        runBlocking {
            try {
                val connector = ExampleConnector.instance
                val result = connector.getUsuarios.execute()
                val usuarios = result.data.usuarios

                android.util.Log.d("DataConnectTest", "Total usuarios encontrados: ${usuarios.size}")
                usuarios.forEach { user ->
                    android.util.Log.d(
                        "DataConnectTest",
                        " - [${user.userid}] ${user.nombre} ${user.apellido} (${user.correo}) | Alergia: ${user.fkIdAlergia ?: "Sin registro"} | Entorno: ${user.fkIdTipoCiudad ?: "Sin registro"}"
                    )
                }
                assertNotNull(usuarios)
            } catch (e: Exception) {
                android.util.Log.e("DataConnectTest", "Error en testGetUsuarios: ${e.message}", e)
                throw e
            }
        }
    }

    @Test
    fun testGetFullUsuarioById() {
        runBlocking {
            val uid = "FDS317G1aYQGSixO74V81HLiUoS2"
            try {
                val connector = ExampleConnector.instance
                val result = connector.getFullUsuarioById.execute(userid = uid)
                val usuario = result.data.usuario

                assertNotNull(usuario)

                if (usuario != null) {
                    android.util.Log.d("DataConnectTest", "Usuario encontrado: ${usuario.nombre} ${usuario.apellido}")
                    android.util.Log.d("DataConnectTest", " - Correo: ${usuario.correo}")
                    android.util.Log.d("DataConnectTest", " - Alergia: ${usuario.fkIdAlergia ?: "Sin registro"}")
                    android.util.Log.d("DataConnectTest", " - Entorno: ${usuario.fkIdTipoCiudad ?: "Sin registro"}")
                } else {
                    android.util.Log.w("DataConnectTest", "No se encontró ningún usuario con el ID: $uid")
                }
            } catch (e: Exception) {
                android.util.Log.e("DataConnectTest", "Error en testGetFullUsuarioById: ${e.message}", e)
                throw e
            }
        }
    }
}
