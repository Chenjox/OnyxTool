package chenjox.onyx.jobs

import chenjox.onyx.data.Formeln
import chenjox.onyx.qtilayer.Item
import chenjox.onyx.qtilayer.QTIManager
import chenjox.onyx.qtilayer.expression.StringExpression
import chenjox.onyx.serialisation.aufgaben.*
import chenjox.onyx.xmlLayer.assessmentItem.BaseType
import onyx.observer.Observer

class ItemAddJob(
    val formeln: Formeln,
    val assessment: Assessment,
    val sectionName: String
    ) : QtiChangeJob{

    override fun changeOnyx(qtiManager: QTIManager, queues: JobQueue) {
        Observer.ob.beginSection("Adding Item")
        val identifier = assessment.title.hashCode().toString(16)
        val filter : (Item) -> Boolean = { it.identifier == identifier }
        Observer.ob.report("Copying '${assessment.copyOf}' in '$sectionName'")
        qtiManager.copyItem(
            identifier,
            { it.title == sectionName },
            { it.title == assessment.copyOf }
        )
        assessment.image.forEach {
            queues.itemChangeQueue.push(ImgReplaceJob(filter, it.altText, it.imgRef))
            queues.testChangeQueue.push(ImgTestAddJob(it.imgRef))
        }
        Observer.ob.report("Preparing Variables")
        assessment.variables.forEach {
            when(val setting = it.set){
                is FormulaSetting -> {
                    val data = formeln[setting.id]
                    if (data != null) {
                        queues.itemChangeQueue.push(FormulaReplaceJob(filter, it.id, data, setting.modifier))
                    }
                }
                is RandomSetting -> {
                    queues.itemChangeQueue.push(
                        RandomSetterJob(
                            itemFilter = filter,
                            it.id,
                            setting.upper,
                            setting.lower,
                            setting.places
                        )
                    )
                }
                is StringSetting -> {
                    queues.itemChangeQueue.push(
                        ExpressionReplaceJob(
                            filter,
                            it.id,
                            BaseType.STRING,
                            StringExpression(setting.newString)
                        )
                    )
                }
            }
        }
        assessment.preprocessing.forEach {
            when(val pre = it.setting){
                is RenameSetting -> {
                    queues.itemChangeQueue.push(RenameVariableJob(
                        itemFilter = filter,
                        templateIdentifier = it.id,
                        newTemplateIdentifier = pre.newId
                    ))
                }
                is SwitchSetting -> {
                    queues.itemChangeQueue.push(SwitchVariableJob(
                        itemFilter = filter,
                        templateIdentifier1 = it.id,
                        templateIdentifier2 = pre.switchId
                    ))
                }
            }
        }
        queues.itemChangeQueue.push(ItemTitleChangeJob(filter, assessment.title))
        Observer.ob.endSection("Adding Item")
    }

}