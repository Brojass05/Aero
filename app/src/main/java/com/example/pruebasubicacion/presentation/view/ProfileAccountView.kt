package com.example.pruebasubicacion.presentation.view
// TODO Obtener nombre apellido alergia y entorno al iniciar sesion


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pruebasubicacion.presentation.ui.misc.ProfileDetailRow
import com.example.pruebasubicacion.presentation.viewmodel.AuthViewModel
import com.example.pruebasubicacion.util.Log
import com.example.pruebasubicacion.util.colors.getAdaptiveThemeColors

@Composable
fun ProfileAccountView(
    theme: ThemeColors,
    userProfile: UserProfileData,
    onLoginSuccess: (UserProfileData) -> Unit,
    onLogout: () -> Unit,
    onClose: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    // Si el usuario ya está logueado, usamos los datos que vinieron del ViewModel:
    val perfilActualizado = userProfile.copy(
        nombre = viewModel.uiState.nombre.ifBlank { userProfile.nombre },
        apellido = viewModel.uiState.apellido.ifBlank { userProfile.apellido },
        alergias = viewModel.uiState.alergia.ifBlank { userProfile.alergias },
        tipoCiudad = viewModel.uiState.tipoCiudad.ifBlank { userProfile.tipoCiudad }
    )

    ProfileAccountView(
        theme = theme,
        userProfile = perfilActualizado,
        email = viewModel.uiState.email,
        password = viewModel.uiState.password,
        regEmail = viewModel.uiState.regEmail,
        regPassword = viewModel.uiState.regPassword,
        isLoading = viewModel.uiState.isLoading,
        errorMessage = viewModel.uiState.errorMessage,
        listaAlergias = viewModel.uiState.listaAlergias,
        listaTiposEntorno = viewModel.uiState.listaTiposEntorno,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onRegEmailChanged = viewModel::onRegEmailChanged,
        onRegPasswordChanged = viewModel::onRegPasswordChanged,
        onLogin = { onSuccess ->
            viewModel.login {
                onLoginSuccess(
                    UserProfileData(
                        isLoggedIn = true,
                        nombre = viewModel.uiState.nombre.ifBlank { "Usuario" },
                        apellido = viewModel.uiState.apellido.ifBlank { "" },
                        correo = viewModel.uiState.email.ifBlank { userProfile.correo },
                        alergias = viewModel.uiState.alergia.ifBlank { "Ninguna" },
                        tipoCiudad = viewModel.uiState.tipoCiudad.ifBlank { "Urbana" }
                    )
                )
                onSuccess()
            }
        },
        onRegister = { nombre, apellido, tieneAlergia, fkIdAlergia, fkIdTipoCiudad, onSuccess ->
            viewModel.register(
                nombre,
                apellido,
                tieneAlergia,
                fkIdAlergia,
                fkIdTipoCiudad,
                onSuccess
            )
        },
        onLoginSuccess = onLoginSuccess,
        onLogout = onLogout,
        onClose = onClose
    )
}

