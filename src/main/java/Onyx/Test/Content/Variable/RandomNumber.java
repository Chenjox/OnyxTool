package Onyx.Test.Content.Variable;

import XMLString.XMLStringNode;

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
    public Type getVariableType() {
        return null;
    }
}
