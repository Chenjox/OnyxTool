package chenjox.onyx

import chenjox.onyx.serialisation.OnyxManipulationReader
import onyx.observer.Observer
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.Path
/*
fun main(args: Array<String>) {
    val filename = Path((args.firstOrNull() ?: "aufgaben.toml"))
    if(Files.exists(filename)){
        val b = OnyxManipulationReader(filename)
        val manipulator = b.read()
        val error = manipulator.formulas.checkFormulas()
        if(error.isNotEmpty()) {
            error.forEach {
                println(it.toErrorString())
            }
        }else{
            manipulator.work()
        }
        //TODO -> aus Assessment eine handlungsanweisung ableiten
        //TODO -> Error checking bei Formeln (fail fast und so)
        //TODO ADOC Docs!!!! !!!
    }
}
*/
fun start(path: Path){
    if(Files.exists(path)){
        val b = OnyxManipulationReader(path)
        val manipulator = b.read()
        val error = manipulator.formulas.checkFormulas()
        if(error.isNotEmpty()) {
            Observer.ob.beginSection("Formula Errors")
            error.forEach {
                Observer.ob.report(it.toErrorString())
            }
            Observer.ob.endSection("Formula Errors")
        }else{
            Observer.ob.beginSection("OnyxManipulation")
            manipulator.work()
            Observer.ob.endSection("OnyxManipulation")
        }
    }
}