@Composable
fun ProfileAccountView(
    theme: ThemeColors,
    userProfile: UserProfileData,
    email: String,
    password: String,
    regEmail: String,
    regPassword: String,
    isLoading: Boolean,
    errorMessage: String?,
    listaAlergias: List<String>,
    listaTiposEntorno: List<String>,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRegEmailChanged: (String) -> Unit,
    onRegPasswordChanged: (String) -> Unit,
    onLogin: (() -> Unit) -> Unit,
    onRegister: (String, String, Boolean, Int?, Int?, () -> Unit) -> Unit,
    onLoginSuccess: (UserProfileData) -> Unit,
    onLogout: () -> Unit,
    onClose: () -> Unit
) {
    var isRegisterTab by remember { mutableStateOf(false) }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = theme.bgCard),
        border = BorderStroke(1.5.dp, theme.accentBrand.copy(0.35f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Header con botón de cierre
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = theme.accentBrand.copy(0.18f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                if (userProfile.isLoggedIn) Icons.Outlined.Person else Icons.Outlined.Lock,
                                contentDescription = null,
                                tint = theme.accentBrand,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (userProfile.isLoggedIn) "Mi Perfil AeroTrack" else "Bienvenido a AeroTrack",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = theme.textPrimary
                        )
                        Text(
                            text = if (userProfile.isLoggedIn) "Sesión sincronizada" else "Monitoreo de aire para alto rendimiento",
                            fontSize = 11.sp,
                            color = theme.textMuted,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = theme.textPrimary)
                }
            }

            if (!userProfile.isLoggedIn) {
                // Segmented Tab Switcher (Iniciar Sesión / Registrarse)
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = theme.bgSubtle,
                    border = BorderStroke(1.dp, theme.borderCard),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (!isRegisterTab) theme.accentBrand else Color.Transparent)
                                .clickable { isRegisterTab = false }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Iniciar Sesión",
                                color = if (!isRegisterTab) Color.White else theme.textMuted,
                                fontWeight = if (!isRegisterTab) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isRegisterTab) theme.accentBrand else Color.Transparent)
                                .clickable { isRegisterTab = true }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Registrarse",
                                color = if (isRegisterTab) Color.White else theme.textMuted,
                                fontWeight = if (isRegisterTab) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            if (userProfile.isLoggedIn) {
                // --- VISTA PERFIL LOGUEADO ---
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = CircleShape,
                        color = theme.accentBrand.copy(0.15f),
                        modifier = Modifier.size(72.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${userProfile.nombre.take(1)}${userProfile.apellido.take(1)}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = theme.accentBrand
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "${userProfile.nombre} ${userProfile.apellido}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = theme.textPrimary
                    )
                    Text(
                        text = userProfile.correo,
                        fontSize = 13.sp,
                        color = theme.textMuted
                    )

                    Spacer(Modifier.height(18.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ProfileDetailRow("Nombre", userProfile.nombre, Icons.Outlined.Badge, theme)
                        ProfileDetailRow("Apellido", userProfile.apellido, Icons.Outlined.Badge, theme)
                        ProfileDetailRow("Correo", userProfile.correo, Icons.Outlined.Email, theme)
                        ProfileDetailRow("Alergias / Polen", userProfile.alergias, Icons.Outlined.Shield, theme)
                        ProfileDetailRow("Tipo de Entorno", userProfile.tipoCiudad, Icons.Outlined.Place, theme)
                    }

                    Spacer(Modifier.height(20.dp))

                    OutlinedButton(
                        onClick = onLogout,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFEF4444)),
                        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Cerrar Sesión", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            } else {
                if (isRegisterTab) {
                    // --- VISTA REGISTRARSE ---
                    val textColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = theme.accentBrand,
                        unfocusedBorderColor = theme.borderCard,
                        focusedLabelColor = theme.accentBrand,
                        focusedContainerColor = theme.bgSubtle,
                        unfocusedContainerColor = theme.bgSubtle,
                        focusedTextColor = theme.textPrimary,
                        unfocusedTextColor = theme.textPrimary
                    )
                    var regNombre by remember { mutableStateOf("") }
                    var regApellido by remember { mutableStateOf("") }

                    val defaultAlergia = listaAlergias.firstOrNull() ?: "Ninguna / Sin alergias"
                    val defaultEntorno = listaTiposEntorno.firstOrNull() ?: "Urbana"

                    var regAlergias by remember(listaAlergias) { mutableStateOf(defaultAlergia) }
                    var regTipoCiudad by remember(listaTiposEntorno) { mutableStateOf(defaultEntorno) }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = regNombre,
                            onValueChange = { regNombre = it },
                            label = { Text("Nombre") },
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null, tint = theme.accentBrand) },
                            colors = textColors,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                            value = regApellido,
                            onValueChange = { regApellido = it },
                            label = { Text("Apellido") },
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Outlined.Badge, contentDescription = null, tint = theme.accentBrand) },
                            colors = textColors,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                            value = regEmail,
                            onValueChange = onRegEmailChanged,
                            label = { Text("Correo Electrónico") },
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null, tint = theme.accentBrand) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = textColors,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                            value = regPassword,
                            onValueChange = onRegPasswordChanged,
                            label = { Text("Contraseña") },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = theme.accentBrand) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = textColors,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        AeroTrackDropdown(
                            label = "Alergia / Polinizante (Aire libre)",
                            selectedValue = regAlergias,
                            options = listaAlergias,
                            onItemSelected = { regAlergias = it },
                            theme = theme
                        )

                        AeroTrackDropdown(
                            label = "Tipo de Entorno Urbano / Rural",
                            selectedValue = regTipoCiudad,
                            options = listaTiposEntorno,
                            onItemSelected = { regTipoCiudad = it },
                            theme = theme
                        )

                        if (errorMessage != null) {
                            Log("RegisterError", errorMessage)
                            Text(
                                text = errorMessage,
                                color = Color(0xFFEF4444),
                                fontSize = 12.sp
                            )
                        }

                        Spacer(Modifier.height(4.dp))

                        Button(
                            onClick = {
                                onRegister(
                                    regNombre.ifBlank { "Atleta" },
                                    regApellido.ifBlank { "Pro" },
                                    regAlergias != "Ninguna / Sin alergias",
                                    listaAlergias.indexOf(regAlergias) + 1,
                                    listaTiposEntorno.indexOf(regTipoCiudad) + 1
                                ) {
                                    onLoginSuccess(
                                        UserProfileData(
                                            isLoggedIn = true,
                                            nombre = regNombre.ifBlank { "Atleta" },
                                            apellido = regApellido.ifBlank { "Pro" },
                                            correo = regEmail,
                                            alergias = regAlergias,
                                            tipoCiudad = regTipoCiudad
                                        )
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.accentBrand),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Text(
                                if (isLoading) "Creando cuenta..." else "Crear Cuenta AeroTrack",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }
                } else {
                    val textColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = theme.accentBrand,
                        unfocusedBorderColor = theme.borderCard,
                        focusedLabelColor = theme.accentBrand,
                        focusedContainerColor = theme.bgSubtle,
                        unfocusedContainerColor = theme.bgSubtle,
                        focusedTextColor = theme.textPrimary,
                        unfocusedTextColor = theme.textPrimary
                    )
                    // --- VISTA INICIAR SESIÓN (NO LOGUEADO) ---
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = onEmailChanged,
                            label = { Text("Correo Electrónico") },
                            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null, tint = theme.accentBrand) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = textColors,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        OutlinedTextField(
                            value = password,
                            onValueChange = onPasswordChanged,
                            label = { Text("Contraseña") },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = theme.accentBrand) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = textColors,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        if (errorMessage != null) {
                            Text(
                                text = errorMessage,
                                color = Color(0xFFEF4444),
                                fontSize = 12.sp
                            )
                        }

                        Spacer(Modifier.height(4.dp))

                        Button(
                            onClick = {
                                onLogin {
                                    // Los datos reales de la BD son manejados y enviados por el wrapper
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.accentBrand),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Text(
                                if (isLoading) "Iniciando sesión..." else "Iniciar Sesión",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileAccountViewPreview() {
    val theme = getAdaptiveThemeColors(isDark = true)
    var userProfile by remember { mutableStateOf(UserProfileData()) }
    var showProfile by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var regEmail by remember { mutableStateOf("") }
    var regPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val sampleAlergias = listOf("Polen de Gramíneas", "Ninguna / Sin alergias")
    val sampleEntornos = listOf("Urbana", "Rural")

    ProfileAccountView(
        theme = theme,
        userProfile = userProfile,
        email = email,
        password = password,
        regEmail = regEmail,
        regPassword = regPassword,
        isLoading = isLoading,
        errorMessage = errorMessage,
        listaAlergias = sampleAlergias,
        listaTiposEntorno = sampleEntornos,
        onEmailChanged = { email = it },
        onPasswordChanged = { password = it },
        onRegEmailChanged = { regEmail = it },
        onRegPasswordChanged = { regPassword = it },
        onLogin = { onSuccess -> onSuccess() },
        onRegister = { _, _, _, _, _, onSuccess -> onSuccess() },
        onLoginSuccess = { updatedProfile ->
            userProfile = updatedProfile.copy(isLoggedIn = true)
        },
        onLogout = {
            userProfile = userProfile.copy(isLoggedIn = false)
        },
        onClose = { showProfile = false }
    )
}
