package Onyx.Test;

import Onyx.Test.Content.Section.OnyxSection;

import java.util.List;

/**
 * A abstract representation of a Onyx "Test" instance.
 * Upon Serialisation, the imsmanifest.xml is also created.
 */
public class OnyxTest {

    /**
     * This is the Identifier of the Test. It will be present in the <tt>imsmanifest.xml</tt>.
     */
    private String identifier;

    /**
     * This is the name of the Test, e.g.: <tt>OnyxToolTest</tt>.
     */
    private String name;

    /**
     * This is the description of the test. Will be present in the <tt>imsmanifest.xml</tt>.
     */
    private String description;

    /**
     * The author of the onyx test.
     */
    private OnyxAuthor author;


    //Metadata Stuff...
    private static final String SCHEMA = "QTIv2.1 Package";
    private static final String SCHEMA_VERSION = "1.0.0";

    private static final String LEARNING_RESOURCE_TYPE_SOURCE = "QTIv2.1";
    private static final String LEARNING_RESOURCE_TYPE_VALUE = "ExaminationTest";

    private static final String VCARD_CRLF = "&#xD;\n";
    private static final String VCARD_VERSION = "4.0";
    private static final String VCARD_LANGUAGE = "de";
    private static final String VCARD_PRODUCT_ID = "ONYX Editor 9.7.3";

    /**
     * This returns the VCARD Data of a Onyx LOM (Learning Object Modell, IEEE Standard)
     * @return The VCARD Data, Ready to be used.
     */
    public String getVCARD(){
        return "BEGIN:VCARD"+ VCARD_CRLF +
                "VERSION:"+ VCARD_VERSION + VCARD_CRLF +
                "N;LANGUAGE="+ VCARD_LANGUAGE +":" + author.getLastName() + ";" + author.getFirstName() + ";;;"+ VCARD_CRLF +
                "FN:"+ author.getFirstName() + " " + author.getLastName() + VCARD_CRLF +
                "EMAIL:" + author.getEmail() + VCARD_CRLF +
                "PRODID:"+ VCARD_PRODUCT_ID + VCARD_CRLF +
                "END:VCARD"+ VCARD_CRLF;
    }
    //TODO Maybe refactor the Learning Object Model into a new class/package

    /* ############### TestContents API ############## */

    /**
     * This holds the sections of the test. The Assessments are inside the sections.
     */
    private List<OnyxSection> Sections;

}
