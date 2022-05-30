package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.QTIManager

@JvmInline
value class Queue<T>(val list: MutableList<T>){

    fun push(element: T){
        list.add(0, element)
    }

    fun pop(): T?{
        return if(list.isNotEmpty()) list.removeAt(0) else null
    }

    fun isEmpty(): Boolean{
        return list.isEmpty()
    }

}



class JobQueue(
    val itemChangeQueue: Queue<ItemChangeJob> = Queue(ArrayList()),
    val qtiChangeQueue: Queue<QtiChangeJob> = Queue(ArrayList()),
    val testChangeQueue: Queue<TestChangeJob> = Queue(ArrayList()),
    val sharedState: MutableMap<String, String> = HashMap()
) {

    fun workStep(qtiManager: QTIManager){
        when {
            !qtiChangeQueue.isEmpty() -> {
                qtiChangeQueue.pop()?.changeOnyx(qtiManager, this)
            }
            !itemChangeQueue.isEmpty() -> {
                itemChangeQueue.pop()?.let { itemJ ->
                    qtiManager.items.firstOrNull(itemJ.itemFilter)?.apply {
                        itemJ.changeItem(this, this@JobQueue)
                    }
                }
            }
            !testChangeQueue.isEmpty() -> {
                testChangeQueue.pop()?.changeTest(qtiManager.test, this)
            }
        }
    }

    fun queuesEmpty(): Boolean = itemChangeQueue.isEmpty() && qtiChangeQueue.isEmpty() && testChangeQueue.isEmpty()

}