package XMLString;

public enum XMLStringNodeType {
    TEXT(XMLStringNode_Text.class),
    ELEMENT(XMLStringNode_Element.class);

    final Class<? extends XMLStringNode> classType;

    XMLStringNodeType(Class<? extends XMLStringNode> c){
        this.classType = c;
    };

    public Class<? extends XMLStringNode> getClassType(){
        return classType;
    }
}
