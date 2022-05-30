package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.QTIManager

class qtiIdentifierOrdering : QtiChangeJob {

    override fun changeOnyx(qtiManager: QTIManager, queues: JobQueue) {
        qtiManager.items.forEach {
            queues.itemChangeQueue.push(IdentifierChangeJob(
                { item -> item.title == it.title  },
                "mod"
            ))
        }
    }

}