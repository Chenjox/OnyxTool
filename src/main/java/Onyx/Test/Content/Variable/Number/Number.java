package Onyx.Test.Content.Variable.Number;

import Onyx.Test.Content.Variable.Variable;

abstract class Number extends Variable {

    private final Type varType;

    protected Number(String identifier, Type varType){
        super(identifier);
        this.varType = varType;
    }

    @Override
    public Type getVariableType() {
        return varType;
    }
}
