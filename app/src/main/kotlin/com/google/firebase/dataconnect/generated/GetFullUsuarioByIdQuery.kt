
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


public interface GetFullUsuarioByIdQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      ExampleConnector,
      GetFullUsuarioByIdQuery.Data,
      GetFullUsuarioByIdQuery.Variables
    >
{
  
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
  
    val fkIdAlergia: Int?,
  
    val fkIdTipoCiudad: Int?,
  
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "GetFullUsuarioById"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun GetFullUsuarioByIdQuery.ref(
  
    userid: String,

  
  
): com.google.firebase.dataconnect.QueryRef<
    GetFullUsuarioByIdQuery.Data,
    GetFullUsuarioByIdQuery.Variables
  > =
  ref(
    
      GetFullUsuarioByIdQuery.Variables(
        userid=userid,
  
      )
    
  )

public suspend fun GetFullUsuarioByIdQuery.execute(

  
    
      userid: String,
  fetchPolicy: com.google.firebase.dataconnect.QueryRef.FetchPolicy = com.google.firebase.dataconnect.QueryRef.FetchPolicy.PREFER_CACHE,
  

  ): com.google.firebase.dataconnect.QueryResult<
    GetFullUsuarioByIdQuery.Data,
    GetFullUsuarioByIdQuery.Variables
  > =
  ref(
    
      userid=userid,
  
    
  ).execute(fetchPolicy = fetchPolicy)


  public fun GetFullUsuarioByIdQuery.flow(
    
      userid: String,

  
    
    ): kotlinx.coroutines.flow.Flow<GetFullUsuarioByIdQuery.Data> =
    ref(
        
          userid=userid,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

