package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Item
import onyx.observer.Observer

class ItemTitleChangeJob(
    override val itemFilter: (Item) -> Boolean,
    val newTitle: String
) : ItemChangeJob {

    override fun changeItem(item: Item, queues: JobQueue) {
        Observer.ob.report("Changing Title '${item.title}' to '$newTitle'")
        item.title = newTitle
    }
}

class IdentifierChangeJob(
    override val itemFilter: (Item) -> Boolean,
    val newIdentifier: String
) : ItemChangeJob{

    override fun changeItem(item: Item, queues: JobQueue) {
        queues.sharedState.setIfAbsent(this::class, (0).toChar().toString())

        val counter = queues.sharedState[this::class]?.get(0)?.code
        counter?.run {
            val newId = "${item.title.collapseTitle()}-$newIdentifier-$this"
            Observer.ob.report("Changing Identifier '${item.identifier}' to '$newId'")
            item.identifier = newId
            queues.sharedState[this@IdentifierChangeJob::class] = (counter + 1).toChar().toString()
        }

    }

    private fun String.collapseTitle(): String{
        return this.replace(" ","-").map {
            if( it=='-'||it.code in 'A'.code..'Z'.code || it.code in 'a'.code..'z'.code || it.code in '0'.code..'9'.code) it else (it.code % 25 + 64).toChar()
        }.joinToString(separator = "") { it.toString() }
    }

}