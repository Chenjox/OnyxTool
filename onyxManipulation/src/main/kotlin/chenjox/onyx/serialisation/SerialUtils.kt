package chenjox.onyx.serialisation

import chenjox.onyx.serialisation.serializers.BaseTypeDeserializer
import chenjox.onyx.serialisation.serializers.BaseTypeSerializer
import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

val mapper = TomlMapper.builder()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .addModule(
        SimpleModule("CustomBaseTypeDeSerializer").also {
            it.addSerializer(BaseType::class.java, BaseTypeSerializer)
            it.addDeserializer(BaseType::class.java, BaseTypeDeserializer)
        }
    )
    .addModule( KotlinModule.Builder().build() )
    .build()!!