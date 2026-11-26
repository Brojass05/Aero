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

import com.google.firebase.dataconnect.getInstance as _fdcGetInstance

public interface ExampleConnector :
    com.google.firebase.dataconnect.generated.GeneratedConnector<ExampleConnector> {
    override val dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect


    public val deleteUsuario: DeleteUsuarioMutation

    public val getAlergias: GetAlergiasQuery

    public val getFullUsuarioById: GetFullUsuarioByIdQuery

    public val getTipoEntornos: GetTipoEntornosQuery

    public val getUsuarioById: GetUsuarioByIdQuery

    public val getUsuarios: GetUsuariosQuery

    public val upsertUsuario: UpsertUsuarioMutation


    public companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        public val config: com.google.firebase.dataconnect.ConnectorConfig =
            com.google.firebase.dataconnect.ConnectorConfig(
                connector = "example",
                location = "southamerica-west1",
                serviceId = "pruebasbbdd-db511-service",
            )

        public fun getInstance(
            dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect
        ): ExampleConnector = synchronized(instances) {
            instances.getOrPut(dataConnect) {
                ExampleConnectorImpl(dataConnect)
            }
        }

        private val instances =
            java.util.WeakHashMap<com.google.firebase.dataconnect.FirebaseDataConnect, ExampleConnectorImpl>()


        public val defaultCacheSettings: com.google.firebase.dataconnect.CacheSettings =
            com.google.firebase.dataconnect.CacheSettings(


            )

        public val defaultDataConnectSettings: com.google.firebase.dataconnect.DataConnectSettings =
            com.google.firebase.dataconnect.DataConnectSettings(
                cacheSettings = defaultCacheSettings,
            )

    }
}

public val ExampleConnector.Companion.instance: ExampleConnector
    get() = getInstance(
        com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(
            config, defaultDataConnectSettings
        )
    )

public fun ExampleConnector.Companion.getInstance(
    settings: com.google.firebase.dataconnect.DataConnectSettings = defaultDataConnectSettings
): ExampleConnector =
    getInstance(
        com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(
            config,
            settings
        )
    )

public fun ExampleConnector.Companion.getInstance(
    app: com.google.firebase.FirebaseApp,
    settings: com.google.firebase.dataconnect.DataConnectSettings = defaultDataConnectSettings
): ExampleConnector =
    getInstance(
        com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(
            app,
            config,
            settings
        )
    )

private class ExampleConnectorImpl(
    override val dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect
) : ExampleConnector {

    override val deleteUsuario by lazy(LazyThreadSafetyMode.PUBLICATION) {
        DeleteUsuarioMutationImpl(this)
    }

    override val getAlergias by lazy(LazyThreadSafetyMode.PUBLICATION) {
        GetAlergiasQueryImpl(this)
    }

    override val getFullUsuarioById by lazy(LazyThreadSafetyMode.PUBLICATION) {
        GetFullUsuarioByIdQueryImpl(this)
    }

    override val getTipoEntornos by lazy(LazyThreadSafetyMode.PUBLICATION) {
        GetTipoEntornosQueryImpl(this)
    }

    override val getUsuarioById by lazy(LazyThreadSafetyMode.PUBLICATION) {
        GetUsuarioByIdQueryImpl(this)
    }

    override val getUsuarios by lazy(LazyThreadSafetyMode.PUBLICATION) {
        GetUsuariosQueryImpl(this)
    }

    override val upsertUsuario by lazy(LazyThreadSafetyMode.PUBLICATION) {
        UpsertUsuarioMutationImpl(this)
    }


    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun operations(): List<com.google.firebase.dataconnect.generated.GeneratedOperation<ExampleConnector, *, *>> =
        queries() + mutations()

    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun mutations(): List<com.google.firebase.dataconnect.generated.GeneratedMutation<ExampleConnector, *, *>> =
        listOf(
            deleteUsuario,
            upsertUsuario,

            )

    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun queries(): List<com.google.firebase.dataconnect.generated.GeneratedQuery<ExampleConnector, *, *>> =
        listOf(
            getAlergias,
            getFullUsuarioById,
            getTipoEntornos,
            getUsuarioById,
            getUsuarios,

            )

    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun copy(dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect) =
        ExampleConnectorImpl(dataConnect)

    override fun equals(other: Any?): Boolean =
        other is ExampleConnectorImpl &&
                other.dataConnect == dataConnect

    override fun hashCode(): Int =
        java.util.Objects.hash(
            "ExampleConnectorImpl",
            dataConnect,
        )

    override fun toString(): String =
        "ExampleConnectorImpl(dataConnect=$dataConnect)"
}


