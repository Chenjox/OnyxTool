package chenjox.onyx.xmlLayer

import dsl.ElementInspector
import dsl.inspect
import mu.KotlinLogging
import org.w3c.dom.Document
import org.w3c.dom.Element

private val logger = KotlinLogging.logger {}

public class ImsmanifestBuilder(private val manifest: Document) {

    private companion object {
        private val ALLOWED_IMAGE_FORMATS : List<String> = listOf(".png",".jpg")
    }

    private val imageRefs : MutableList<String> = ArrayList()
    private val itemResourceElements: MutableMap<String, Element> = HashMap()
    private val itemResourcesDependencies: MutableMap<String, Element> = HashMap()
    //private val items : MutableList<ItemResource> = ArrayList()
    private lateinit var test : TestResource

    private fun getDependencies() {
        logger.debug { "Inspecting Manifest" }
        manifest.inspect {
            inspectRoot {
                inspectElementIfExists("resources") {
                    inspectElements("resource"){
                        val type = findAttr("type")
                        val identifier = findAttr("identifier")
                        val href = findAttr("href")
                        if(type != null && identifier != null && href != null){
                            when(type){
                                "imsqti_test_xmlv2p1" -> {
                                    test = getTest(href)
                                }
                                "imsqti_item_xmlv2p1" -> {
                                    getFile()
                                }
                                else -> logger.warn { "Found unknown type! $type" }
                            }
                        }
                    }
                }
            }
        }
    }

    public fun build() : Imsmanifest{
        getDependencies()
        val items : List<ItemResource> = itemResourcesDependencies.map {
            val resource = itemResourceElements[it.key]
            if (resource != null) {
                ItemResource(resource, it.value)
            }else throw AssertionError()
        }
        return Imsmanifest(manifest, test, items)
    }

    private fun ElementInspector.getTest(href: String): TestResource{
        logger.debug { "Found Test File!" }
        val domElement = this.w3c
        var domFileElement : Element? = null
        logger.trace { "Extracting files..." }
        inspectElements("file"){
            val hrefFile = findAttr("href")
            if (hrefFile!=null){
                when {
                    href == hrefFile -> {
                        logger.debug { "Found <file href=\"$hrefFile\"> \\\\ Found the EAssessment Test File!" }
                        domFileElement = w3c
                    }
                    hrefFile.run {
                        ALLOWED_IMAGE_FORMATS.forEach { if(this.endsWith(it)) return@run true }
                        return@run false
                    } -> {
                        logger.debug { "Found <file href=\"$hrefFile\"> \\\\ Found an image dependency!" }
                        imageRefs.add(hrefFile)
                    }
                    else -> {
                        logger.warn { "Found an illegal file href! <file href=\"$hrefFile\">" }
                    }
                }
            }
        }
        inspectElements("dependency"){
            val id = findAttr("identifierref")!!
            itemResourcesDependencies[id] = w3c
        }
        return TestResource(domElement, domFileElement!!)
    }

    private fun ElementInspector.getFile(){
        logger.debug { "Found Item File" }
        val identifier = findAttr("identifier")!!

        itemResourceElements[identifier] = w3c
    }

}