package Onyx.Test.Content.Variable;

import XMLString.XMLStringNode;

public abstract class Variable {

    private final String identifier;

    protected Variable(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier(){
        return identifier;
    };

    /**
     * returns the &lt;TemplateDeclaration&gt; tag.
     * @return the prepared Node
     */
    public abstract XMLStringNode getTemplateDeclaration();

    /**
     * returns the &lt;setTemplateValue&gt; tag.
     * @return the prepared Node
     */
    public abstract XMLStringNode getSetTemplateValue();

    public abstract Type getVariableType();

    public abstract boolean checkIntegrity();

    public enum Type {
        IMAGE,
        FLOAT,
        INTEGER,
        STRING;
    }
}
