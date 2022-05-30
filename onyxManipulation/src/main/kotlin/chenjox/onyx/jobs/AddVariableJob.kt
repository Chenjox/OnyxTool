package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Item
import chenjox.onyx.qtilayer.expression.QTIExpression
import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import onyx.observer.Observer


@JvmInline
value class AddMode(val mode: Int)

class AddVariableJob(
    override val itemFilter: (Item) -> Boolean,
    val newTemplateIdentifier: String,
    val mode: AddMode = MODE_INSERT_BEFORE,
    val oldTemplateIdentifier: String = "",
    val baseType: BaseType,
    val expression: QTIExpression
) : ItemChangeJob {

    companion object{
        val MODE_INSERT_BEGINNING = AddMode(0)
        val MODE_INSERT_BEFORE = AddMode(1)
        val MODE_INSERT_AFTER = AddMode(2)
    }

    override fun changeItem(item: Item, queues: JobQueue) {
        when(mode.mode){
            0 -> {
                Observer.ob.report("Inserting '$newTemplateIdentifier' into '${item.title}'")
                item.insertTemplateAtBeginning(
                    newTemplateIdentifier,
                    baseType,
                    expression
                )
            }
            1 -> {
                Observer.ob.report("Inserting '$newTemplateIdentifier' into '${item.title}' before '$oldTemplateIdentifier'")
                item.insertTemplateBefore(
                    oldTemplateIdentifier,
                    newTemplateIdentifier,
                    baseType,
                    expression
                )
            }
            2 -> {
                Observer.ob.report("Inserting '$newTemplateIdentifier' into '${item.title}' after '$oldTemplateIdentifier'")
                item.insertTemplateAfter(
                    oldTemplateIdentifier,
                    newTemplateIdentifier,
                    baseType,
                    expression
                )
            }
        }
    }

}