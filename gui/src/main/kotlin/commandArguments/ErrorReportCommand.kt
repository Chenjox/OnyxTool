package commandArguments

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal

class ErrorReportCommand(val msg: String): Command {

    override fun execute(terminal: Terminal): Boolean {
        terminal.println(TextColors.red(msg))
        return false
    }

}