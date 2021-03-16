package Onyx.Parser.Structure;

import XMLString.XMLStringNodeTree;

public class AssessmentTest implements OnyxAssessment{

    private XMLStringNodeTree TestTree;

    @Override
    public XMLStringNodeTree getTree() {
        return TestTree;
    }
}
