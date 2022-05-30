package commandArguments

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal

object ExitCommand : Command {
    override fun execute(terminal: Terminal): Boolean {
        terminal.println(TextColors.red("Closing Down"))
        return true
    }
}