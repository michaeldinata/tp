package seedu.duke.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.human.User;
import seedu.duke.ui.Ui;
import seedu.duke.watchlist.Watchlist;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DecoderTest {
    private Decoder decoder;
    private Ui ui;

    @BeforeEach
    void setUp() {
        decoder = new Decoder();
        ui = new Ui();
    }

    @Test
    void decodeUserProfileString_validString_decodedUserProfileObject() {
        String userString = "Tester | 01/01/2020 | Other";
        User userProfile = decoder.decodeUserString(ui, userString);
        String expected = "\n Name: Tester\n Birthdate: 01/01/2020\n Gender: Other";
        assertEquals(userProfile.toString(), expected);
    }

    @Test
    void decodeUserProfileString_invalidString_nullObject() {
        String userString = "Tester | 01/01/2020";
        User user = decoder.decodeUserString(ui, userString);
        assertNull(user);
    }

    @Test
    void decodeWatchlistString_validString_decodedWatchlistObject() {
        String watchlistString = "Test A | [abc, def]" + System.lineSeparator() + "Test B | []";
        ArrayList<Watchlist> watchlists = decoder.decodeWatchlistString(ui, watchlistString);

        String expected = "Test A" + System.lineSeparator();
        expected += "1. abc" + System.lineSeparator() + "2. def" + System.lineSeparator() + System.lineSeparator();
        expected += "Test B" + System.lineSeparator() + "Uhh.. It's empty.. :(" + System.lineSeparator();
        String actual = watchlists.get(0).toString() + System.lineSeparator() + watchlists.get(1).toString();

        assertEquals(actual, expected);
    }

    @Test
    void decodeWatchlistString_invalidString_notNullObject() {
        String watchlistString = "Test A" + System.lineSeparator() + "Test B";
        ArrayList<Watchlist> watchlists = decoder.decodeWatchlistString(ui, watchlistString);
        assertNotNull(watchlists);
    }
}
