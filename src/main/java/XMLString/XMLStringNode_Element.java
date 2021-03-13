package XMLString;

import org.w3c.dom.*;

import java.util.*;

public class XMLStringNode_Element implements XMLStringNode {

    private final String nodeName;

    private final XMLStringNodeType nodeType = XMLStringNodeType.ELEMENT;

    private final HashMap<String, String> attributes;

    private final ArrayList<XMLStringNode> childNodes;

    public XMLStringNode_Element(String nodeName, HashMap<String, String> attributes, ArrayList<XMLStringNode> childs){
        this.nodeName=nodeName;
        this.attributes= attributes;
        this.childNodes=childs;
    }

    public XMLStringNode_Element(String nodeName){
        this.nodeName=nodeName;
        this.attributes= new HashMap<>();
        this.childNodes= new ArrayList<>();
    }
    /**
     * Returns the tag name of the Node.
     * @return the tag name of the Node.
     */
    @Override
    public String getNodeName() {
        return nodeName;
    }

    /**
     * @return XMLStringNodeType.ELEMENT
     */
    @Override
    public XMLStringNodeType getType() {
        return nodeType;
    }

    /**
     * There is no Content, such is the way of the content
     * @return an empty String
     */
    @Override
    public String getContent() {
        return "";
    }

    @Override
    public void setContent(String newContent) {
    }

    @Override
    public boolean hasChildNodes() {
        return childNodes.isEmpty();
    }

    @Override
    public List<XMLStringNode> getChildNodes() {
        return childNodes;
    }

    public void addChildNode(XMLStringNode node){
        this.childNodes.add( node );
    }

    @Override
    public List<XMLStringNode> getChildNodesByName(String name) {
        ArrayList<XMLStringNode> namedNodes = new ArrayList<>();
        for (XMLStringNode n:childNodes) {
            if(n.getNodeName().equals( name )) namedNodes.add( n );
        }
        return namedNodes;
    }

    @Override
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String getAttribute(String name) {
        return attributes.get( name );
    }

    @Override
    public void setAttribute(String name, String value) {
        if(attributes.containsKey(name))attributes.replace( name, value );
        else attributes.put( name, value );
    }

    @Override
    public List<XMLStringNode> getSubNodesWithName(String name) {
        List<XMLStringNode> resultList = new ArrayList<>();
        Deque<XMLStringNode> stack = new ArrayDeque<>();
        stack.push( this );
        while(!stack.isEmpty()){
            XMLStringNode temp = stack.pop();
            for (XMLStringNode n : temp.getChildNodes()) {
                if(n.getNodeName().equals( name )) resultList.add( n );
                stack.push( n );
            }
        }
        return resultList;
    }

    @Override
    public List<XMLStringNode> getSubNodesWithPath(String path) {
        List<XMLStringNode> resultList = new ArrayList<>();
        String[] pathArguments = path.split( "\\\\" );
        int treedepth = 0;
        boolean poppedOnly = true;
        boolean lastIndexReached;
        //Validating the first argument in the path
        //Init Stack, Put RootNode onto the stack.
        Deque<XMLStringNode> stack = new ArrayDeque<>();
        stack.push( this );
        while(!stack.isEmpty()){
            XMLStringNode temp = stack.pop();
            lastIndexReached = treedepth==pathArguments.length-2;
            if((treedepth<pathArguments.length-1)) {
                for (XMLStringNode n : temp.getChildNodes()) {
                    if(n.getNodeName().equals( pathArguments[treedepth+1] )){
                        if(lastIndexReached) resultList.add( n );
                        stack.push( n );
                        poppedOnly=false;
                    }
                }
            }else {
                poppedOnly=true;
            }
            if(!poppedOnly)treedepth++;
            else treedepth--;
        }
        return resultList;
    }

    @Override
    public void toDomNode(Document doc, Node e) {
        Element here = doc.createElement(nodeName);
        for (String key: attributes.keySet()) {
            Attr attr = doc.createAttribute(key);
            attr.setValue( attributes.get( key ) );
            here.setAttributeNode( attr );
        }
        for (XMLStringNode x:childNodes) {
            x.toDomNode( doc, here );
        }
        e.appendChild( here );
    }

    @Override
    public String DumpData() {
        return DumpData(0);
    }
    
    public String DumpData(int indent){
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            b.append( '-' );
        }
        b.append( "ElementNode: " ).append( nodeName ).append( "," );
        if(!attributes.isEmpty()) {
            b.append( " Attributes: " );
            for (String key : attributes.keySet()) {
                b.append( key ).append( " : " ).append( attributes.get( key ) ).append( "," );
            }
        }
        b.append( '\n' );
        for (XMLStringNode n:childNodes) {
            b.append( n.DumpData(indent+1) );
        }
        return b.toString();
    }
}
