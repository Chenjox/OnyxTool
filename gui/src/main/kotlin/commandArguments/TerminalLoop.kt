package commandArguments

import com.github.ajalt.mordant.rendering.BorderStyle
import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.widgets.Panel
import com.github.ajalt.mordant.widgets.Text
import com.github.ajalt.mordant.widgets.UnorderedList


class TerminalLoop(val t: Terminal = Terminal()) {

    private val parser = CommandParser()

    fun startLoop(args: Array<String>){
        t.startMenu()
        var shouldExit = false
        while (!shouldExit){
            t.println(TextColors.green("Please type your command..."))
            val s = readLine()?.split(' ').orEmpty().toTypedArray()
            val r = parser.parse(s)
            r.forEach {
                shouldExit = it.execute(t)
            }
        }
    }

    private fun Terminal.startMenu(){
        println(
            Panel(
                content = TextColors.brightYellow("Onyx Copy Tool"),
                borderStyle = BorderStyle.ASCII_DOUBLE_SECTION_SEPARATOR
            )
        )
        println()
        println("Please give your Command:")
        println(TextColors.green("[-h, --help]"))
        println("  * Opens the help")
        println(TextColors.green("[-e, --exit]"))
        println("  * Exits the application")
        println(TextColors.green("[-f, --file] FILE"))
        println("  * Starts the Onyx Manipulation with the .toml File")
    }



}