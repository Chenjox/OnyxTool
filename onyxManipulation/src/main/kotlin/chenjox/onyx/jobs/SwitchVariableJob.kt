package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Item
import chenjox.onyx.qtilayer.expression.ConstantExpression
import chenjox.onyx.qtilayer.expression.QTIExpression
import chenjox.onyx.qtilayer.expression.StringExpression
import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import onyx.observer.Observer

class SwitchVariableJob(
    override val itemFilter: (Item) -> Boolean,
    val templateIdentifier1: String,
    val templateIdentifier2: String
) : ItemChangeJob {
    override fun changeItem(item: Item, queues: JobQueue) {

        if(!(item.hasTemplateIdentifier(templateIdentifier1) || item.hasTemplateIdentifier(templateIdentifier2))){
            throw IllegalStateException("No '$templateIdentifier1' or '$templateIdentifier2' in '${item.title}' found.")
        }

        // val templateBaseType1 = item.getBaseTypeFor(templateIdentifier1)
        // val expression1 = getDummyExpression(templateBaseType1)
        // val templateBaseType2 = item.getBaseTypeFor(templateIdentifier2)
        // val expression2 = getDummyExpression(templateBaseType2)

        Observer.ob.report("Switching '$templateIdentifier1' with '$templateIdentifier2' in '${item.title}'")
        item.setTemplateName(templateIdentifier1, "dummy")
        item.setTemplateName(templateIdentifier2, templateIdentifier1)
        item.setTemplateName("dummy", templateIdentifier2)

    }

    private fun getDummyExpression(type: BaseType): QTIExpression{
        return when(type){
            BaseType.INTEGER -> {
               ConstantExpression(3)
            }
            BaseType.FLOAT -> {
                ConstantExpression(3)
            }
            BaseType.STRING -> {
                StringExpression("DUMMY")
            }
        }

    }
}