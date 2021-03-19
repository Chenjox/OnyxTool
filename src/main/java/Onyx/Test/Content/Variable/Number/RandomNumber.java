package Onyx.Test.Content.Variable.Number;

import XMLString.XMLStringNode;
import XMLString.XMLStringNode_Element;

public class RandomNumber extends Number{

    private java.lang.Number upperBound;

    private java.lang.Number lowerBound;

    protected RandomNumber(String identifier, Type varType) {
        super(identifier, varType);
    }

    @Override
    public XMLStringNode getTemplateDeclaration() {
        return null;
    }

    @Override
    public XMLStringNode getSetTemplateValue() {
        return null;
    }

    @Override
    public boolean checkIntegrity() {
        return true;
    }
}
