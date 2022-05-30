
import commandArguments.TerminalLoop
import onyx.observer.Observer

fun main(args: Array<String>) {
    val s = TerminalLoop()

    Observer.setObserver(TerminalObserver(s.t))
    s.startLoop(args)


}