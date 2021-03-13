package Onyx.Structure;

import XMLString.XMLStringNodeTree;
import XMLString.XMLStringNode_Element;
import XMLString.XMLStringTreeParser;

import java.io.File;

/**
 * Class to represent the imsmanifest.xml of an onyx test.
 */
public class AssessmentManifest {



    /**
     * the tree of imsmanifest.xml
     */
    private final XMLStringNodeTree imsmanifest;

    /**
     * Creates a AssessmentTest from a XML formatted String
     * @param XML_String the XML formatted String
     */
    public AssessmentManifest(String XML_String){
        this.imsmanifest = XMLStringTreeParser.parseString( XML_String );
    }

    /**
     * Creates a AssessmentTest from a File containing a XML formatted Document
     * @param f the File containing the XML
     */
    public AssessmentManifest(File f){
        this.imsmanifest = XMLStringTreeParser.parseDocument( f );
    }

    /**
     * Adds a Media Reference to the imsmanifest.xml
     * this is important if any assessment items use images in their itembody tag
     * @param Reference adds a reference to a media inside the imsmanifest.xml
     */
    public void AddMediaReference(String Reference){
        XMLStringNode_Element file = new XMLStringNode_Element( Reference );
        XMLStringNode_Element resource = imsmanifest.getNodesFromPath( "resources\\resource" ).get( 0 ).toType( XMLStringNode_Element.class );
        resource.addChildNode( file );
    }

    /**
     * Adds a AssessmentItem Reference into the imsmanifest data.
     * @param identifier the identifier of the Assessment Item.
     */
    public void AddAssessmentItemDependency(String identifier){
        XMLStringNode_Element dependency = new XMLStringNode_Element( "dependency" );
        dependency.setAttribute( "identifierref", identifier );
        imsmanifest.getNodesFromPath( "resources\\resource" ).get( 0 ).toType( XMLStringNode_Element.class ).addChildNode( dependency );

        XMLStringNode_Element resource = new XMLStringNode_Element( "resource" );
        resource.setAttribute( "identifier", identifier );
        resource.setAttribute( "type", "imsqti_item_xmlv2p1" );
        resource.setAttribute( "href", identifier+".xml" );
        XMLStringNode_Element resourcefile = new XMLStringNode_Element( "file" );
        resourcefile.setAttribute( "href",identifier+".xml" );
        resource.addChildNode( resourcefile );

        imsmanifest.getNodesFromPath( "resources" ).get( 0 ).toType( XMLStringNode_Element.class ).addChildNode( resource );
        //imstest.addAufgabenref( identifier );
    }
}
