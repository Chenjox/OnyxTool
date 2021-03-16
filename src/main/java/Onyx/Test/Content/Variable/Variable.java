package Onyx.Test.Content.Variable;

import XMLString.XMLStringNode;

public interface Variable {

    String getIdentifier();
    /**
     * returns the &lt;TemplateDeclaration&gt; tag.
     * @return the prepared Node
     */
    XMLStringNode getTemplateDeclaration();

    /**
     * returns the &lt;setTemplateValue&gt; tag.
     * @return the prepared Node
     */
    XMLStringNode getSetTemplateValue();

    Type getVariableType();

    Order getVariableOrder();

    enum Type {
        IMAGE,
        FLOAT,
        INTEGER,
        STRING;
    }
    enum Order {
        POST,
        PRE;
    }
}
