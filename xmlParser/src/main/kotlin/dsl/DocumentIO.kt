package dsl

import org.w3c.dom.Document
import org.xml.sax.InputSource
import wellusion.DocumentExt
import wellusion.ext
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

public fun File.toXML() : Document {
    val factory = DocumentBuilderFactory.newInstance()
    factory.isIgnoringElementContentWhitespace = true
    val b = factory.newDocumentBuilder()

    return b.parse(InputSource(InputStreamReader(this.inputStream(), Charsets.UTF_8)))
}

public fun Document.toFile(str: File) {
    if(!str.exists()) str.createNewFile()
    val trans = TransformerFactory.newInstance().newTransformer().apply {
        setOutputProperty(OutputKeys.INDENT, "yes")
        setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5")
    }
    val source = DOMSource(this)
    val result = StreamResult( OutputStreamWriter(str.outputStream(), Charsets.UTF_8) )
    trans.transform(source,result)
}