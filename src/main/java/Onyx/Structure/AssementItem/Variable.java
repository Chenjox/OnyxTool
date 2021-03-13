package Onyx.Structure.AssementItem;

import XMLString.XMLStringNode_Element;

public interface Variable {

    /**
     * The Identifier is a name of the variable e.g. <tt>{PRNG}</tt> would be identified with <tt>PRNG</tt>
     * @return the Identifier of the Variable
     */
    String getIdentifier();

    /**
     * ??? ???
     * @return the cardinality
     */
    default String getCardinality(){
        return "single";
    };

    /**
     * Returns the base type of the variable
     * @return the basetype
     */
    String getBaseType();

    /**
     * Sets the Base Type
     * @param type the base type
     */
    void setBaseType(String type);

    /**
     * Returns the variable in node form e.g. enclosing it in a <tt>setTemplateValue</tt> tag.
     * @return the XMLStringNode of the Variable
     */
    XMLStringNode_Element toValueNode();

    /**
     * Returns the variable in node form e.g. enclosing it in a <tt>templateDeclaration</tt>
     * @return the XMLStringNode of the Variable
     */
    XMLStringNode_Element toDeclarationNode();
}
