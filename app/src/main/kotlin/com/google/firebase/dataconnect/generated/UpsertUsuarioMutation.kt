
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



public interface UpsertUsuarioMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      ExampleConnector,
      UpsertUsuarioMutation.Data,
      UpsertUsuarioMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val userid: String,
  
    val nombre: String,
  
    val apellido: String,
  
    val tienealergia: Boolean,
  
    val correo: String,
  
    val fkIdAlergia: com.google.firebase.dataconnect.OptionalVariable<Int?>,
  
    val fkIdTipoCiudad: com.google.firebase.dataconnect.OptionalVariable<Int?>,
  
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      
      @BuilderDsl
      public interface Builder {
        public var userid: String
        public var nombre: String
        public var apellido: String
        public var tienealergia: Boolean
        public var correo: String
        public var fkIdAlergia: Int?
        public var fkIdTipoCiudad: Int?
        
      }

      public companion object {
        
        @Suppress("NAME_SHADOWING")
        public fun build(
          userid: String,nombre: String,apellido: String,tienealergia: Boolean,correo: String,
          block_: Builder.() -> Unit
        ): Variables {
          var userid= userid
            var nombre= nombre
            var apellido= apellido
            var tienealergia= tienealergia
            var correo= correo
            var fkIdAlergia: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var fkIdTipoCiudad: com.google.firebase.dataconnect.OptionalVariable<Int?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var userid: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { userid = value_ }
              
            override var nombre: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { nombre = value_ }
              
            override var apellido: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { apellido = value_ }
              
            override var tienealergia: Boolean
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { tienealergia = value_ }
              
            override var correo: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { correo = value_ }
              
            override var fkIdAlergia: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { fkIdAlergia = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var fkIdTipoCiudad: Int?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { fkIdTipoCiudad = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              userid=userid,nombre=nombre,apellido=apellido,tienealergia=tienealergia,correo=correo,fkIdAlergia=fkIdAlergia,fkIdTipoCiudad=fkIdTipoCiudad,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuario_upsert: UsuarioKey,
  
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UpsertUsuario"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UpsertUsuarioMutation.ref(
  
    userid: String,nombre: String,apellido: String,tienealergia: Boolean,correo: String,

  
    block_: UpsertUsuarioMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    UpsertUsuarioMutation.Data,
    UpsertUsuarioMutation.Variables
  > =
  ref(
    
      UpsertUsuarioMutation.Variables.build(
        userid=userid,nombre=nombre,apellido=apellido,tienealergia=tienealergia,correo=correo,
  
    block_
      )
    
  )

public suspend fun UpsertUsuarioMutation.execute(

  
    
      userid: String,nombre: String,apellido: String,tienealergia: Boolean,correo: String,

  
    block_: UpsertUsuarioMutation.Variables.Builder.() -> Unit = {}

  ): com.google.firebase.dataconnect.MutationResult<
    UpsertUsuarioMutation.Data,
    UpsertUsuarioMutation.Variables
  > =
  ref(
    
      userid=userid,nombre=nombre,apellido=apellido,tienealergia=tienealergia,correo=correo,
  
    block_
    
  ).execute()


