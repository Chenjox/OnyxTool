package commandArguments

import chenjox.onyx.start
import com.github.ajalt.mordant.terminal.Terminal
import onyx.observer.Observer
import java.nio.file.Path

class OnyxStartCommand(val path: Path): Command {
    override fun execute(terminal: Terminal): Boolean {
        Observer.ob.beginSection("Starting Onyx Manipulation")
        start(path = path)
        Observer.ob.endSection("Starting Onyx Manipulation")
        return false
    }
}