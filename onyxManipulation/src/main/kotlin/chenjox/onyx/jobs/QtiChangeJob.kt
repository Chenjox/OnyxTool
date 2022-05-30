package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.QTIManager

interface QtiChangeJob {

    fun changeOnyx(qtiManager: QTIManager, queues: JobQueue)

}