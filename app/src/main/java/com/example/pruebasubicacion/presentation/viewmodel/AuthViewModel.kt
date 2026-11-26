package com.example.pruebasubicacion.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebasubicacion.data.repository.AuthRepository
import com.example.pruebasubicacion.data.repository.AuthRepositoryImpl
import com.example.pruebasubicacion.data.repository.AuthUiState
import com.example.pruebasubicacion.util.errorList
import com.example.pruebasubicacion.util.fetchAlergias
import com.example.pruebasubicacion.util.fetchTiposEntorno
import com.example.pruebasubicacion.util.fetchUsarioPorId
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {
    var uiState by mutableStateOf(AuthUiState())
        private set

    init {
        loadAlergiasYEntornos()
        checkExistingSession()
    }

    private fun checkExistingSession() {
        viewModelScope.launch {
            try {
                val currentUser = authRepository.getCurrentUser()
                if (currentUser != null) {
                    val uid = currentUser.uid
                    val usuario = fetchUsarioPorId(uid)
                    if (usuario != null) {
                        uiState = uiState.copy(
                            isLoggedIn = true,
                            nombre = usuario.nombre,
                            apellido = usuario.apellido,
                            tieneAlergia = usuario.tieneAlergia,
                            alergia = usuario.alergia ?: "Ninguna",
                            tipoCiudad = usuario.tipoEntorno ?: "Urbana"
                        )
                    }
                }
            } catch (_: Exception) {
                // Ignore network errors on session check
            }
        }
    }

    fun loadAlergiasYEntornos() {
        viewModelScope.launch {
            try {
                val alergias = fetchAlergias()
                val entornos = fetchTiposEntorno()
                uiState.copy(
                    listaAlergias = alergias.ifEmpty { uiState.listaAlergias },
                    listaTiposEntorno = entornos.ifEmpty { uiState.listaTiposEntorno }
                ).also { uiState = it }
            } catch (_: Exception) {
                // Mantener listas por defecto si falla la red
            }
        }
    }

    fun onEmailChanged(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun onRegEmailChanged(email: String) {
        uiState = uiState.copy(regEmail = email)
    }

    fun onRegPasswordChanged(password: String) {
        uiState = uiState.copy(regPassword = password)
    }

    fun register(
        nombre: String = "Usuario",
        apellido: String = "App",
        fkIdAlergia: Int? = null,
        fkIdTipoCiudad: Int? = null,
        onSuccess: () -> Unit = {}
    ) {
        val min = 1
        val max = 6
        val tieneAlergiaFinal: Boolean = if (fkIdAlergia in min..max) {
            true
        } else {
            false
        }
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            val result = authRepository.registrarUsuario(
                uiState.regEmail,
                uiState.regPassword,
                nombre = nombre,
                apellido = apellido,
                tieneAlergia = tieneAlergiaFinal,
                fkIdAlergia = fkIdAlergia,
                fkIdTipoCiudad = fkIdTipoCiudad
            )

            uiState = if (result.isSuccess) {
                onSuccess()
                uiState.copy(isLoading = false, isLoggedIn = true, regEmail = "", regPassword = "")
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = traducirErrorFirebase(result.exceptionOrNull())
                )
            }
        }
    }

    fun login(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            val result = authRepository.iniciarSesion(uiState.email, uiState.password)

            if (result.isSuccess) {
                val uid = authRepository.getCurrentUser()?.uid

                if (uid != null) {
                    val usuario = fetchUsarioPorId(uid)
                    if (usuario != null) {
                        uiState = uiState.copy(
                            nombre = usuario.nombre,
                            apellido = usuario.apellido,
                            tieneAlergia = usuario.tieneAlergia,
                            alergia = usuario.alergia ?: "Ninguna",
                            tipoCiudad = usuario.tipoEntorno ?: "Urbana"
                        )
                    }
                }

                onSuccess()
                uiState =
                    uiState.copy(isLoading = false, isLoggedIn = true, email = "", password = "")
            } else {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = traducirErrorFirebase(result.exceptionOrNull())
                )
            }
        }
    }

    private fun traducirErrorFirebase(exception: Throwable?): String {
        val mensaje = exception?.localizedMessage ?: exception?.message ?: ""

        for ((key, value) in errorList) {
            if (mensaje.contains(key, ignoreCase = true)) {
                return value
            }
        }

        return when {
            mensaje.contains("empty or null", ignoreCase = true) || mensaje.contains(
                "blank",
                ignoreCase = true
            ) ->
                "Debe completar todos los campos obligatorios."

            mensaje.contains("network", ignoreCase = true) || mensaje.contains(
                "timeout",
                ignoreCase = true
            ) ->
                "Error de conexión. Compruebe su acceso a Internet."

            mensaje.isNotBlank() -> "No se pudo completar la operación. Verifique sus datos."
            else -> "Ocurrió un error inesperado. Intente nuevamente."
        }
    }

    fun logout() {
        authRepository.logout()
        uiState = AuthUiState(
            email = "",
            password = "",
            regEmail = "",
            regPassword = "",
            listaAlergias = uiState.listaAlergias,
            listaTiposEntorno = uiState.listaTiposEntorno
        )
    }
}
