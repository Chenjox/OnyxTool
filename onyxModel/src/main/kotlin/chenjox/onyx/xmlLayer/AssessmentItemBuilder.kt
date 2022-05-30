package chenjox.onyx.xmlLayer

import chenjox.onyx.xmlLayer.assessmentItem.ItemBody
import chenjox.onyx.xmlLayer.assessmentItem.Template
import chenjox.onyx.xmlLayer.assessmentItem.TemplateDeclaration
import chenjox.onyx.xmlLayer.assessmentItem.TemplateValue
import dsl.inspect
import mu.KotlinLogging
import org.w3c.dom.Document
import wellusion.ext

private val logger = KotlinLogging.logger {}

public class AssessmentItemBuilder(public val document: Document) {

    public fun build() : AssessmentItem {

        val map : MutableMap<String, TemplateDeclaration> = HashMap()
        var set : MutableSet<String>
        var html : ItemBody? = null
        val templates: MutableList<Template> = ArrayList()

        document.inspect {
            inspectRoot {
                inspectElements("templateDeclaration"){
                    val identifier = findAttr("identifier") ?: throw IllegalArgumentException("Invalid QTI File!")
                    map[identifier] = TemplateDeclaration(w3c)
                }
                inspectElement("itemBody"){
                    html = ItemBody(w3c)
                }
                set = map.keys
                inspectElementIfExists("templateProcessing"){
                    inspectElements("setTemplateValue"){
                        val identifier = findAttr("identifier") ?: throw IllegalArgumentException("Invalid QTI File!")
                        templates.add(
                            Template(
                                map[identifier] ?: throw AssertionError("No processing found for $identifier"),
                                TemplateValue(w3c),
                                html?.templateItemBodyRef?.filter { it.identifier == identifier } ?: emptyList()
                            )
                        )
                        set.remove(identifier)
                    }
                    // Sollte eine Declaration keinen setTemplateValue haben, dann
                    if (set.isNotEmpty()) logger.warn { "Templates ohne Ausdruck gefunden: ${set.joinToString { it }}" }
                }
            }
        }
        return AssessmentItem(
            document,
            templates,
            html ?: throw AssertionError("Not <itemBody> found!")
        )
    }

}