package chenjox.onyx.xmlLayer

import chenjox.onyx.xmlLayer.assessmentTest.Section
import chenjox.onyx.xmlLayer.assessmentTest.SectionBuilder
import dsl.inspect
import org.w3c.dom.Document

public class AssessmentTestBuilder(public val document: Document) {

    public fun build() : AssessmentTest {
        var sections : List<Section>? = null
        document.inspect {
            inspectRoot {
                inspectElement("testPart"){
                    sections = SectionBuilder(w3c).build().subsections
                }
            }
        }
        return AssessmentTest(document, sections ?: throw IllegalArgumentException("Invalid QTI File!"))
    }

}