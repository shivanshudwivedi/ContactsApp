package edu.trinity.cpsc215f23;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Contacts App.
 */
class ContactsAppTest {

    /**
     * The number of items in the sample data.
     */
    int size;

    @Test
    void searchForNonExisting() {
        ContactsApp app = new ContactsApp();

        assertTrue(app.getContacts().isEmpty());
        String unknownSoldier = "Waldo";
        assertNull(app.getContacts().get(unknownSoldier), String.format("'%s' was not found.", unknownSoldier));
    }

    @Test
    void addAndRemove() {
        ContactsApp app = new ContactsApp();

        String johnMuirName = "Muir, John";
        app.getContacts().put(johnMuirName, app.parseCommunications("email: john.muir@sierraclub.org, linkedin: johnmuir"));
        String johnMuirEmail = app.getContacts().get(johnMuirName).get(Communications.EMAIL);
        assertFalse(johnMuirEmail.isEmpty(), String.format("Contact '%s' has email `%s`.", johnMuirName, johnMuirEmail));

        app.getContacts().remove(johnMuirName);
        assertNull(app.getContacts().get(johnMuirName), String.format("Contact %s was removed.", johnMuirName));
        assertTrue(app.getContacts().isEmpty());
    }

    @Test
    @Order(3)
    void addMode() {
        ContactsApp app = getPopulatedContactsApp();
        assertEquals(app.getContacts().size(), 5);
    }

    @Test
    void listContactsInformation() {
        ContactsApp app = getPopulatedContactsApp();

        assertTrue(app.getContacts().toString().length() > 300,
                String.format("Form 1: Raw list has expected length: %d. Content: %n%s",
                        app.getContacts().toString().length(), app.getContacts().toString()));
    }

    @Test
    void listFormattedContactsInformation() {
        ContactsApp app = getPopulatedContactsApp();

        String allContacts = app.listAllContacts();
        assertEquals(allContacts.length(), 361,
                String.format("Form 2: Formatted list has expected length: %d. Content: %n%s",
                        allContacts.length(), allContacts));
    }

    @Test
    void listContactNames() {
        ContactsApp app = getPopulatedContactsApp();

        String allNames = app.listAllContactNames();
        System.out.println(allNames);
        assertTrue(allNames.length() > 100,
                String.format("All contact names: %d. Content: %n%s",
                        allNames.length(), allNames));
    }

    @Test
    void listContactCommunications() {
        ContactsApp app = getPopulatedContactsApp();

        String allValues = app.listAllContactCommunications();
        assertTrue(allValues.length() > 280,
                String.format("All values of all contacts have expected length: %d. Content: %n%s",
                        allValues.length(), allValues));
    }

    private ContactsApp getPopulatedContactsApp() {
        // Add more nice people (feel free to replace with your own data)
        String[] names = {
                "Elizabeth Wathuti",
                "Shiva, Vandana",
                "Ceesay, Isatou",
                "LaDuke, Winona",
                "Maathai, Wangari"};
        String[] coms = {
                "email: lizwathuti@gmail.com, website: linktr.ee/lizwathuti, linkedin: elizabeth-wathuti-8415a299",
                "web: vandanashiva.com",
                "linkedin: isatou-ceesay-4a837216",
                "li: winona-laduke-71861818, w: https://en.wikipedia.org/wiki/Winona_LaDuke",
                "li: wanjira-mathai-1b561ab, mobile: 44-023-233-2323"
        };

        size = names.length;

        // Add above data to contacts
        ContactsApp app = new ContactsApp();

        for (int i = 0; i < names.length; i++) {
            app.getContacts().put(names[i], app.parseCommunications(coms[i]));
        }
        return app;
    }
}