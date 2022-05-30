package chenjox.onyx.xmlLayer.assessmentItem

import dsl.ElementBuilder
import dsl.appendChildElement
import dsl.element
import dsl.inspect
import org.w3c.dom.Element
import wellusion.ext

public class TemplateValue(
    public val domElement: Element
) {

    public var identifier: String
        get() {
            var s : String? = null
            domElement.inspect {
                s = findAttr("identifier")
            }
            return s ?: throw IllegalStateException("No identifier in <setTemplateValue>")
        }
        set(value) {
            domElement.inspect {
                set("identifier", value)
            }
        }

    //TODO Was ist wenn es auf Imsassessment Ebene gesetzt wird?
    public val expression: Element
        get() {
            var expr : Element? = null
            domElement.inspect {
                expr = w3c.firstChild as Element
            }
            return expr ?: throw IllegalStateException("No Expression in <setTemplateValue>")
        }
        /*set(value) {
            domElement.inspect {
                w3c.removeChild(w3c.firstChild)
                w3c.appendChild(value)
            }
        }*/

    public fun setExpressionNode(tagname: String, builder: ElementBuilder.() -> Unit){
        domElement.ext.getAllChildElements().firstOrNull()?.run {
            domElement.ext.removeChildElement(this.tagName)
        }
        //domElement.removeChild(domElement.firstChild)
        domElement.appendChildElement(tagname, builder)
    }

    /**
     * Inserts a setTemplateValue tag with the given Expression before this node and returns the templateValue
     */
    internal fun insertBefore(templateIdentifier: String, tagname: String, expressionBuilder: ElementBuilder.() -> Unit) : TemplateValue{

        val new = domElement.ownerDocument.element("setTemplateValue") {
            attributes {
                "identifier" with templateIdentifier
            }
            childElement(tagname, expressionBuilder)
        }
        domElement.parentNode.insertBefore(
            new,
            domElement
            )
        return TemplateValue(new)
    }

    /**
     * Inserts a setTemplateValue tag with the given Expression after this node and returns the templateValue
     */
    internal fun insertAfter(templateIdentifier: String, tagname: String, expressionBuilder: ElementBuilder.() -> Unit) : TemplateValue{
        val new = domElement.ownerDocument.element("setTemplateValue") {
            attributes {
                "identifier" with templateIdentifier
            }
            childElement(tagname, expressionBuilder)
        }
        val sib = domElement.nextSibling
        if(sib!=null){
            domElement.parentNode.insertBefore(new, sib)
        }else{
            domElement.parentNode.appendChild(new)
        }
        return TemplateValue(new)
    }

}

public enum class BaseType(public val qtiName: String){
    INTEGER("integer"),
    FLOAT("float"),
    STRING("string");
    //TODO

    public companion object {
        public fun fromString(str: String): BaseType = when (str) {
            INTEGER.qtiName -> INTEGER
            FLOAT.qtiName -> FLOAT
            else -> throw IllegalArgumentException("Illegal BaseType '$str' found!")
        }
    }

    public fun toStrings() : String = this.qtiName

}