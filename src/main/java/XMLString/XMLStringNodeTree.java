package XMLString;

import java.util.*;

public class XMLStringNodeTree {

    private final XMLStringNode rootNode;

    public XMLStringNodeTree(XMLStringNode rootNode){
        this.rootNode = rootNode;
    }

    public String dump(){
        return rootNode.DumpData();
    }

    public XMLStringNode getRootNode() {
        return rootNode;
    }

    /**
     * breadth-first search of a tree, done iteratively. Returns the List of found nodes
     * @param path the path of the searched node
     * @return the list of found nodes, empty if none are found
     */
    public List<XMLStringNode> getNodesFromPath(String path){
        //Getting and setting up the components for the stack;
        List<XMLStringNode> resultList = new ArrayList<>();
        String[] pathArguments = path.split( "\\\\" );
        int treedepth = 0;
        boolean poppedOnly = true;
        boolean lastIndexReached;
        //Validating the first argument in the path
        if(!pathArguments[0].equals( rootNode.getNodeName() ))return resultList;
        //Init Stack, Put RootNode onto the stack.
        Deque<XMLStringNode> stack = new ArrayDeque<>();
        stack.push( rootNode );
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
}
