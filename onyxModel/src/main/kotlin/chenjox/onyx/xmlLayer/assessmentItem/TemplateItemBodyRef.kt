package chenjox.onyx.xmlLayer.assessmentItem

import dsl.inspect
import org.w3c.dom.Element

public class TemplateItemBodyRef(
    public val element: Element
) {

    public var identifier: String
        get() {
            var b : String? = null
            element.inspect {
                b = findAttr("identifier")!!
            }
            return b ?: throw AssertionError("<printedVariable> without Identifier attribute!")
        }
        set(value){
            element.inspect {
                set("identifier",value)
            }
        }

}