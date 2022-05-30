package chenjox.onyx.serialisation.serializers

import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.exc.InputCoercionException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer


object BaseTypeSerializer : StdSerializer<BaseType>(BaseType::class.java) {
    override fun serialize(value: BaseType, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value.qtiName)
    }
}

object BaseTypeDeserializer : StdDeserializer<BaseType>(BaseType::class.java){
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): BaseType {
        return p.valueAsString?.run {
            try {
                BaseType.fromString(this)
            }catch (e: IllegalArgumentException){
                throw InputCoercionException(
                    p,
                    "String value (${p.text}) not one of the expected types!",
                    JsonToken.VALUE_STRING,
                    BaseType::class.java
                )
            }
        } ?: throw InputCoercionException(
            p,
            "String value (${p.text}) not one of the expected types!",
            JsonToken.VALUE_STRING,
            BaseType::class.java
        )

    }
}