package chenjox.onyx.xmlLayer.assessmentTest

import dsl.inspect
import org.w3c.dom.Element

public class AssessmentItemRef(
    public val domElement: Element
) {

    /**
     * Verändert den Identifier der Ressource in der Section
     */
    public var identifier: String
        get() {
            var s : String? = null
            domElement.inspect {
                s = findAttr("identifier")
            }
            return s ?: throw IllegalStateException("Resource has no href attribute!")
        }
        set(value) {
            domElement.inspect {
                set("href", "$value.xml")
                set("identifier", value)
            }
        }

    /**
     * Gibt den Dateinamen des Items an.
     */
    public val associatedFileName: String
        get() {
            var s : String? = null
            domElement.inspect {
                s = findAttr("href")
            }
            return s ?: throw IllegalStateException("Resource has no href attribute!")
        }

    // ? was auch immer das ist
    public var fixed: Boolean
        get() {
            var b = false
            domElement.inspect {
                b = findAttr("fixed")?.let {
                    when(it){
                        "false" -> false
                        "true" -> true
                        else -> throw IllegalStateException("fixed has no valid value!")
                    }
                } ?: throw IllegalStateException("AssessmentItemRef has no 'fixed' attribute!")
            }
            return b
        }
        set(value) {
            domElement.inspect {
                set("fixed", value.toString())
            }
        }

    public fun normalizeIdentifiers(){
        identifier = "${identifier.replace('_','-')}-norm"
    }

}