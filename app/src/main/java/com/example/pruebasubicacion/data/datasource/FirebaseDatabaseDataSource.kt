package com.example.pruebasubicacion.data.datasource

import com.example.pruebasubicacion.data.model.AlertaDto
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseDatabaseDataSource {
    private val database = Firebase.database.reference

    fun getAlertsFlow(): Flow<List<AlertaDto>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val alerts = snapshot.children.mapNotNull { it.getValue(AlertaDto::class.java) }
                trySend(alerts)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        database.child("alerts").addValueEventListener(listener)
        awaitClose { database.child("alerts").removeEventListener(listener) }
    }
}
