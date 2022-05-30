package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Item
import chenjox.onyx.qtilayer.expression.ConstantExpression
import chenjox.onyx.qtilayer.expression.MaximaExpression
import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import onyx.observer.Observer

class RandomSetterJob(
    override val itemFilter: (Item) -> Boolean,
    val templateIdentifier: String,
    val upper: Float,
    val lower: Float,
    val places: Int
) : ItemChangeJob {

    override fun changeItem(item: Item, queues: JobQueue) {

        val key = "${this::class.simpleName}${item.identifier}"

        queues.sharedState.putIfAbsent(key, (0).toString())

        queues.sharedState[key]?.toInt()?.let {
            if(it == 0){
                queues.itemChangeQueue.push(
                    ExpressionReplaceJob(
                        itemFilter = itemFilter,
                        templateIdentifier = templateIdentifier,
                        baseType = BaseType.FLOAT, //FIXME stimmt das so?
                        expression = MaximaExpression.getRandomExpression(it, upper, lower, places)
                    )
                )
                queues.itemChangeQueue.pushCondiotionalJob(
                    itemFilter,
                    AddVariableJob(
                        itemFilter = itemFilter,
                        newTemplateIdentifier = "PRNG_0",
                        mode = AddVariableJob.MODE_INSERT_AFTER,
                        oldTemplateIdentifier = "INPUT",
                        baseType = BaseType.INTEGER,
                        expression = MaximaExpression.getPrngStartExpression("INPUT")
                    )
                ) { item ->
                    !item.hasTemplateIdentifier("PRNG_0")
                }
                //item.hasTemplateIdentifier("INPUT")
                queues.itemChangeQueue.pushCondiotionalJob(
                    itemFilter,
                    AddVariableJob(
                        itemFilter = itemFilter,
                        newTemplateIdentifier = "INPUT",
                        mode = AddVariableJob.MODE_INSERT_BEGINNING,
                        oldTemplateIdentifier = "",
                        baseType = BaseType.INTEGER,
                        expression = ConstantExpression(123)
                    )
                ) { item ->
                    val b = !item.hasTemplateIdentifier("INPUT")
                    //if(b) Observer.ob.report(item.templates())
                    //Observer.ob.report("'${item.title}' $b satisfies the condition")
                    b
                }


            }else {
                queues.itemChangeQueue.push(
                    ExpressionReplaceJob(
                        itemFilter = itemFilter,
                        templateIdentifier = templateIdentifier,
                        baseType = BaseType.FLOAT, //FIXME stimmt das so?
                        expression = MaximaExpression.getRandomExpression(it, upper, lower, places)
                    )
                )
                val prng = "PRNG_$it"
                queues.itemChangeQueue.pushCondiotionalJob(
                    itemFilter,
                    AddVariableJob(
                        itemFilter = itemFilter,
                        newTemplateIdentifier = prng,
                        mode = AddVariableJob.MODE_INSERT_AFTER,
                        oldTemplateIdentifier = "PRNG_${it - 1}",
                        baseType = BaseType.INTEGER,
                        MaximaExpression.getPrngExpression(it-1)
                        )
                ) { item -> !item.hasTemplateIdentifier(prng) }
            }
            queues.sharedState[key] = (it+1).toString()
        }

    }

}