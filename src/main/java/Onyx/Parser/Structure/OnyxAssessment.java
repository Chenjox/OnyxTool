package Onyx.Parser.Structure;

import XMLString.XMLStringNode;
import XMLString.XMLStringNodeTree;

import java.util.List;

public interface OnyxAssessment {

    XMLStringNodeTree getTree();

    default List<XMLStringNode> getNodeFromPath(String path){
        return getTree().getNodesFromPath(path);
    }

}
