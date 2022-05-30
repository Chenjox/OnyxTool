package chenjox.onyx.jobs

import chenjox.onyx.data.Formeln
import chenjox.onyx.qtilayer.Item
import chenjox.onyx.qtilayer.expression.MaximaExpression
import chenjox.onyx.qtilayer.expression.QTIExpression
import chenjox.onyx.serialisation.aufgaben.FormulaModifier
import onyx.observer.Observer

class FormulaReplaceJob(
    override val itemFilter: (Item) -> Boolean,
    val templateIdentifier: String,
    val formula: Formeln.Data,
    val modifier: FormulaModifier? = null
) : ItemChangeJob {

    override fun changeItem(item: Item, queues: JobQueue) {
        Observer.ob.report("Changing '$templateIdentifier' in '${item.title}' to '${formula.formula}'")
        item.setExpressionFor(templateIdentifier , getTemplateExpression())
        item.setBaseTypeFor(templateIdentifier, formula.baseType)
    }

    private fun getTemplateExpression(): QTIExpression {
        return if (modifier!=null) MaximaExpression(modifier.run { formula.formula.applyModifier() }) else MaximaExpression(formula.formula)
    }

}