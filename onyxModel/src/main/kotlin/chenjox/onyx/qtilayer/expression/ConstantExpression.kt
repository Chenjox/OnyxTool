package chenjox.onyx.qtilayer.expression

import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import dsl.ElementBuilder

private const val CONSTANT_TAGNAME = "baseValue"
// <baseValue baseType="integer">6</baseValue>
public class ConstantExpression(private val value: Int) : QTIExpression{

    override fun buildNode(): Pair<String, ElementBuilder.() -> Unit> {
        return CONSTANT_TAGNAME to {
            attributes {
                "baseType" with BaseType.INTEGER.qtiName
            }
            childText(value.toString())
        }
    }

}

// <baseValue baseType="integer">6</baseValue>
public class StringExpression(
    private val newValue: String
) : QTIExpression{

    override fun buildNode(): Pair<String, ElementBuilder.() -> Unit> {
        return CONSTANT_TAGNAME to {
            attributes {
                "baseType" with BaseType.STRING.qtiName
            }
            childText(newValue)
        }
    }

}