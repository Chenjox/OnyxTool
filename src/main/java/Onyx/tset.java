package Onyx;

import org.w3c.dom.Document;
import uk.ac.ed.ph.jqtiplus.JqtiExtensionManager;
import uk.ac.ed.ph.jqtiplus.SimpleJqtiFacade;
import uk.ac.ed.ph.jqtiplus.node.RootNode;
import uk.ac.ed.ph.jqtiplus.node.expression.ExpressionType;
import uk.ac.ed.ph.jqtiplus.node.item.AssessmentItem;
import uk.ac.ed.ph.jqtiplus.node.item.template.processing.SetTemplateValue;
import uk.ac.ed.ph.jqtiplus.node.shared.VariableDeclaration;
import uk.ac.ed.ph.jqtiplus.node.test.AssessmentTest;
import uk.ac.ed.ph.jqtiplus.provision.RootNodeProvider;
import uk.ac.ed.ph.jqtiplus.reading.QtiObjectReadResult;
import uk.ac.ed.ph.jqtiplus.reading.QtiObjectReader;
import uk.ac.ed.ph.jqtiplus.reading.QtiXmlInterpretationException;
import uk.ac.ed.ph.jqtiplus.reading.QtiXmlReader;
import uk.ac.ed.ph.jqtiplus.utils.contentpackaging.ImsManifestReadResult;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlParseResult;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlReadResult;
import uk.ac.ed.ph.jqtiplus.xmlutils.XmlResourceNotFoundException;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.FileResourceLocator;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.FileSandboxResourceLocator;

import java.io.File;
import java.net.URI;

public class tset {

    public static void main(String[] args) throws XmlResourceNotFoundException, QtiXmlInterpretationException {
        /*
        XmlReadResult result = readUnitTestFile( new File( "onyxexample/imsmanifest.xml" ).toURI().toString(), false );
        System.out.println(result.toString());

        XmlParseResult parseResult = result.getXmlParseResult();
        System.out.println(parseResult.toString());

         */

        SimpleJqtiFacade test = new SimpleJqtiFacade();
        //FIXME Lesen des IMSMANIFEST! Siehe https://github.com/davemckain/qtiworks/blob/2d8c502d8f2b266fd80197ad19bcae3d4566276a/qtiworks-jqtiplus/src/test/java/uk/ac/ed/ph/jqtiplus/reading/QtiXmlReaderTest.java
        ImsManifestReadResult imsmanifest;

        QtiObjectReadResult<AssessmentTest> result = test.readQtiRootNode( new FileResourceLocator(), new File( "onyxexample/idTest3a3ad858-7c57-4b07-8ea7-ccf1cb851b24.xml" ).toURI(), false,
                                                                                  AssessmentTest.class );

        /*
        System.out.println( result.getRootNode() // AssessmentTest
                                    .getTestParts() // Tags TestPart
                                    .get( 0 ) // In Onyx gibt es nur einen...
                                    .getAssessmentSections() // List<AssessmentSection>
                                    .get( 0 ) // Jetzt erstmal eine
                                    .getSectionParts() // In einer Section kann auch noch eine Zweite sein
                                    .get( 0 ) // jetzt haben wir _etwas_
                                    .getAttributes() // Davon die Attribute
                                    .getUriAttribute( "href" ) // Und davon ein URI attribute
                                    .getValue() //Davon die URI
                                    .toString()
        );
        */
        QtiObjectReadResult<AssessmentItem> result2 = test.readQtiRootNode( new FileResourceLocator(), new File( "onyxexample/idde25f69e-355c-4718-b022-5dd5c5a0a8fb.xml" ).toURI(), false, AssessmentItem.class);

        System.out.println( result2.getRootNode().getIdentifier() );
        result2.getRootNode()
                .getTemplateProcessing()
                .getTemplateProcessingRules()
                .forEach( a -> {
                    if(a instanceof SetTemplateValue){
                        SetTemplateValue b = (SetTemplateValue) a;
                        if( b.getExpression().getType().equals( ExpressionType.getType( "customOperator" ) ))
                        b.getExpression().getExpressions().forEach( e -> System.out.println( e.getType().toString() ) );
                        System.out.println( b.getExpression().getClass().toString() );
                    }
                }
                );

        //Document document = result.getDocument();

        //System.out.println(d.getChildNodes().item( 0 ).getNodeName());
    }

    private static XmlReadResult readUnitTestFile(final String testFilePath, final boolean schemaValiadating)
            throws XmlResourceNotFoundException {
        final QtiXmlReader reader = new QtiXmlReader( new JqtiExtensionManager(  ) );
        final URI testFileUri = URI.create( testFilePath );
        return reader.read( new FileResourceLocator(), testFileUri, schemaValiadating);
    }

}
