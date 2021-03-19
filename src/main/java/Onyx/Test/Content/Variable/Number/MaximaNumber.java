package Onyx.Test.Content.Variable.Number;

import Onyx.Test.Content.Variable.Variable;
import XMLString.XMLStringNode;

import static Onyx.Util.StringUtils.*;

public class MaximaNumber extends Number{

    private final String formula;

    protected MaximaNumber(String identifier, Variable.Type varType, String formula) {
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

    /**
     * Checks whether the given formula has the right amount of parentheses.
     * @return whether the given formula has the right amount of parentheses.
     */
    @Override
    public boolean checkIntegrity() {
        return CountCharsInString( formula, '(' )==CountCharsInString( formula,')' ) &&
                CountCharsInString( formula, '[' )==CountCharsInString( formula, ']' ) &&
                CountCharsInString( formula, '{' )==CountCharsInString( formula, '}' );
    }
}
