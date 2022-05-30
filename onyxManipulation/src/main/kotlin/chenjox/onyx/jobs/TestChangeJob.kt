package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Test

interface TestChangeJob {

    fun changeTest(test: Test, queues: JobQueue)

}