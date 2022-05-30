package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Item
import onyx.observer.Observer

fun Queue<ItemChangeJob>.pushCondiotionalJob(itemFilter: (Item) -> Boolean, job: ItemChangeJob, condition: (Item) -> Boolean){
    push(CheckConditionJob(
        itemFilter,
        condition
    ) { job })
}

class CheckConditionJob(
    override val itemFilter: (Item) -> Boolean,
    val condition: (Item) -> Boolean,
    val action: () -> ItemChangeJob) : ItemChangeJob {

    override fun changeItem(item: Item, queues: JobQueue) {
        if(condition(item)) {

            queues.itemChangeQueue.push(action.invoke())
        }
    }

}