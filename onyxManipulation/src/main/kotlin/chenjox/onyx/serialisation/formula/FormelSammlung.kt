package chenjox.onyx.serialisation.formula

import chenjox.onyx.serialisation.Metadata
import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.*

data class FormelSammlung(
    val formulaConfig: FormelConfiguration,
    val formula: List<Formula>,
    val metadata: Metadata = Metadata()
    )

data class FormelConfiguration(
    val implicitType: BaseType = BaseType.FLOAT
    )

data class Formula(
    val id: String,
    val formula: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val type: BaseType? = null
    )



