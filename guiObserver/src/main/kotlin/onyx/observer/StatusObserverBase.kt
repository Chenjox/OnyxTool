package onyx.observer

@JvmInline
public value class Stack<E>(private val list: MutableList<E> = ArrayList()){

    public fun push(element: E){
        list.add(0, element)
    }

    public fun pop(): E{
        return list.removeFirst()
    }

    public fun popOrNull(): E?{
        return list.removeFirstOrNull()
    }

    public fun peek(): E{
        return list.first()
    }

    public fun peekOrNull(): E?{
        return list.firstOrNull()
    }

    public fun isEmpty(): Boolean = list.isEmpty()

    public val size: Int
        get() = list.size
}

public abstract class StatusObserverBase : StatusObserver {

    protected val sectionStack: Stack<String> = Stack()

    final override fun beginSection(name: String, msg: String?) {
        sectionStack.push(name)
        beginSec(name, msg)
    }

    final override fun endSection(name: String, msg: String?) {
        do {
            val s = sectionStack.popOrNull()
        } while (s != null && s != name && !sectionStack.isEmpty())
        endSec(name, msg)
    }

    protected abstract fun beginSec(name: String, msg: String?)
    protected abstract fun endSec(name: String, msg: String?)
}