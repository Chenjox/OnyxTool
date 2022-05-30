package chenjox.onyx.serialisation

import chenjox.onyx.OnyxManipulator
import chenjox.onyx.data.Formeln
import chenjox.onyx.serialisation.aufgaben.OnyxConfig
import chenjox.onyx.serialisation.formula.FormelSammlung
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.name
import kotlin.io.path.pathString
import kotlin.io.path.reader
import com.fasterxml.jackson.module.kotlin.readValue
import onyx.observer.Observer

class OnyxManipulationReader(val config: Path) {

    fun read(): OnyxManipulator {
        Observer.ob.beginSection("Reading Files")
        Observer.ob.report("Reading File '${config.fileName}'")
        val configuration : OnyxConfig = mapper.readValue<OnyxConfig>(config.reader())
        val s = configuration.globalConfig.formulaFile.let { // FIXME Mit und ohne erweiterung!
            config.parent.resolve(it)
            //config.resolve(it)
        }
        val inputDir = configuration.globalConfig.inputDir.let {
            config.parent.resolve(it)
        }
        val outputDir = configuration.globalConfig.outputDir.let {
            config.parent.resolve(it)
        }

        //FIXME Das mit den Templates!

        Observer.ob.report("Input is '${inputDir.name}', Output is '${outputDir.name}'")

        Observer.ob.report("Reading File '${s.name}'")
        if(!Files.exists(s)) throw IllegalArgumentException("File '${s.name}' does not exist in '${s.pathString}'!")


        val formulas : FormelSammlung = mapper.readValue<FormelSammlung>(s.reader())

        Observer.ob.beginSection("Formeln")
        val f = formulas.let { formelSammlung ->
            Observer.ob.startProgress("RF","Parsing Formulas!", formelSammlung.formula.size)
            Formeln(
                globalConfig = formelSammlung.formulaConfig,
                formeln = formelSammlung.formula.associateBy {
                    Observer.ob.updateProgress("RF",1)
                    it.id
                }
            ).apply {
                Observer.ob.finishProgress("RF")
            }
        }
        Observer.ob.endSection("Formeln")

        if(!Files.isDirectory(inputDir)) throw IllegalArgumentException("Input Path '${inputDir.pathString}' is not a directory!")
        if(!Files.isDirectory(outputDir)) when {
            Files.exists(outputDir) -> throw IllegalArgumentException("Output Path '${outputDir.pathString}' is not a directory!")
            Files.notExists(outputDir) -> Files.createDirectories(outputDir)
        }
        Observer.ob.endSection("Reading Files")


        return OnyxManipulator(
            formulas = f,
            inputDir = inputDir,
            outputDir = outputDir,
            assessments = configuration.assessment,
            idOrdering = configuration.globalConfig.idOrdering
        )
    }

}