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


public interface DeleteUsuarioMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
            ExampleConnector,
            DeleteUsuarioMutation.Data,
            DeleteUsuarioMutation.Variables
            > {

    @kotlinx.serialization.Serializable
    public data class Variables(

        val userid: String,

        ) {


    }


    @kotlinx.serialization.Serializable
    public data class Data(

        val usuario_delete: UsuarioKey?,

        ) {


    }


    public companion object {
        public val operationName: String = "DeleteUsuario"

        public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
            kotlinx.serialization.serializer()

        public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
            kotlinx.serialization.serializer()
    }
}

public fun DeleteUsuarioMutation.ref(

    userid: String,


    ): com.google.firebase.dataconnect.MutationRef<
        DeleteUsuarioMutation.Data,
        DeleteUsuarioMutation.Variables
        > =
    ref(

        DeleteUsuarioMutation.Variables(
            userid = userid,

            )

    )

public suspend fun DeleteUsuarioMutation.execute(


    userid: String,


    ): com.google.firebase.dataconnect.MutationResult<
        DeleteUsuarioMutation.Data,
        DeleteUsuarioMutation.Variables
        > =
    ref(

        userid = userid,


        ).execute()


