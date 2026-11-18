package com.example.pruebasubicacion.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebasubicacion.data.repository.AuthRepository
import com.example.pruebasubicacion.data.repository.AuthRepositoryImpl
import com.example.pruebasubicacion.data.repository.AuthUiState
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
    }

    fun loadAlergiasYEntornos() {
        viewModelScope.launch {
            try {
                val alergias = fetchAlergias()
                val entornos = fetchTiposEntorno()
                uiState = uiState.copy(
                    listaAlergias = if (alergias.isNotEmpty()) alergias else uiState.listaAlergias,
                    listaTiposEntorno = if (entornos.isNotEmpty()) entornos else uiState.listaTiposEntorno
                )
            } catch (e: Exception) {
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
        tieneAlergia: Boolean = false,
        fkIdAlergia: Int? = null,
        fkIdTipoCiudad: Int? = null,
        onSuccess: () -> Unit = {}
    ) {
        val min = 1
        val max = 6
        var tieneAlergiaFinal = tieneAlergia
        if (fkIdAlergia in min..max){
            tieneAlergiaFinal = true
        }else{
            tieneAlergiaFinal = false
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
                uiState.copy(isLoading = false, isLoggedIn = true)
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.localizedMessage ?: "Error desconocido"
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
                    val usuario =  fetchUsarioPorId(uid)
                    if (usuario != null){
                        uiState = uiState.copy(
                            nombre = usuario.nombre,
                            apellido = usuario.apellido,
                            alergia = usuario.alergia ?: "Ninguna",
                            tipoCiudad = usuario.tipoEntorno ?: "Urbana"
                        )
                    }
                }

                onSuccess()
                uiState = uiState.copy(isLoading = false, isLoggedIn = true)
            } else {
                uiState.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.localizedMessage ?: "Error desconocido"
                )
            }
        }
    }
}
