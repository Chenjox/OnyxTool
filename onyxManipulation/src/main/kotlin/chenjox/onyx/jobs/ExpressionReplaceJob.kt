package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Item
import chenjox.onyx.qtilayer.expression.QTIExpression
import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import onyx.observer.Observer

class ExpressionReplaceJob(
    override val itemFilter: (Item) -> Boolean,
    val templateIdentifier: String,
    val baseType: BaseType,
    val expression: QTIExpression
) : ItemChangeJob {

    override fun changeItem(item: Item, queues: JobQueue) {
        Observer.ob.report("Setting '$templateIdentifier' in '${item.title}' to a new Expression")
        item.setExpressionFor(templateIdentifier, expression)
        item.setBaseTypeFor(templateIdentifier, baseType)
    }

}