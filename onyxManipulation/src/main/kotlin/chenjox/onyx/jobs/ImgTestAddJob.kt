package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Test
import onyx.observer.Observer

class ImgTestAddJob(
    val href: String
) : TestChangeJob {

    override fun changeTest(test: Test, queues: JobQueue) {
        Observer.ob.report("Adding '$href' to Test '${test.title}'")
        test.addImgDependency(href)
    }

}