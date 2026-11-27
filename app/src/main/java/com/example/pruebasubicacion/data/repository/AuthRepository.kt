package com.example.pruebasubicacion.data.repository

import com.example.pruebasubicacion.data.datasource.FirebaseAuthDataSource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.dataconnect.generated.ExampleConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance

interface AuthRepository {
    suspend fun iniciarSesion(correo: String, pass: String): Result<AuthResult?>
    suspend fun registrarUsuario(
        correo: String,
        pass: String,
        nombre: String,
        apellido: String,
        tieneAlergia: Boolean,
        fkIdAlergia: Int?,
        fkIdTipoCiudad: Int?
    ): Result<AuthResult?>
    suspend fun actualizarUsuario(
        userid: String,
        nombre: String,
        apellido: String,
        tieneAlergia: Boolean,
        fkIdAlergia: Int?,
        fkIdTipoCiudad: Int?,
        correo: String
    ): Result<Unit>

    fun getCurrentUser(): FirebaseUser?
    fun logout()
}

class AuthRepositoryImpl(
    private val authDataSource: FirebaseAuthDataSource = FirebaseAuthDataSource()
) : AuthRepository {
    private val connector = ExampleConnector.instance

    override suspend fun iniciarSesion(correo: String, pass: String): Result<AuthResult?> {
        return try {
            val result = authDataSource.login(correo, pass)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registrarUsuario(
        correo: String,
        pass: String,
        nombre: String,
        apellido: String,
        tieneAlergia: Boolean,
        fkIdAlergia: Int?,
        fkIdTipoCiudad: Int?
    ): Result<AuthResult?> {
        return try {
            val authResult = authDataSource.signUp(correo, pass)
            val userId = authResult?.user?.uid ?: correo

            // Guardar usuario en PostgreSQL vía Firebase Data Connect
            android.util.Log.d(
                "AuthRepository",
                "Registrando usuario $userId con fkIdAlergia=$fkIdAlergia, fkIdTipoCiudad=$fkIdTipoCiudad"
            )
            connector.upsertUsuario.execute(
                userid = userId,
                nombre = nombre,
                apellido = apellido,
                tienealergia = tieneAlergia,
                correo = correo
            ) {
                this.fkIdAlergia = fkIdAlergia
                this.fkIdTipoCiudad = fkIdTipoCiudad
            }


            Result.success(authResult)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun actualizarUsuario(
        userid: String,
        nombre: String,
        apellido: String,
        tieneAlergia: Boolean,
        fkIdAlergia: Int?,
        fkIdTipoCiudad: Int?,
        correo: String
    ): Result<Unit> {
        return try {
            connector.upsertUsuario.execute(
                userid = userid,
                nombre = nombre,
                apellido = apellido,
                tienealergia = tieneAlergia,
                correo = correo
            ) {
                this.fkIdAlergia = fkIdAlergia
                this.fkIdTipoCiudad = fkIdTipoCiudad
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser() = authDataSource.getCurrentUser()
    override fun logout() = authDataSource.logout()
}

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val regEmail: String = "",
    val regPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false,
    val nombre: String = "",
    val apellido: String = "",
    val userEmail: String = "",
    val tieneAlergia: Boolean = false,
    val alergia: String = "",
    val tipoCiudad: String = "",
    val listaAlergias: List<String> = listOf(
        "Polvo de Aliso",
        "Polen de Abedul",
        "Polen de pasto",
        "Polen de Artemisa",
        "Polen de Olivo",
        "Polen de Ambrosia",
        "Sin Alergias / Ninguna"
    ),
    val listaTiposEntorno: List<String> = listOf(
        "Zona Rural / Campo",
        "Zona Semiurbana / Pueblo",
        "Ciudad Media (Baja polución)",
        "Gran Ciudad / Metrópoli (Alta polución)",
        "Zona Industrial / Polución crítica"
    ),
    val correo: String? = null
)
