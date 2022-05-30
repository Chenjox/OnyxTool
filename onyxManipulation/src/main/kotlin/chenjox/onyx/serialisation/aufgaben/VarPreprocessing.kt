package chenjox.onyx.serialisation.aufgaben

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

data class VarPreprocessing(
    val id: String,
    @JsonProperty("set") val setting: PreprocessorSetting
)

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonSubTypes(
    JsonSubTypes.Type(name = "name", value = RenameSetting::class)
)
sealed class PreprocessorSetting

data class RenameSetting (
    @JsonProperty("to") val newId: String
): PreprocessorSetting()