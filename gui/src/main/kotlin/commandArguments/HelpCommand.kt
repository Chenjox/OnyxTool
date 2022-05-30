package commandArguments

import com.github.ajalt.mordant.rendering.BorderStyle
import com.github.ajalt.mordant.rendering.TextAlign
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal

object HelpCommand : Command {

    override fun execute(terminal: Terminal): Boolean {
        terminal.helpMenu()
        return false
    }

    fun Terminal.helpMenu(){
        println(
            table {
                borderStyle = BorderStyle.SQUARE_DOUBLE_SECTION_SEPARATOR
                align = TextAlign.RIGHT
                outerBorder = false
                header {
                    style(color = TextColors.magenta)
                    row("Command", "Usage", "Effect")
                }
                body {
                    row("-h", "-h", "Opens this Menu")
                    row("-e", "-e", "Exits the Application")
                    row("-f", "-f PATH", "Shows the aufgaben.toml")
                }
            }
        )
    }

}