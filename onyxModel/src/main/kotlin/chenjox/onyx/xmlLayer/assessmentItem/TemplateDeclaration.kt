package chenjox.onyx.xmlLayer.assessmentItem

import dsl.ElementBuilder
import dsl.element
import dsl.inspect
import org.w3c.dom.Element

public class TemplateDeclaration(
    public val domElement: Element
) {

    public var baseType: BaseType
        get() {
            var s : BaseType? = null
            domElement.inspect {
                s = findAttr("baseType")?.let { BaseType.fromString( it ) }
            }
            return s ?: throw IllegalStateException("No BaseType inside <templateDeclaration> !")
        }
        set(value) {
            domElement.inspect {
                set("baseType", value.toStrings())
            }
        }

    // ? Was war das nochmal??
    public var cardinality: String
        get() {
            var s : String? = null
            domElement.inspect {
                s = findAttr("cardinality")
            }
            return s ?: throw IllegalStateException("No cardinality inside <templateDeclaration> !")
        }
        set(value) {
            domElement.inspect {
                set("cardinality", value)
            }
        }

    public var identifier: String
        get() {
            var s : String? = null
            domElement.inspect {
                s = findAttr("identifier")
            }
            return s ?: throw IllegalStateException("No identifier inside <templateDeclaration> !")
        }
        set(value) {
            domElement.inspect {
                set("identifier", value)
            }
        }

    /**
     * Inserts a setTemplateValue tag with the given Expression before this node and returns the templateValue
     */
    internal fun insertBefore(templateIdentifier: String, baseType: BaseType, cardinality: String = "single") : TemplateDeclaration{

        val new = domElement.ownerDocument.element("templateDeclaration") {
            attributes {
                "identifier" with templateIdentifier
                "baseType" with baseType.qtiName
                "cardinality" with cardinality
            }
        }
        domElement.parentNode.insertBefore(
            new,
            domElement
        )
        return TemplateDeclaration(new)
    }

    /**
     * Inserts a setTemplateValue tag with the given Expression after this node and returns the templateValue
     */
    internal fun insertAfter(templateIdentifier: String, baseType: BaseType, cardinality: String = "single") : TemplateDeclaration{
        val new = domElement.ownerDocument.element("templateDeclaration") {
            attributes {
                "identifier" with templateIdentifier
                "baseType" with baseType.qtiName
                "cardinality" with cardinality
            }
        }
        val sib = domElement.nextSibling
        if(sib!=null){
            domElement.parentNode.insertBefore(new, sib)
        }else{
            domElement.parentNode.appendChild(new)
        }
        return TemplateDeclaration(new)
    }

}