private open class ExampleConnectorGeneratedQueryImpl<Data, Variables>(
    override val connector: ExampleConnector,
    override val operationName: String,
    override val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
    override val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
) : com.google.firebase.dataconnect.generated.GeneratedQuery<ExampleConnector, Data, Variables> {

    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun copy(
        connector: ExampleConnector,
        operationName: String,
        dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
        variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
    ) =
        ExampleConnectorGeneratedQueryImpl(
            connector, operationName, dataDeserializer, variablesSerializer
        )

    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun <NewVariables> withVariablesSerializer(
        variablesSerializer: kotlinx.serialization.SerializationStrategy<NewVariables>
    ) =
        ExampleConnectorGeneratedQueryImpl(
            connector, operationName, dataDeserializer, variablesSerializer
        )

    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun <NewData> withDataDeserializer(
        dataDeserializer: kotlinx.serialization.DeserializationStrategy<NewData>
    ) =
        ExampleConnectorGeneratedQueryImpl(
            connector, operationName, dataDeserializer, variablesSerializer
        )

    override fun equals(other: Any?): Boolean =
        other is ExampleConnectorGeneratedQueryImpl<*, *> &&
                other.connector == connector &&
                other.operationName == operationName &&
                other.dataDeserializer == dataDeserializer &&
                other.variablesSerializer == variablesSerializer

    override fun hashCode(): Int =
        java.util.Objects.hash(
            "ExampleConnectorGeneratedQueryImpl",
            connector, operationName, dataDeserializer, variablesSerializer
        )

    override fun toString(): String =
        "ExampleConnectorGeneratedQueryImpl(" +
                "operationName=$operationName, " +
                "dataDeserializer=$dataDeserializer, " +
                "variablesSerializer=$variablesSerializer, " +
                "connector=$connector)"
}

private open class ExampleConnectorGeneratedMutationImpl<Data, Variables>(
    override val connector: ExampleConnector,
    override val operationName: String,
    override val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
    override val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
) : com.google.firebase.dataconnect.generated.GeneratedMutation<ExampleConnector, Data, Variables> {

    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun copy(
        connector: ExampleConnector,
        operationName: String,
        dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
        variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
    ) =
        ExampleConnectorGeneratedMutationImpl(
            connector, operationName, dataDeserializer, variablesSerializer
        )

    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun <NewVariables> withVariablesSerializer(
        variablesSerializer: kotlinx.serialization.SerializationStrategy<NewVariables>
    ) =
        ExampleConnectorGeneratedMutationImpl(
            connector, operationName, dataDeserializer, variablesSerializer
        )

    @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
    override fun <NewData> withDataDeserializer(
        dataDeserializer: kotlinx.serialization.DeserializationStrategy<NewData>
    ) =
        ExampleConnectorGeneratedMutationImpl(
            connector, operationName, dataDeserializer, variablesSerializer
        )

    override fun equals(other: Any?): Boolean =
        other is ExampleConnectorGeneratedMutationImpl<*, *> &&
                other.connector == connector &&
                other.operationName == operationName &&
                other.dataDeserializer == dataDeserializer &&
                other.variablesSerializer == variablesSerializer

    override fun hashCode(): Int =
        java.util.Objects.hash(
            "ExampleConnectorGeneratedMutationImpl",
            connector, operationName, dataDeserializer, variablesSerializer
        )

    override fun toString(): String =
        "ExampleConnectorGeneratedMutationImpl(" +
                "operationName=$operationName, " +
                "dataDeserializer=$dataDeserializer, " +
                "variablesSerializer=$variablesSerializer, " +
                "connector=$connector)"
}


