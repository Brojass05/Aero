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


public interface GetAlergiasQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
            ExampleConnector,
            GetAlergiasQuery.Data,
            Unit
            > {


    @kotlinx.serialization.Serializable
    public data class Data(

        val alergias: List<AlergiasItem>,

        ) {


        @kotlinx.serialization.Serializable
        public data class AlergiasItem(

            val idalergia: Int,

            val nombrealergia: String,

            ) {


        }


    }


    public companion object {
        public val operationName: String = "GetAlergias"

        public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
            kotlinx.serialization.serializer()

        public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
            kotlinx.serialization.serializer()
    }
}

public fun GetAlergiasQuery.ref(

): com.google.firebase.dataconnect.QueryRef<
        GetAlergiasQuery.Data,
        Unit
        > =
    ref(

        Unit

    )

public suspend fun GetAlergiasQuery.execute(


    fetchPolicy: com.google.firebase.dataconnect.QueryRef.FetchPolicy = com.google.firebase.dataconnect.QueryRef.FetchPolicy.PREFER_CACHE,


    ): com.google.firebase.dataconnect.QueryResult<
        GetAlergiasQuery.Data,
        Unit
        > =
    ref(

    ).execute(fetchPolicy = fetchPolicy)


public fun GetAlergiasQuery.flow(

): kotlinx.coroutines.flow.Flow<GetAlergiasQuery.Data> =
    ref(

    ).subscribe()
        .flow
        ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
        ._flow_filterNotNull()
        ._flow_map { it.data }

