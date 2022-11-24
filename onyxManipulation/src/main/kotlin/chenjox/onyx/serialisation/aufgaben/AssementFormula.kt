package chenjox.onyx.serialisation.aufgaben

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * @param id Id der Formel in der Formelsammlung
 * @param modifier Optionaler Modifier für das Erhalten der Formel
 */
data class FormulaSetting(
    val id: String,
    @JsonProperty("mod") val modifier: FormulaModifier? = null
    ) : VariableSetting()

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonSubTypes(
    JsonSubTypes.Type(value = RoundModifier::class, name = "round"),
    //JsonSubTypes.Type(value = )
)
sealed class FormulaModifier{

    abstract fun String.applyModifier(): String

}

/**
 * Ein Modifier für das automatische Runden
 * @param places Anzahl der Nachkommastellen
 */
data class RoundModifier(val places: Int) : FormulaModifier() {

    override fun String.applyModifier(): String {
        val num = "1".padEnd(places+1,'0')
        return "block([wert], wert: $this, (signum(wert)*truncate(abs(wert)*$num + 0.5)/$num)"
    }

}