private class DeleteUsuarioMutationImpl(
    connector: ExampleConnector
) :
    DeleteUsuarioMutation,
    ExampleConnectorGeneratedMutationImpl<
            DeleteUsuarioMutation.Data,
            DeleteUsuarioMutation.Variables
            >(
        connector,
        DeleteUsuarioMutation.Companion.operationName,
        DeleteUsuarioMutation.Companion.dataDeserializer,
        DeleteUsuarioMutation.Companion.variablesSerializer,
    )


private class GetAlergiasQueryImpl(
    connector: ExampleConnector
) :
    GetAlergiasQuery,
    ExampleConnectorGeneratedQueryImpl<
            GetAlergiasQuery.Data,
            Unit
            >(
        connector,
        GetAlergiasQuery.Companion.operationName,
        GetAlergiasQuery.Companion.dataDeserializer,
        GetAlergiasQuery.Companion.variablesSerializer,
    )


private class GetFullUsuarioByIdQueryImpl(
    connector: ExampleConnector
) :
    GetFullUsuarioByIdQuery,
    ExampleConnectorGeneratedQueryImpl<
            GetFullUsuarioByIdQuery.Data,
            GetFullUsuarioByIdQuery.Variables
            >(
        connector,
        GetFullUsuarioByIdQuery.Companion.operationName,
        GetFullUsuarioByIdQuery.Companion.dataDeserializer,
        GetFullUsuarioByIdQuery.Companion.variablesSerializer,
    )


private class GetTipoEntornosQueryImpl(
    connector: ExampleConnector
) :
    GetTipoEntornosQuery,
    ExampleConnectorGeneratedQueryImpl<
            GetTipoEntornosQuery.Data,
            Unit
            >(
        connector,
        GetTipoEntornosQuery.Companion.operationName,
        GetTipoEntornosQuery.Companion.dataDeserializer,
        GetTipoEntornosQuery.Companion.variablesSerializer,
    )


private class GetUsuarioByIdQueryImpl(
    connector: ExampleConnector
) :
    GetUsuarioByIdQuery,
    ExampleConnectorGeneratedQueryImpl<
            GetUsuarioByIdQuery.Data,
            GetUsuarioByIdQuery.Variables
            >(
        connector,
        GetUsuarioByIdQuery.Companion.operationName,
        GetUsuarioByIdQuery.Companion.dataDeserializer,
        GetUsuarioByIdQuery.Companion.variablesSerializer,
    )


private class GetUsuariosQueryImpl(
    connector: ExampleConnector
) :
    GetUsuariosQuery,
    ExampleConnectorGeneratedQueryImpl<
            GetUsuariosQuery.Data,
            Unit
            >(
        connector,
        GetUsuariosQuery.Companion.operationName,
        GetUsuariosQuery.Companion.dataDeserializer,
        GetUsuariosQuery.Companion.variablesSerializer,
    )


private class UpsertUsuarioMutationImpl(
    connector: ExampleConnector
) :
    UpsertUsuarioMutation,
    ExampleConnectorGeneratedMutationImpl<
            UpsertUsuarioMutation.Data,
            UpsertUsuarioMutation.Variables
            >(
        connector,
        UpsertUsuarioMutation.Companion.operationName,
        UpsertUsuarioMutation.Companion.dataDeserializer,
        UpsertUsuarioMutation.Companion.variablesSerializer,
    )


