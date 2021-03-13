package XMLString;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class XMLStringNode_Text implements XMLStringNode{

    private final XMLStringNodeType nodeType = XMLStringNodeType.TEXT;

    private String content;

    public XMLStringNode_Text(String Content){
        this.content = Content;
    }

    @Override
    public String getNodeName() {
        return "#text";
    }

    @Override
    public XMLStringNodeType getType() {
        return nodeType;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String newContent) {
        this.content=newContent;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public List<XMLStringNode> getChildNodes() {
        return Collections.emptyList();
    }

    @Override
    public List<XMLStringNode> getChildNodesByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public Map<String, String> getAttributes() {
        return Collections.emptyMap();
    }

    @Override
    public String getAttribute(String name) {
        return "";
    }

    @Override
    public void setAttribute(String name, String value) {
    }

    @Override
    public List<XMLStringNode> getSubNodesWithName(String name) {
        return Collections.emptyList();
    }

    @Override
    public List<XMLStringNode> getSubNodesWithPath(String path) {
        return Collections.emptyList();
    }

    @Override
    public void toDomNode(Document doc, Node e) {
        e.appendChild( doc.createTextNode( content ) );
    }

    @Override
    public String DumpData() {
        return "TextNode: "+(content.matches( "[\\n\\s]*" ) ? content.replace( '\n','n' ).replace( ' ','0' ) : content.replace( '\n','\\' ));
    }

    @Override
    public String DumpData(int indent) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            b.append( '-' );
        }
        String tempstring = content.matches( "[\\n\\s]*" ) ? content.replace( '\n','n' ).replace( ' ','0' ) : content;
        b.append( "TextNode: " ).append( tempstring ).append( '\n' );
        return b.toString();
    }
}
