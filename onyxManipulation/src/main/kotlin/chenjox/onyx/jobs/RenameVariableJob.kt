package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Item
import onyx.observer.Observer

class RenameVariableJob(
    override val itemFilter: (Item) -> Boolean,
    val templateIdentifier: String,
    val newTemplateIdentifier: String
) : ItemChangeJob {

    override fun changeItem(item: Item, queues: JobQueue) {
        Observer.ob.report("Renaming '$templateIdentifier' to '$newTemplateIdentifier' in '${item.title}'")
        item.setTemplateName(templateIdentifier, newTemplateIdentifier)
    }

}