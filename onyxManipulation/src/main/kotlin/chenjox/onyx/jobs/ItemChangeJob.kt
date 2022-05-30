package chenjox.onyx.jobs

import chenjox.onyx.qtilayer.Item
import kotlin.reflect.KClass

interface ItemChangeJob {

    val itemFilter: (Item) -> Boolean
    fun changeItem(item: Item, queues: JobQueue)

    operator fun MutableMap<String, String>.set(klass: KClass<*>, value: String) = klass.simpleName?.let { put(it, value) }
    fun MutableMap<String, String>.setIfAbsent(klass: KClass<*>, value: String) = klass.simpleName?.let { putIfAbsent(it, value) }
    operator fun MutableMap<String, String>.get(klass: KClass<*>) = this[klass.simpleName]

    fun MutableMap<String, String>.setValue(value: String) = set(this@ItemChangeJob::class, value)
    fun MutableMap<String, String>.setValueIfAbsent(value: String) = setIfAbsent(this@ItemChangeJob::class, value)
    fun MutableMap<String, String>.getValue(): String? = get(this@ItemChangeJob::class)
}