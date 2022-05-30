package chenjox.onyx.xmlLayer

import dsl.appendChildElementReturnRef
import dsl.deepCopy
import dsl.inspect
import org.w3c.dom.Document
import org.w3c.dom.Element

public class Imsmanifest(
    public val document: Document, // Das DOM des Manifests
    public val test: TestResource, // Der Test der drin ist
    Items: List<ItemResource> // die Items die drinnen sind.
) {

    private var _items : MutableList<ItemResource> = Items.toMutableList()
    public val items : List<ItemResource> = _items

    public fun normalizeIdentifiers(){
        _items.forEach {
            it.normalizeIdentifiers()
        }
        test.normalizeIdentifiers()
    }

    /**
     * Appends a QTI AssessmentItemRessource to the Imsmanifest.
     */
    public fun addAssessmentItemRessource(identifier: String): ItemResource{
        var b : Element? = null
        document.inspect {
            inspectRoot {
                inspectElement("resources"){
                     b = w3c.appendChildElementReturnRef("resource"){
                        attributes {
                            "href" with "$identifier.xml"
                            "identifier" with identifier
                            "type" with "imsqti_item_xmlv2p1"
                        }
                        childElement("file"){
                            attributes {
                                "href" with "$identifier.xml"
                            }
                        }
                    }

                }
            }
        }
        val c = test.addItemDependency(identifier)
        val res = ItemResource(b ?: throw AssertionError(""), c)
        _items.add(res)
        return res
    }

    /**
     * Creates a deep copy of the ImsManifest and its Items
     */
    public fun deepClone() : Imsmanifest {
        return ImsmanifestBuilder(document.deepCopy()).build()
    }

}