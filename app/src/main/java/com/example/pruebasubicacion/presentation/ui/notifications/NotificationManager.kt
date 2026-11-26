package com.example.pruebasubicacion.presentation.ui.notifications

import android.Manifest
import android.R
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pruebasubicacion.MainActivity
import com.example.pruebasubicacion.data.repository.UserPreferences
import com.example.pruebasubicacion.util.ChequeadorBackground


suspend fun showSimpleNotification(
    context: Context, id: Int = 111,
    titulo: String = "Notificacion", mensaje: String,
    idNoti: String = "n1",
    iconRes: Int = R.drawable.ic_dialog_info,
    userPreferences: UserPreferences
) {
    val builder = NotificationCompat.Builder(context, "CHANNEL_ID_EJEMPLO")
        .setSmallIcon(iconRes) // Icono distinto según nivel de peligro
        .setContentTitle(titulo)
        .setContentText(mensaje)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
    try {
        with(NotificationManagerCompat.from(context)) {
            notify(id, builder.build())
        }
    } catch (e: SecurityException) {
        Log.e("Notification", "Security error: missing permission", e)
    }
    userPreferences.saveLastSetNoti(idNoti)
}


suspend fun showSimpleNotificationOpenActivity(
    context: Context, notId: Int = 102,
    newPm: Float, lastPm: Float?,
    userPreferences: UserPreferences
) {
    Log.i("Notification", "New PM: $newPm, Last PM: $lastPm")
    val lastPM = lastPm ?: return

    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(context, "CHANNEL_ID_EJEMPLO")
    if (ChequeadorBackground.segundoPlano) {
        Log.i("DebugTag", "Si estas en segundo plano")
        if (newPm > lastPM) {
            notificacionSubida(builder, pendingIntent, newPm, lastPm)
            userPreferences.saveLastSetNoti("n1")

        } else if (newPm < lastPM) {
            notificacionBajada(builder, pendingIntent, newPm, lastPm)
            userPreferences.saveLastSetNoti("n2")
        } else {
            userPreferences.saveLastSetNoti("n3")
            return
        }

    } else {
        Log.i("DebugTag", "No estas en segundo plano")
        return
    }

    // 4. LAUNCH (With permission check to avoid errors)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        NotificationManagerCompat.from(context).notify(notId, builder.build())
    }
}

fun notificacionSubida(
    builder: NotificationCompat.Builder,
    pendingIntent: PendingIntent,
    newPm: Float,
    lastPm: Float?
) {
    builder.setSmallIcon(R.drawable.ic_dialog_alert)
        .setContentTitle("Alerta!!!")
        .setContentText("El nivel de contaminacion ha subido \nTen cuidado si vas a salir")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
}

fun notificacionBajada(
    builder: NotificationCompat.Builder,
    pendingIntent: PendingIntent,
    newPm: Float,
    lastPm: Float?
) {
    builder.setSmallIcon(R.drawable.ic_dialog_info)
        .setContentTitle("Informacion")
        .setContentText("El nivel de contaminacion ha bajado")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
}





