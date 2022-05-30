package dsl

import org.w3c.dom.Document
import org.w3c.dom.Element
import wellusion.ElementExt
import wellusion.ext
import java.util.*

@XmlDslMarker
public fun Document.inspect(func: DocumentInspector.() -> Unit){
    val b = DocumentInspector(this)
    b.func()
}

@XmlDslMarker
public fun Element.inspect(func: ElementInspector.() -> Unit){
    val b = ElementInspector(this)
    b.func()
}

@XmlDslMarker
public class DocumentInspector(private val doc: Document) {

    //TODO()!

    @XmlDslMarker
    public fun inspectRoot(func: ElementInspector.() -> Unit){
        val b = ElementInspector(doc.documentElement)
        b.func()
    }

}

/**
 * A class supplying a nice syntax for inspecting element nodes.
 */
@XmlDslMarker
public class ElementInspector(private val root: Element){

    /**
     * returns the tagName of the element node.
     */
    @XmlDslMarker
    public val name : String = root.tagName

    /**
     * The underlying w3c specification implementation.
     */
    @XmlDslMarker
    public val w3c : Element = root

    /**
     * checks whether the element node contains the specified attribute node.
     * @return true if the element has an attribute with the specified name
     */
    @XmlDslMarker
    public fun hasAttr(name: String): Boolean = root.hasAttribute(name)

    /**
     * Finds an attribute and returns its value if present, null otherwise.
     * Checking ElementInspector#hasAttr() first ensures this value to be not null
     */
    @XmlDslMarker
    public fun findAttr(name: String): String? = root.getAttribute(name)

    @XmlDslMarker
    public inline fun expectAttr(name: String, policy: () -> Unit = { TODO("Expected Atribute not found!, Return policy not implemented!") }): Unit {
        findAttr(name) ?: policy.invoke()
    }

    /**
     * Checks whether this element node has child element nodes.
     */
    @XmlDslMarker
    public fun hasChildElem(tagName: String): Boolean{
        val nl = root.childNodes
        for (i in 0 until nl.length){
            val current = nl.item(i)
            if(current is Element && current.tagName == tagName) return true
        }
        return false
    }

    /**
     * Returns the first child element node, or null if none are present
     */
    @XmlDslMarker
    public fun getChildElem(tagName: String): Element?{
        val nl = root.childNodes
        for (i in 0 until nl.length){
            val current = nl.item(i)
            if(current is Element && current.tagName == tagName) return current
        }
        return null
    }

    /**
     * searches the child elements for an element with the specified name, or null if none are found.
     */
    @XmlDslMarker
    public fun searchChildElements(tagName: String): List<Element>{
        val resultList: MutableList<Element> = ArrayList()
        val stack: Deque<Element> = ArrayDeque()
        stack.push(this.w3c)
        while (!stack.isEmpty()) {
            val temp: Element = stack.pop()
            for (n in temp.ext.getAllChildElements()) {
                if (n.nodeName == tagName) resultList.add(n)
                stack.push(n)
            }
        }
        return resultList
    }

    @XmlDslMarker
    public inline fun expectChildElem(tagName: String, policy: () -> Unit){
        getChildElem(tagName) ?: policy.invoke()
    }

    /**
     * Inspects the child element node with the specified tagName, or throws an error if this node is not present.
     */
    @XmlDslMarker
    public fun inspectElement(tagName: String, inspector: ElementInspector.() -> Unit){
        val e = getChildElem(tagName)
        if(e!=null){
            val b = ElementInspector(e)
            b.inspector()
        } else throw AssertionError("Element <$tagName> in <${this.name}> does not exist.")
    }

    /**
     * Inspects the child element node with the specified tagName.
     */
    @XmlDslMarker
    public fun inspectElementIfExists(tagName: String, inspector: ElementInspector.() -> Unit){
        getChildElem(tagName)?.apply {
            val b = ElementInspector(this)
            b.inspector()
        }
    }

    /**
     * applies an inspector over all child element nodes with the specified name
     */
    @XmlDslMarker
    public fun inspectElements(tagName: String, inspector: ElementInspector.() -> Unit){
        val list = root.ext.getAllChildElements(tagName)
        list.forEach {
            ElementInspector(it).inspector()
        }
    }

    /**
     * sets an attribute with the specified name to a new Value
     */
    @EditXmlDslMarker
    public operator fun set(name: String, newValue: String){
        root.setAttribute(name,newValue)
    }

    /**
     * removes the child element of the node with the specified tagName
     */
    @EditXmlDslMarker
    public fun removeChildElement(name: String){
        root.ext.removeChildElement(name)
    }

    /**
     * Replaces the child element node with the specified tagName with a new child node.
     */
    @EditXmlDslMarker
    public fun replaceChildWith(toBeReplaced: String, newTagName: String, newNode: ElementBuilder.() -> Unit){
        root.ext.removeChildElement(toBeReplaced)
        root.appendChildElement(newTagName, newNode)
    }

}

internal val ElementInspector.ext : ElementExt
get() = this.w3c.ext