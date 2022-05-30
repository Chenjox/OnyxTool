package commandArguments

import kotlin.io.path.Path
import kotlin.io.path.isRegularFile


class CommandParser {

    fun parse(array: Array<String>): List<Command>{
        val result : MutableList<Command> = ArrayList()
        var pos = 0
        while (pos < array.size){
            val current = array[0]
            when(current){
                in listOf("-f","--file") -> {
                    if(pos+1<array.size) {
                        val f = array.consumeNext(pos)
                        pos++
                        val aufgabenFile = Path(f)
                        if(aufgabenFile.isRegularFile()){
                            result.add(OnyxStartCommand(aufgabenFile))
                        }
                    }else{
                        result.add(ErrorReportCommand("No Argument found!"))
                    }
                }
                in listOf("-e", "--exit") -> {
                    result.add(ExitCommand)
                }
                in listOf("-h", "--help") -> {
                    result.add(HelpCommand)
                }
                else -> {
                    result.add(ErrorReportCommand("No valid Command!"))
                }
            }
            pos++
        }
        return result
    }

    private fun Array<String>.consumeNext(currentPos: Int): String{
        return this[currentPos+1]
    }

}