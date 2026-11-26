@file:Suppress(
    "KotlinRedundantDiagnosticSuppress",
    "PropertyName",
    "MayBeConstant",
    "RedundantVisibilityModifier",
    "RedundantCompanionReference",
    "RemoveEmptyClassBody",
    "SpellCheckingInspection",
    "unused",
)

package com.google.firebase.dataconnect.generated


import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map


public interface GetUsuarioByIdQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
            ExampleConnector,
            GetUsuarioByIdQuery.Data,
            GetUsuarioByIdQuery.Variables
            > {

    @kotlinx.serialization.Serializable
    public data class Variables(

        val userid: String,

        ) {


    }


    @kotlinx.serialization.Serializable
    public data class Data(

        val usuario: Usuario?,

        ) {


        @kotlinx.serialization.Serializable
        public data class Usuario(

            val userid: String,

            val nombre: String,

            val apellido: String,

            val tienealergia: Boolean,

            val correo: String,

            val alergia: Alergia?,

            val tipoEntorno: TipoEntorno?,

            ) {


            @kotlinx.serialization.Serializable
            public data class Alergia(

                val nombrealergia: String,

                ) {


            }

            @kotlinx.serialization.Serializable
            public data class TipoEntorno(

                val descripcion: String,

                ) {


            }


        }


    }


    public companion object {
        public val operationName: String = "GetUsuarioById"

        public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
            kotlinx.serialization.serializer()

        public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
            kotlinx.serialization.serializer()
    }
}

public fun GetUsuarioByIdQuery.ref(

    userid: String,


    ): com.google.firebase.dataconnect.QueryRef<
        GetUsuarioByIdQuery.Data,
        GetUsuarioByIdQuery.Variables
        > =
    ref(

        GetUsuarioByIdQuery.Variables(
            userid = userid,

            )

    )

public suspend fun GetUsuarioByIdQuery.execute(


    userid: String,
    fetchPolicy: com.google.firebase.dataconnect.QueryRef.FetchPolicy = com.google.firebase.dataconnect.QueryRef.FetchPolicy.PREFER_CACHE,


    ): com.google.firebase.dataconnect.QueryResult<
        GetUsuarioByIdQuery.Data,
        GetUsuarioByIdQuery.Variables
        > =
    ref(

        userid = userid,


        ).execute(fetchPolicy = fetchPolicy)


public fun GetUsuarioByIdQuery.flow(

    userid: String,


    ): kotlinx.coroutines.flow.Flow<GetUsuarioByIdQuery.Data> =
    ref(

        userid = userid,


        ).subscribe()
        .flow
        ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
        ._flow_filterNotNull()
        ._flow_map { it.data }

