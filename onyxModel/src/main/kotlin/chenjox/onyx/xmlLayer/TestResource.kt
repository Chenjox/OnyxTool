package chenjox.onyx.xmlLayer

import dsl.appendChildElement
import dsl.appendChildElementReturnRef
import dsl.appendChildWithText
import dsl.inspect
import mu.KotlinLogging
import org.w3c.dom.Element

private val logger = KotlinLogging.logger {}

/**
 * A class representing the qti test item resource contained inside the imsmanifest data tree.
 * It will cross reference the file resource as well as the domElement resource
 */
public class TestResource(override val domElement: Element, private val embeddedFileElement: Element ) : ResourceElement {

    /**
     * A Property representing the Identifier of the specified resource.
     * Note that a change in this value needs to be reflected in the resource tree, else the loading back into onyx will fail.
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
            logger.trace { "Changing identifier of test to $value!" }
            domElement.inspect {
                set("identifier",value)
                set("href", "$value.xml")
                inspectElement("metadata"){
                    inspectElement("lom"){
                        inspectElement("general") {
                            inspectElement("identifier") {
                                removeChildElement("entry")
                                w3c.appendChildWithText("entry", value) {}
                            }
                        }
                    }
                }
            }
            embeddedFileElement.inspect {
                set("href", "$value.xml")
            }
        }

    /**
     * Gibt den Dateinamen des Items an.
     */
    public val associatedFileName: String
        get() {
            var s : String? = null
            embeddedFileElement.inspect {
                s = findAttr("href")
            }
            return s ?: throw IllegalStateException("Resource has no href attribute!")
        }

    /**
     * normalizes the identifier: replacing all `_` with `-` as well as appending `-norm` to the end of the identifier.
     * Important to cross-reference this identifier to other resources.
     */
    public fun normalizeIdentifiers(){
        identifier = "${identifier.replace('_','-')}-norm"
    }

    /**
     * Adds the img Dependency to the testRessource inside the imsmanifest
     */
    public fun addImgDependency(href: String){
        domElement.appendChildElement("file") {
            attributes {
                "href" with href
            }
        }
    }

    /**
     * Adds the Item Dependency to the testRessource inside the imsmanifest
     * To work properly a <resource> tag must be added to the imsmanifest <resources!>
     */
    internal fun addItemDependency(identifierref: String): Element{
        return domElement.appendChildElementReturnRef("dependency") {
            attributes {
                "identifierref" with identifierref
            }
        }
    }

    //TODO Media Files!

    /*
    public fun addDependency(identifier: String){
        logger.debug { "Adding dependency $identifier to ${this.identifier}" }
        domElement.appendChildElement("dependency"){
            attributes {
                "identifierref" with identifier
            }
        }
    }

    public fun removeDependency(identifier: String){
        logger.debug { "Trying removal of dependency $identifier from ${this.identifier}" }
        domElement.inspect {
            inspectElements("dependency"){
                val href = findAttr("identifierref")
                logger.trace { "Found $href in attribute 'identifierref'" }
                if(href!=null && href == identifier){
                    logger.trace { "Removing $href from ${this@TestResource.identifier}" }
                    domElement.removeChild(this.w3c)
                }
            }
        }
    }

    public fun replaceDependency(oldIdentifier: String, identifier: String){
        logger.debug { "Replacing dependency $oldIdentifier with $identifier in ${this.identifier}" }
        domElement.inspect {
            inspectElements("dependency"){
                val attrIdentifier = findAttr("identifierref")
                if(attrIdentifier!=null && attrIdentifier == oldIdentifier){
                    set("identifierref", identifier)
                }
            }
        }
    }
    */

}