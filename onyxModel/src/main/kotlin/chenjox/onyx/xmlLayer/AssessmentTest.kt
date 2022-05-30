package chenjox.onyx.xmlLayer

import chenjox.onyx.xmlLayer.assessmentTest.Section
import dsl.deepCopy
import dsl.inspect
import org.w3c.dom.Document

public class AssessmentTest(
    public val document: Document,
    public val sections: List<Section>
    ) {

    public var identifier: String
        get() {
            var s : String? = null
            document.inspect {
                inspectRoot {
                    s = findAttr("identifier")
                }
            }
            return s ?: throw IllegalStateException("No identifier in")
        }
        set(value) {
            document.inspect {
                inspectRoot {
                    set("identifier",value)
                }
            }
        }

    public var title: String
        get() {
            var s : String? = null
            document.inspect {
                inspectRoot {
                    s = findAttr("title")
                }
            }
            return s ?: throw IllegalStateException("No title found!")
        }
        set(value) {
            document.inspect {
                inspectRoot {
                    set("title",value)
                }
            }
        }


    public fun normalizeIdentifiers(){
        identifier = "${identifier.replace('_','-')}-norm"
        sections.forEach { it.normalizeIdentifiers() }
    }

    public fun deepClone() : AssessmentTest {
        return AssessmentTestBuilder(document.deepCopy()).build()
    }


    //TODO Sections
    //TODO Namen und Identifier
    //TODO Löschen?
    //TODO Whatever

}