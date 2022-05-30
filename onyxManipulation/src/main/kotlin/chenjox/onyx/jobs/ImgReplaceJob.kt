package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Item
import onyx.observer.Observer

class ImgReplaceJob(
    override val itemFilter: (Item) -> Boolean,
    val altText: String,
    val newRef: String
) : ItemChangeJob {

    override fun changeItem(item: Item, queues: JobQueue) {
        Observer.ob.report("Changing image reference of '$altText' to '$newRef' in '${item.title}'")
        item.setImgReference(newRef) {
            it.alt == altText
        }
    }

}