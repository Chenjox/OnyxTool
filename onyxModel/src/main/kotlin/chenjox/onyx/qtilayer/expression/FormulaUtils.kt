package chenjox.onyx.qtilayer.expression

/**
 * ```
 * {PRNG} -> $(1)
 * ```
 */
public fun String.replaceIdentifiersInOrder(ids: List<String>): String{
    var pos = 1
    var res = this
    ids.forEach {
        res = res.replace("{$it}", "\$($pos)")
        pos++
    }
    return res
}

/**
 * Returns the list of Identifiers
 */
public fun String.extractIdentifiers(): List<String> {
    var pos: Int = 0
    val res = ArrayList<String>()
    while (pos < length){
        val c1 = this[pos]
        if (c1=='{'){
            pos++
            var c2 = this[pos]
            val b = StringBuilder()
            while (c2 != '}'){
                b.append(c2)
                pos++
                c2 = this[pos]
            }
            b.toString().let {
                if(it.isNotEmpty()){
                    res.add(it)
                }
            }
        }
        pos++
    }
    return res
}


public fun String.isBalanced(): Boolean {
    var pos = 0
    // es gibt 3 brackets ( [ {
    var result = true
    val stack : Stack<Char> = mutableListOf()
    while (pos < length){
        when(val c = this[pos]){
            '(','[','{' -> stack.push(c)
            ')',']','}' -> {
                val s = stack.pop()
                val isCorrect = when(s){
                    '(' -> c==')'
                    '[' -> c==']'
                    '{' -> c=='}'
                    else -> false
                }
                if(!isCorrect){
                    result = false
                }
            }
        }
        pos++
    }
    return result
}

public fun String.isBalancedWithError(): Int {
    var pos = 0
    var errorPos = -1
    // es gibt 3 brackets ( [ {
    val stack : Stack<Char> = mutableListOf()
    while (pos < length){
        when(val c = this[pos]){
            '(','[','{' -> stack.push(c)
            ')',']','}' -> {
                val isCorrect = when(stack.pop()){
                    '(' -> c==')'
                    '[' -> c==']'
                    '{' -> c=='}'
                    else -> false
                }
                if(!isCorrect){
                    errorPos = pos + 1
                }
            }
        }
        pos++
    }
    if(stack.isNotEmpty() && errorPos == -1) errorPos = length
    return errorPos
}

/**
 * Stack as type alias of Mutable List
 */
private typealias Stack<T> = MutableList<T>

/**
 * Pushes item to [Stack]
 * @param item Item to be pushed
 */
private inline fun <T> Stack<T>.push(item: T) = add(item)

/**
 * Pops (removes and return) last item from [Stack]
 * @return item Last item if [Stack] is not empty, null otherwise
 */
private fun <T> Stack<T>.pop(): T? = if (isNotEmpty()) removeAt(lastIndex) else null

/**
 * Peeks (return) last item from [Stack]
 * @return item Last item if [Stack] is not empty, null otherwise
 */
private fun <T> Stack<T>.peek(): T? = if (isNotEmpty()) this[lastIndex] else null
