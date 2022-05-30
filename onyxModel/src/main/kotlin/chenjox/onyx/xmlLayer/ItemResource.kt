package chenjox.onyx.xmlLayer

import dsl.inspect
import org.w3c.dom.Element

/**
 * "Shorthand" für eine Ressource: sorgt dafür, dass der Identifier immer überschrieben wird.
 */
public class ItemResource(override val domElement: Element, public val dependencyElement: Element) : ResourceElement {

    /**
     * Verändert den Identifier der Ressource im Imsmanifest
     */
    public var identifier: String
        get() {
            var s : String = ""
            domElement.inspect {
                s = findAttr("identifier")!!
            }
            return s
        }
        set(value) {
            domElement.inspect {
                set("href", "$value.xml")
                set("identifier", value)
                inspectElement("file"){
                    set("href", "$value.xml")
                }
            }
            dependencyElement.inspect {
                set("identifierref",value)
            }
        }

    /**
     * Gibt den Dateinamen des Items an.
     */
    public val associatedFileName: String
        get() {
            var s : String? = null
            domElement.inspect {
                inspectElement("file") {
                    s = findAttr("href")
                }
            }
            return s ?: throw IllegalStateException("Resource has no href attribute!")
        }

    public fun normalizeIdentifiers(){
        identifier = "${identifier.replace('_','-')}-norm"
    }

}