package chenjox.onyx.data

import chenjox.onyx.qtilayer.expression.isBalancedWithError
import chenjox.onyx.serialisation.formula.FormelConfiguration
import chenjox.onyx.serialisation.formula.Formula
import chenjox.onyx.xmlLayer.assessmentItem.BaseType

data class Formeln(
    private val formeln: Map<String, Formula>,
    private val globalConfig: FormelConfiguration
){

    operator fun get(id: String): Data? {
        val f = formeln[id]
        return f?.let {
            Data(it.formula, it.type ?: globalConfig.implicitType)
        }
    }

    fun checkFormulas(): List<ErroneousData> {
        val errors : MutableList<ErroneousData> = ArrayList()
        formeln.values.forEach {
            val pos = it.formula.isBalancedWithError()
            if(pos != -1){
                errors.add(ErroneousData(it.id, it.formula, pos))
            }
        }
        return errors
    }

    data class Data(val formula: String, val baseType: BaseType)

    data class ErroneousData(val id: String, val formula: String, val pos: Int){
        fun toErrorString() : String {
            val b = StringBuilder()
            b.append("Error at [$pos]: $formula").appendLine()
            b.append("             ")
                .append("".padStart(pos.toString().length))
                .append("^".padStart(pos)).appendLine()
            return b.toString()
        }
    }

}
