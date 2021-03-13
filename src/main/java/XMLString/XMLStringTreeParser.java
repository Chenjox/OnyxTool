package XMLString;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;

public class XMLStringTreeParser {

    public static XMLStringNodeTree parseDocument(File doc){
        Document d = null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            d = builder.parse( doc );
        }catch (Exception e){
            e.printStackTrace();
        }
        assert d != null;
        d.getDocumentElement().normalize();

        Element root = d.getDocumentElement();
        XMLStringNode rootNode = XMLStringNode.FromDomNode( root );
        return new XMLStringNodeTree(rootNode);
    }

    /**
     * Parses a String in XML Format to a XMLStringNodeTree
     * Returns null under certain instances.
     * @param xmlstring
     * @return the XMLStringNodeTree representing the String.
     */
    public static XMLStringNodeTree parseString(String xmlstring){
        Document d = null;
        try {
            Source source = new StreamSource( new StringReader( xmlstring ) );
            DOMResult result = new DOMResult();
            TransformerFactory.newInstance().newTransformer().transform( source, result );
            d = (Document) result.getNode();
        }catch(Exception e){
            throw new AssertionError("Hier ist was komisches Passiert");
        }
        if(d==null) return null;

        XMLStringNode rootNode = XMLStringNode.FromDomNode( d.getDocumentElement() );
        return new XMLStringNodeTree( rootNode );
    }

    private static void DumpNodeData(Node node){
        DumpNodeData( node, 0 );
    }

    private static void DumpNodeData(Node node, int indent){
        StringBuilder ind = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            ind.append( "| " );
        }
        String s = ind.toString();
        DumpDataBasedOnType( node, s );
        if (node.hasAttributes()) {
            NamedNodeMap nodeMap = node.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); i++) {
                Node tempNode = nodeMap.item( i );
                DumpDataBasedOnType( tempNode, s );
            }
        }
        if (node.hasChildNodes()) {
            //We got more childs; Let's visit them as well
            NodeList l = node.getChildNodes();
            for (int i = 0; i < l.getLength(); i++) {
                DumpNodeData( l.item( i ), indent + 1);
            }
        }
    }
    private static void DumpDataBasedOnType(Node n, String indent){
        switch (n.getNodeType()){
            case Node.ELEMENT_NODE:
                System.out.println(indent+"ElementName: "+n.getNodeName()+", ");
                break;
            case Node.ATTRIBUTE_NODE:
                System.out.println(indent+"-AttributeName: "+n.getNodeName()+", Value: "+n.getNodeValue());
                break;
            case Node.TEXT_NODE:
                if(n.getNodeValue().matches( "\\n\\s*" )) System.out.println(indent+"Node is empty!");else System.out.println(indent+"TextNodeName: "+n.getNodeName()+", Value: "+n.getNodeValue());
                break;
            default:
                System.out.println("Not Know node Type: "+n.getNodeType());
        }
    }

    public static void WriteToXMLFile(File f, XMLStringNodeTree tree) {
        try {
            DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            tree.getRootNode().toDomNode( doc, doc );
            //Write to file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");

            DOMSource source = new DOMSource( doc );
            StreamResult result = new StreamResult( f );
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
