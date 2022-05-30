package chenjox.onyx.qtilayer

import chenjox.onyx.xmlLayer.AssessmentItemBuilder
import chenjox.onyx.xmlLayer.AssessmentTestBuilder
import chenjox.onyx.xmlLayer.ImsmanifestBuilder
import chenjox.onyx.xmlLayer.ItemResource
import chenjox.onyx.xmlLayer.assessmentTest.AssessmentItemRef
import chenjox.onyx.xmlLayer.assessmentTest.Section
import dsl.toXML
import wellusion.ext
import java.io.File

public class QTIManagerBuilder(private val imsmanifestFile: File) {

    public fun build(): QTIManager {
        val d = imsmanifestFile.toXML()
        d.normalizeDocument()
        d.documentElement.normalize()

        val imsmanifest = ImsmanifestBuilder(d).build()

        val dir = imsmanifestFile.parentFile.absolutePath

        val testFile = File(dir+ "\\" + imsmanifest.test.associatedFileName)
        val assessmentTestFile = testFile.toXML()
        assessmentTestFile.normalizeDocument()
        assessmentTestFile.documentElement.normalize()

        val assessmentTest = AssessmentTestBuilder(assessmentTestFile).build()

        val assessmentItems = imsmanifest.items
            .map { File(dir+ "\\" + it.associatedFileName).toXML() }
            .map {
                it.normalizeDocument()
                it.documentElement.normalize()

                AssessmentItemBuilder(it).build()
            }

        // Ab hier werden keine Files mehr gelesen!
        //

        imsmanifest.normalizeIdentifiers()
        assessmentTest.normalizeIdentifiers()
        assessmentItems.forEach { it.normalizeIdentifiers() }

        val map = HashMap<String, ItemResource>()
        imsmanifest.items.forEach {
            map[it.identifier] = it
        }

        val map3 = HashMap<String, AssessmentItemRef>()
        assessmentTest.sections.forEach {
            it.getItemRefs(map3)
        }

        val finalList: MutableList<Item> = ArrayList()
        assessmentItems.forEach {
            finalList.add(
                Item(
                    assessmentItem = it,
                    assessmentItemRef = map3[it.identifier] ?: throw AssertionError("\"${it.identifier}\""),
                    itemResource = map[it.identifier] ?: throw AssertionError("\"${it.identifier}\"")
                )
            )
        }

        return QTIManager(
            imsmanifest,
            assessmentTest,
            assessmentItems,
            Test(
                assessmentTest,
                imsmanifest.test
            ),
            finalList
        )
    }

}


private fun Section.getItemRefs(map: MutableMap<String, AssessmentItemRef>){
    items.forEach {
        map.put(it.identifier, it)
    }
    subsections.forEach {
        it.getItemRefs(map)
    }
}