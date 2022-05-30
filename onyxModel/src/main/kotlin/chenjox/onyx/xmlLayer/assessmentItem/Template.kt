package chenjox.onyx.xmlLayer.assessmentItem

import chenjox.onyx.xmlLayer.ImsDslMarker
import dsl.ElementBuilder
import org.w3c.dom.Element

public class Template(
    private val declaration: TemplateDeclaration,
    private val setValue: TemplateValue,
    private val itemBodyRefs : List<TemplateItemBodyRef>
) {
    init {
        check(declaration.identifier==setValue.identifier) {
            "Declarations Identifier and its Value must have the same identifier! ${declaration.identifier} =/= ${setValue.identifier}"
        }
    }

    @ImsDslMarker
    public var identifier: String
        get() {
            return declaration.identifier
        }
        set(value) {
            declaration.identifier = value
            setValue.identifier = value
            itemBodyRefs.forEach {
                it.identifier = value
            }
        }


    @ImsDslMarker
    public var baseType: BaseType
        get() {
            return declaration.baseType
        }
        set(value) {
            declaration.baseType = value
        }


    @ImsDslMarker
    public val expression: Element
        get() {
            return setValue.expression
        }

    @ImsDslMarker
    public fun setExpression(tagname: String, builder: ElementBuilder.() -> Unit){
        setValue.setExpressionNode(tagname, builder)
    }


    internal fun insertBefore(templateIdentifier: String, baseType: BaseType, cardinality: String, expressionTagname: String, expressionbuilder: ElementBuilder.() -> Unit) : Template{
        val value = setValue.insertBefore(templateIdentifier, expressionTagname, expressionbuilder)
        val decla = declaration.insertBefore(templateIdentifier, baseType, cardinality)
        return Template(decla, value, emptyList())
    }

    internal fun insertAfter(templateIdentifier: String, baseType: BaseType, cardinality: String, expressionTagname: String, expressionbuilder: ElementBuilder.() -> Unit) : Template{
        val value = setValue.insertAfter(templateIdentifier, expressionTagname, expressionbuilder)
        val decla = declaration.insertAfter(templateIdentifier, baseType, cardinality)
        return Template(decla, value, emptyList())
    }

}