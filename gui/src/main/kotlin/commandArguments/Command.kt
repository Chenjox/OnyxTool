package commandArguments

import com.github.ajalt.mordant.terminal.Terminal

interface Command {

    /**
     * @return Whether the application closes or not
     */
    fun execute(terminal: Terminal): Boolean

}