package chenjox.onyx

import chenjox.onyx.data.Formeln
import chenjox.onyx.jobs.IdentifierChangeJob
import chenjox.onyx.jobs.ItemAddJob
import chenjox.onyx.jobs.JobQueue
import chenjox.onyx.jobs.qtiIdentifierOrdering
import chenjox.onyx.qtilayer.QTIManager
import chenjox.onyx.qtilayer.QTIManagerBuilder
import chenjox.onyx.serialisation.aufgaben.Assessment
import chenjox.onyx.serialisation.aufgaben.FormulaSetting
import chenjox.onyx.serialisation.aufgaben.RandomSetting
import chenjox.onyx.serialisation.aufgaben.RoundModifier
import onyx.observer.Observer
import java.nio.file.Path
import kotlin.io.path.isReadable
import kotlin.io.path.name

class OnyxManipulator(
    val formulas: Formeln,
    val inputDir: Path,
    val outputDir: Path,
    val assessments: List<Assessment>,
    val idOrdering: Boolean
) {



    fun work(){
        val qti = qtiManager()
        val queues = JobQueue()
        queues.populate()
        Observer.ob.beginSection("First Pass")
        while (!queues.queuesEmpty()){
            queues.workStep(qti)
        }
        Observer.ob.endSection("First Pass")
        queues.populateLast()
        Observer.ob.beginSection("2nd Pass")
        while (!queues.queuesEmpty()){
            queues.workStep(qti)
        }
        Observer.ob.endSection("2nd Pass")
        qti.serialize(outputDir.toFile())
    }

    private fun qtiManager(): QTIManager {
        val imspath = inputDir.resolve("imsmanifest.xml")
        if(imspath.isReadable()) {
            return QTIManagerBuilder(imspath.toFile()).build()
        }else throw IllegalArgumentException("Found no imsmanifest.xml inside ${imspath.name}")
    }


    private fun JobQueue.populate(){
        assessments.forEach {
            qtiChangeQueue.push(ItemAddJob(formulas, it, "Aufgaben")) //FIXME Section auswählen
        }
    }

    private fun JobQueue.populateLast(){
        if(idOrdering){
            qtiChangeQueue.push(qtiIdentifierOrdering())
        }
    }

}