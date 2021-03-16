package Onyx.Test.Content.Variable;

import Onyx.Util.StringUtils;
import XMLString.XMLStringNode;

public class MaximaVariable implements Variable{

    private final Type varType;
    private final String formula;
    private final String identifier;

    public MaximaVariable(String identifier, Type varType, String formula){
        this.identifier = identifier;
        this.varType = varType;
        this.formula = formula;
    }

    public boolean isFormulaNaiveValid(){
        return ( StringUtils.CountCharsInString(formula, '(')==StringUtils.CountCharsInString(formula, ')') ) &&
                ( StringUtils.CountCharsInString(formula, '[')==StringUtils.CountCharsInString(formula, ']') ) &&
                ( StringUtils.CountCharsInString(formula, '{')==StringUtils.CountCharsInString(formula, '}') );
    }

    public String getFormula(){
        return formula;
    }

    @Override
    public String getIdentifier() {
        return identifier;
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
