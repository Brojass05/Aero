# AeroTrack


AeroTrack es una aplicación nativa de Android diseñada para proporcionar datos ambientales y de calidad del aire en tiempo real basados en la ubicación. Adaptada para atletas y personas sensibles a la contaminación del aire, ofrece recomendaciones personalizadas, pronósticos y alertas en segundo plano para ayudar a los usuarios a planificar sus actividades al aire libre de manera segura.

## Características

- **Datos ambientales en tiempo real:** Obtiene datos completos que incluyen PM2.5, PM10, ozono, diversos tipos de polen, índice UV, temperatura y humedad.
- **Perfiles de usuario personalizados:** Registro e inicio de sesión seguros a través de Firebase. Los usuarios pueden guardar datos personales, como alergias y tipo de entorno habitual, para recibir consejos adaptados.
- **Recomendaciones adaptativas:** La interfaz de usuario proporciona orientación específica según si el usuario es atleta o tiene sensibilidades, ayudándole a tomar decisiones informadas sobre el ejercicio al aire libre.
- **Informe ambiental del día siguiente:** Genera un pronóstico detallado para el día siguiente, calculando el horario óptimo para actividades al aire libre según los niveles proyectados de contaminantes y polen.
- **Interfaz de usuario dinámica:** Una interfaz moderna construida con Jetpack Compose que admite modos claro y oscuro, así como diseños adaptables para orientaciones vertical y horizontal.
- **Monitoreo y alertas en segundo plano:** Un servicio periódico en segundo plano verifica cambios significativos en la calidad del aire (PM2.5) y envía notificaciones push para alertar al usuario.
- **Visualizaciones ricas en datos:** Incluye un medidor interactivo de ICA (Índice de Calidad del Aire), pronósticos por hora y una perspectiva de 7 días para ayudar a los usuarios a comprender las tendencias ambientales.

## Stack Tecnológico y Arquitectura

AeroTrack está construida con un stack tecnológico moderno de Android y nativo de la nube.

### Frontend (Android)
- **Lenguaje:** Kotlin
- **Interfaz de usuario (UI):** Jetpack Compose
- **Asincronía:** Corrutinas de Kotlin (Kotlin Coroutines)
- **Arquitectura:** MVVM (Modelo-Vista-VistaModelo)
- **Procesamiento en segundo plano:** WorkManager
- **Almacenamiento local:** DataStore Preferences
- **Redes:** Retrofit

### Backend e Infraestructura
- **Autenticación:** Firebase Authentication
- **Base de datos:** PostgreSQL (en Google Cloud SQL)
- **Capa de API:** Firebase Data Connect (proporciona una API GraphQL con seguridad de tipos para la base de datos)

### APIs Externas
- **Open-Meteo:** Para datos meteorológicos y de calidad del aire.
- **OpenStreetMap Nominatim:** Para geocodificación inversa (conversión de coordenadas a nombres de ubicaciones).

### Resumen de la Arquitectura

La aplicación sigue una arquitectura MVVM. La interfaz de usuario, construida íntegramente con Jetpack Compose, observa el estado desde los `ViewModels`.

- El `UbicacionViewModel` gestiona la ubicación y la obtención de datos ambientales desde las APIs de Open-Meteo a través de un `ClimaRepository`.
- El `AuthViewModel` maneja la autenticación de usuarios mediante Firebase y gestiona los datos de perfil almacenados en una base de datos PostgreSQL.
- La comunicación con la base de datos PostgreSQL es fluida y con seguridad de tipos utilizando un SDK de cliente generado por **Firebase Data Connect**, el cual expone una interfaz GraphQL definida en el directorio `dataconnect/`.
- Para el monitoreo proactivo, una tarea de `WorkManager` (`PmCheckerWorker`) se ejecuta periódicamente en segundo plano para obtener los últimos niveles de PM2.5 y activar notificaciones si las condiciones cambian.

## Primeros Pasos

Para obtener una copia local y ponerla en funcionamiento, sigue estos pasos.

### Prerrequisitos

- Android Studio (se recomienda la última versión)
- JDK 11 o superior
- Un proyecto de Firebase
- Un proyecto de Google Cloud con una instancia de PostgreSQL en Cloud SQL

### Configuración

1. **Clonar el repositorio:**
   ```sh
   git clone [https://github.com/brojass05/aerotrack.git](https://github.com/brojass05/aerotrack.git)
   ```
2. **Configuración de Firebase:**
  Crea un nuevo proyecto en la Consola de Firebase.
  Agrega una aplicación Android a tu proyecto con el nombre de paquete com.example.pruebasubicacion.
  Descarga el archivo google-services.json generado y colócalo en el directorio app/.
  En la consola de Firebase, habilita Firebase Authentication con el proveedor "Correo electrónico/Contraseña" (Email/Password).
3. **Configuración del Backend (Firebase Data Connect y PostgreSQL):**
  Configura una instancia de PostgreSQL en Google Cloud SQL dentro de tu proyecto de Google Cloud.
  Sigue la documentación de Firebase Data Connect para vincular tu proyecto de Firebase con tu instancia de Cloud SQL.
  El esquema de la base de datos requerido por la aplicación está definido en dataconnect/schema/schema.gql. Deberás crear las tablas correspondientes (usuarios, alergias, tipoentorno) en tu base de datos PostgreSQL.
  Despliega el servicio Data Connect utilizando Firebase CLI desde la raíz del proyecto.
4. **Compilar y Ejecutar:**
  Abre el proyecto clonado en Android Studio.
  Permite que Gradle sincronice las dependencias.
  Ejecuta la aplicación en un emulador de Android o en un dispositivo físico.
**Componentes Clave**

    **MainActivity.kt**: El punto de entrada principal de la aplicación, responsable de gestionar los permisos y configurar la interfaz de usuario de Jetpack Compose.

    **EcoAlertScreen.kt**: La función composable principal que construye toda la interfaz de usuario, incluyendo las vistas del panel principal (dashboard), perfil y notificaciones.

    **UbicacionViewModel.kt**: Obtiene y gestiona el estado de los datos de calidad del aire, clima y ubicación.

    **AuthViewModel.kt**: Gestiona el estado de autenticación del usuario (inicio de sesión, registro, cierre de sesión) e interactúa con el repositorio para manejar los datos del perfil de usuario.

    **ClimaRepository.kt**: Un componente de la capa de datos que orquesta las llamadas a las APIs de Open-Meteo (calidad del aire/clima) y Nominatim (geocodificación).

    **AuthRepository.kt**: Implementa la lógica para la autenticación de usuarios y las operaciones CRUD en los perfiles de usuario a través del SDK de Firebase Data Connect.

    **PmCheckerWorker.kt**: La tarea en segundo plano de WorkManager que verifica periódicamente los cambios en la calidad del aire y activa las notificaciones para el usuario.

    **dataconnect/**: Este directorio contiene la definición completa del backend para Firebase Data Connect, incluyendo el esquema GraphQL, consultas (queries) y mutaciones (mutations) que interactúan con la base de datos PostgreSQL.
