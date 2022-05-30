import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import onyx.observer.StatusObserverBase
import kotlin.math.roundToInt

class TerminalObserver(val terminal: Terminal) : StatusObserverBase() {

    private val indent
        get() = sectionStack.size
    private val currentSection
        get() = sectionStack.peek()

    private fun indentStr(diff: Int = 0) : String{
        return "".padStart(indent - diff, ' ')
    }

    private val regex = Regex("\\'[a-zA-Z 0-9\\_\\!\\;\\(\\)\\[\\]\\{\\}\\-\\.\\+\\*\\\\\\/]+\\'")

    private fun String.highlight() : String{
        return this.replace(regex) { matchResult -> "'"+TextColors.cyan(matchResult.value.substring(1 until matchResult.value.length-1))+"'" }
    }

    override fun beginSec(name: String, msg: String?) {
        with(terminal){
            val secColor = when(sectionStack.size){
                1 -> TextColors.magenta(name)
                2 -> TextColors.yellow(name)
                3 -> TextColors.gray(name)
                else -> TextColors.blue(name)
            }
            if(msg!=null){
                println("${indentStr(1)}[+$secColor]: ${msg.highlight()}")
            } else println("${indentStr(1)}[+$secColor]")
        }
    }

    override fun endSec(name: String, msg: String?) {
        if(msg!=null){
            terminal.println("${indentStr()}[-$name]: ${msg.highlight()}")
        } //else terminal.println("${indentStr()}[-$name]")
    }

    override fun report(msg: String) {
        terminal.println(indentStr()+msg.highlight())
    }

    private val progressMap: MutableMap<String, Progress> = HashMap()

    override fun startProgress(name: String, msg: String, amount: Int) {
        report(msg)
        report("-".padEnd(80,'-'))
        terminal.print(indentStr())
        val p = Progress(0, amount)
        progressMap[name] = p
        //progressAnimation.start()
        //progressAnimation.updateTotal(amount.toLong())
    }

    override fun updateProgress(name: String, amount: Int) {
        progressMap[name]?.let {
            it.current++
            val progress = (it.current.toFloat()/it.total.toFloat() * 80).roundToInt()
            if(progress > it.drawn) {
                terminal.print("#")
                it.drawn++
            }
        }
    }

    override fun finishProgress(name: String) {
        progressMap.remove(name)
        terminal.println()
        report("Finish!")
    }

    private data class Progress(var current: Int, val total: Int, var drawn: Int = 0)
}