package chenjox.onyx.serialisation.aufgaben

import chenjox.onyx.serialisation.Metadata
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

data class OnyxConfig(
    val globalConfig: AssessmentGlobalConfig,
    val metadata: Metadata = Metadata(),
    val assessment: List<Assessment>
)

/**
 * @param formulaFile Wo die Formeln sind
 * @param inputDir Wo das input Verzeichnis ist
 * @param outputDir Wo das output Verzeichnis ist
 * @param idOrdering Ob alle Identifier numeriert werden sollen
 * @param templateFile Wo ein optionales template file ist
 */
data class AssessmentGlobalConfig(
    val formulaFile: String,
    val inputDir: String = "Input",
    val outputDir: String = "Output",
    val idOrdering: Boolean = true,
    val templateFile: String? = null
)

/**
 * @param title The title of the Item
 * @param copyOf The title of the to be copied Item
 * @param image All Image replace jobs
 * @param variables all variable configurations
 */
data class Assessment(
    val title: String,
    val copyOf: String,
    @JsonProperty("pre") val preprocessing: List<VarPreprocessing> = emptyList(),
    @JsonProperty("image") val image: List<ImageReplace> = emptyList(),
    @JsonProperty("var") val variables: List<VariableConfiguration> = emptyList()
)

/**
 * @param oldPos Der Index des Bilds. Entspricht der Reihenfolge des Auftretens bei einer Tiefensuche des html Documents
 * @param imgRef Die Referenz des einzufügenden Bilds
 */
data class ImageReplace(
    @JsonProperty("alt") val altText: String = "",
    val imgRef: String
)

/**
 * @param id Die Id der Variable innerhalb der Aufgabe.
 * @param set Die Setzmethode der Variable
 */
data class VariableConfiguration(
    val id: String,
    val set: VariableSetting
)

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonSubTypes(
    JsonSubTypes.Type(name = "formula", value = FormulaSetting::class),
    JsonSubTypes.Type(name = "random", value = RandomSetting::class),
    JsonSubTypes.Type(name = "text", value = StringSetting::class)
)
sealed class VariableSetting

/**
 * @param upper Obere Grenze für die Zahl
 * @param lower Untere Grenze für die Zahl
 * @param places Anzahl nachkommastellen
 */
data class RandomSetting(val upper: Float, val lower: Float = 0.0f, val places: Int = 2) : VariableSetting()

/**
 * @param newString Der neue Text
 */
data class StringSetting(@JsonProperty("to") val newString: String) : VariableSetting()