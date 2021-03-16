package Onyx.Test.Content;

/**
 * An abstract representation of a Node that is included inside a <tt>testPart</tt> - Tag
 */
public abstract class OnyxContent {

    private final String identifier;

    protected OnyxContent(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
