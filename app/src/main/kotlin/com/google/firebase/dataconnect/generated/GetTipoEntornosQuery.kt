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


public interface GetTipoEntornosQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
            ExampleConnector,
            GetTipoEntornosQuery.Data,
            Unit
            > {


    @kotlinx.serialization.Serializable
    public data class Data(

        val tipoEntornos: List<TipoEntornosItem>,

        ) {


        @kotlinx.serialization.Serializable
        public data class TipoEntornosItem(

            val idtipoentorno: Int,

            val descripcion: String,

            ) {


        }


    }


    public companion object {
        public val operationName: String = "GetTipoEntornos"

        public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
            kotlinx.serialization.serializer()

        public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
            kotlinx.serialization.serializer()
    }
}

public fun GetTipoEntornosQuery.ref(

): com.google.firebase.dataconnect.QueryRef<
        GetTipoEntornosQuery.Data,
        Unit
        > =
    ref(

        Unit

    )

public suspend fun GetTipoEntornosQuery.execute(


    fetchPolicy: com.google.firebase.dataconnect.QueryRef.FetchPolicy = com.google.firebase.dataconnect.QueryRef.FetchPolicy.PREFER_CACHE,


    ): com.google.firebase.dataconnect.QueryResult<
        GetTipoEntornosQuery.Data,
        Unit
        > =
    ref(

    ).execute(fetchPolicy = fetchPolicy)


public fun GetTipoEntornosQuery.flow(

): kotlinx.coroutines.flow.Flow<GetTipoEntornosQuery.Data> =
    ref(

    ).subscribe()
        .flow
        ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
        ._flow_filterNotNull()
        ._flow_map { it.data }

