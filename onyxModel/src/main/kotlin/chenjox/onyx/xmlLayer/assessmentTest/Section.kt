package chenjox.onyx.xmlLayer.assessmentTest

import dsl.appendChildElement
import dsl.appendChildElementReturnRef
import dsl.inspect
import org.w3c.dom.Element

public class Section(
    public val domElement: Element,
    public val subsections: List<Section>,
    items: List<AssessmentItemRef>
) {

    private val _items: MutableList<AssessmentItemRef> = items.toMutableList()
    public val items: List<AssessmentItemRef> = _items

    public var identifier: String
        get() {
            var s : String? = null
            domElement.inspect {
                s = findAttr("identifier")
            }
            return s ?: throw IllegalStateException("No identifier inside <assessmentSection> !")
        }
        set(value) {
            domElement.inspect {
                set("identifier", value)
            }
        }

    public var title: String
        get() {
            var s : String? = null
            domElement.inspect {
                s = findAttr("title")
            }
            return s ?: throw IllegalStateException("No title found!")
        }
        set(value) {
            domElement.inspect {
                set("title",value)
            }
        }

    public fun normalizeIdentifiers(){
        identifier = "${identifier.replace('_','-')}-norm"
        subsections.forEach { it.normalizeIdentifiers() }
        items.forEach { it.normalizeIdentifiers() }
    }

    public fun addAssessmentItemRef(identifier: String): AssessmentItemRef{
        return AssessmentItemRef(domElement.appendChildElementReturnRef("assessmentItemRef"){
            attributes {
                "fixed" with "false"
                "href" with "$identifier.xml"
                "identifier" with identifier
            }
        })
    }

}