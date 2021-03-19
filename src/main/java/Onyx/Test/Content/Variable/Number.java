package Onyx.Test.Content.Variable;

public abstract class Number extends Variable{

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
