package chenjox.onyx.xmlLayer.assessmentTest

import dsl.inspect
import org.w3c.dom.Element

/**
 * [domElement] is the <testPart> thingy!
 */
public class SectionBuilder(public val domElement: Element) {

    private val subsections: MutableList<Section> = ArrayList()
    private val items: MutableList<AssessmentItemRef> = ArrayList()

    public fun build() : Section {
        domElement.inspect {
            inspectElements("assessmentSection"){
                subsections.add(SectionBuilder(w3c).build())
            }
            inspectElements("assessmentItemRef"){
                items.add(AssessmentItemRef(w3c))
            }
        }
        return Section(domElement, subsections, items)
    }

}