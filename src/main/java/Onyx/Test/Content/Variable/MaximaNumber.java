package Onyx.Test.Content.Variable;

import XMLString.XMLStringNode;

public class MaximaNumber extends Number{

    private String formula;

    protected MaximaNumber(String identifier, Type varType, String formula) {
        super(identifier, varType);
        this.formula = formula;
    }

    public String getFormula(){
        return formula;
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
