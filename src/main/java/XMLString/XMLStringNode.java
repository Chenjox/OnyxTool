package XMLString;

import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface XMLStringNode {

    String getNodeName();

    XMLStringNodeType getType();

    String getContent();

    void setContent(String newContent);

    boolean hasChildNodes();

    List<XMLStringNode> getChildNodes();

    List<XMLStringNode> getChildNodesByName(String name);

    Map<String, String> getAttributes();

    String getAttribute(String name);

    void setAttribute(String name, String value);

    List<XMLStringNode> getSubNodesWithName(String name);

    List<XMLStringNode> getSubNodesWithPath(String path);

    /**
     * ?????????
     * @param doc das haupt dokument
     * @param e das elternelement
     */
    void toDomNode(Document doc, Node e);

    String DumpData();

    String DumpData(int indent);

    default <T extends XMLStringNode> T toType(Class<T> tClass){
        return tClass.cast( this );
    };

    /**
     * Converts a DOMNode to a StringNode
     * @param n
     * @return
     */
    static XMLStringNode FromDomNode(Node n){
        switch (n.getNodeType()){
            case Node.ELEMENT_NODE:
                NamedNodeMap nm = n.getAttributes();
                HashMap<String, String> map = new HashMap<>();
                if(nm!=null){
                    for (int i = 0; i < nm.getLength(); i++) {
                        Node tempNode = nm.item(i);
                        map.put( tempNode.getNodeName(), tempNode.getNodeValue() );
                    }
                }
                NodeList nl = n.getChildNodes();
                ArrayList<XMLStringNode> nodelist = new ArrayList<>();
                if(nl!=null){
                    for (int i = 0; i < nl.getLength(); i++) {
                        XMLStringNode tempNode = FromDomNode( nl.item( i ) );
                        if(tempNode!=null) nodelist.add( tempNode );
                    }
                }
                return new XMLStringNode_Element( n.getNodeName(), map, nodelist );
            case Node.TEXT_NODE:
                if(n.getNodeValue().matches( "[\\n\\s]*" )) return null;
                return new XMLStringNode_Text( n.getNodeValue() );
            default:
                return null;
        }
    }

    /**
     * Utility Method to create the following XML Node structure:
     * <pre>
     *     &lt;tag&gt;value&lt;/tag&gt;
     * </pre>
     *
     * @param tag the tag of the mother node
     * @param value the value of the text node
     * @return the node structure
     */
    static XMLStringNode CreateSingletonNode(String tag, String value){
        XMLStringNode_Text valuenode = new XMLStringNode_Text( value );
        ArrayList<XMLStringNode> node = new ArrayList<>();
        node.add( valuenode );
        return new XMLStringNode_Element( tag, new HashMap<>(), node );
    }
}
