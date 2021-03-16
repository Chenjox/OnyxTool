package Onyx.Test.Content.Section;

import Onyx.Test.Content.OnyxContent;

import java.util.List;

public class OnyxSection extends OnyxContent {

    private final String title;

    private final List<OnyxContent> contents;

    public OnyxSection(String identifier, String title, List<OnyxContent> contents) {
        super(identifier);
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }
}
