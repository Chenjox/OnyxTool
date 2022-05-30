package dsl

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMResult
import javax.xml.transform.dom.DOMSource

@XmlDslMarker
public fun Document.element(name: String, func: ElementBuilder.() -> Unit): Element{
    val e = ElementBuilder(name, this)
    e.func()
    return e.build()
}

@XmlDslMarker
public fun Document.elementWithText(tagName: String, text: String, func: AttributeBuilder.() -> Unit = {  }): Element{
    val e = createElement(tagName)
    val t = createTextNode(text)
    val attr : AttributeBuilder = object : AttributeBuilder {
        override fun String.with(other: String) {
            e.setAttribute(this, other)
        }
    }
    attr.func()
    e.appendChild(t)
    return e
}

@XmlDslMarker
public fun Document.deepCopy() : Document {
    val tfactory = TransformerFactory.newInstance()
    val tx = tfactory.newTransformer()
    val source = DOMSource(this)
    val result = DOMResult()
    tx.transform(source, result)
    return result.node as Document
}

/**
 * A Builder creating to Element DOM Nodes with a nice and comprehensive Syntax
 * It should not be instantiated directly, instead, it should be called in the context of an inspector.
 */
@XmlDslMarker
public class ElementBuilder(private val tagname: String, private val parentDoc: Document){

    private val attributes: MutableMap<String, String> = HashMap()
    private val children: MutableList<Node> = ArrayList()

    /**
     * Opens a block to append Attributes to the Element Node
     */
    @XmlDslMarker
    public fun attributes(func: AttributeBuilder.() -> Unit){
        val b = object : AttributeBuilder {
            override fun String.with(other: String) {
                this@ElementBuilder.attributes[this] = other
            }
        }
        b.func()
    }

    /**
     * Opens a block of an [ElementBuilder] which is appended to the enclosing node as a child.
     */
    @XmlDslMarker
    public fun childElement(name: String, func: ElementBuilder.() -> Unit){
        val builder = ElementBuilder(name,parentDoc)
        builder.func()
        children.add(builder.build())
    }

    /**
     * Appends a child text node to this element node
     */
    @XmlDslMarker
    public fun childText(text: String){
        children.add(parentDoc.createTextNode(text))
    }

    internal fun build(): Element{
        val e = parentDoc.createElement(tagname)
        attributes.forEach {
            e.setAttribute(it.key, it.value)
        }
        children.forEach {
            e.appendChild(it)
        }
        return e
    }
}

/**
 * The Interface defining an Attribute builder, supplies methods to create and modify attributes.
 */
@XmlDslMarker
public interface AttributeBuilder{

    /**
     * Appends a key value pair to the attributes.
     * ```kotlin
     * ElementBuilder("someElement",document) {
     *   attributes {
     *     "key" with "value"
     *   }
     * }
     * ```
     * would create
     * ```xml
     * <someElement key="value"/>
     * ```
     *
     */
    @XmlDslMarker
    public infix fun String.with(other: String)

    @XmlDslMarker
    @Deprecated(
        message = "Use AttributeBuilder#with instead!", replaceWith = ReplaceWith("this.with(other)")
    )
    public infix fun String.to(other: String) { this.with(other) }

}

/**
 * Convenience function to append a child element to an existing node.
 */
@XmlDslMarker
public fun Element.appendChildElement(name: String, func: ElementBuilder.() -> Unit){
    val new = this.ownerDocument.element(name, func)
    this.appendChild(new)
}

/**
 * Convenience function to append a child element to an existing node, and returning it.
 */
@XmlDslMarker
public fun Element.appendChildElementReturnRef(name: String, func: ElementBuilder.() -> Unit): Element{
    val new = this.ownerDocument.element(name, func)
    this.appendChild(new)
    return new
}
/**
 * Convenience function to append a child element with a text node to an existing node.
 * ```
 * element.appendChildWithText("Test", "Test Text"){}
 * ```
 * would result in
 * ```xml
 * <element>
 *     <Test>Test Text</Test>
 * </element>
 * ```
 */
@XmlDslMarker
public fun Element.appendChildWithText(name: String, text: String, func: AttributeBuilder.() -> Unit){
    val new = this.ownerDocument.elementWithText(name,text,func)
    this.appendChild(new)
}