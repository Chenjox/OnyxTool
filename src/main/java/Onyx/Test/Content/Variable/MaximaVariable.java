package Onyx.Test.Content.Variable;

import XMLString.XMLStringNode;

public class MaximaVariable implements Variable{

    private final Type varType;
    private final String formula;

    public MaximaVariable(Type varType, String formula){
        this.varType = varType;
        this.formula = formula;
    }

    public boolean isFormulaValid(){
        return false;
    }

    public String getFormula(){
        return formula;
    }

    @Override
    public String getIdentifier() {
        return null;
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

    @Override
    public Order getVariableOrder() {
        return null;
    }
}
