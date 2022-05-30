package chenjox.onyx.qtilayer

import chenjox.onyx.xmlLayer.AssessmentItem
import chenjox.onyx.xmlLayer.AssessmentItemBuilder
import chenjox.onyx.xmlLayer.AssessmentTest
import chenjox.onyx.xmlLayer.Imsmanifest
import chenjox.onyx.xmlLayer.assessmentTest.Section
import dsl.inspect
import dsl.toFile
import org.w3c.dom.Document
import java.io.File

public class QTIManager(
    private val imsmanifest: Imsmanifest,
    private val assessmentTest: AssessmentTest,
    assessmentItems: List<AssessmentItem>,
    public val test :Test,
    items :List<Item>
) {

    private val _assessmentItems: MutableList<AssessmentItem> = assessmentItems.toMutableList()
    private val assessmentItems: List<AssessmentItem> = _assessmentItems

    private val _items: MutableList<Item> = items.toMutableList()
    public val items :List<Item> = _items

    public fun serialize(dir: File){
        if(dir.isDirectory) {

            val imsF = File(dir.absolutePath + "\\" + "imsmanifest.xml")
            imsmanifest.document.toFile(imsF)

            val testF = File(dir.absolutePath + "\\"+ assessmentTest.identifier+".xml")
            assessmentTest.document.toFile(testF)

            assessmentItems.forEach {
                val itemF = File(dir.absolutePath + "\\" + it.identifier +".xml")
                it.document.toFile(itemF)
            }
        }
    }

    public fun findItemWithTitle(title: String) : Item? {
        return items.firstOrNull { it.title == title }
    }
    public fun findItemWithIdentifier(identifier: String) : Item? {
        return items.firstOrNull { it.identifier == identifier }
    }

    public fun addItem(document: Document, filter: (Section) -> Boolean): Item{
        val item = AssessmentItemBuilder(document).build()
        val imsref = imsmanifest.addAssessmentItemRessource(item.identifier)
        val sectionRef = assessmentTest.sections.firstOrNull(filter)?.addAssessmentItemRef(item.identifier) //FIXME Section muss bestimmt werden.

        _assessmentItems.add(item)

        val finishedItem = sectionRef?.let { Item(item, it, imsref) }
        if (finishedItem != null) {
            _items.add(finishedItem)
            return finishedItem
        }else throw AssertionError("An AssessmentItem could not be created!")
    }

    public fun copyItem(newIdentififer: String, toSectionFilter: (Section) -> Boolean, itemFilter: (Item) -> Boolean){
        val item = items.firstOrNull(itemFilter)?.copyOfAssessmentItem()
        if (item != null) {
            item.identifier = newIdentififer
            _items.add( addItem(item.document, toSectionFilter) ) //TODO überprüfen???
        }
    }
}
