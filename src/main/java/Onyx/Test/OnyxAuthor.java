package Onyx.Test;

//FIXME Eventuell dieses VCARD (eventuell VisitorCard?) System überdenken.

public class OnyxAuthor {

    private final String firstName;
    private final String lastName;
    private final String email;

    public OnyxAuthor(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
