package seedu.duke.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.watchlist.Watchlist;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class EncoderTest {
    private Encoder encoder;

    @BeforeEach
    void setUp() {
        encoder = new Encoder();
    }

    @Test
    void encodeUserProfile_validObject_encodedString() throws ParseException, AniException {
        User user = new User("Human", "01/01/1990", "Other");
        String expected = "Human | 01/01/1990 | Other";
        assertEquals(encoder.encodeUser(user), expected);
    }

    @Test
    void encodeUserProfile_nullObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            encoder.encodeUser(null);
            fail();
        });
    }

    @Test
    void encodeWatchlist_validObject_encodedString() {
        ArrayList<String> actionAnimeList = new ArrayList<>();
        actionAnimeList.add("Elfen Lied");
        actionAnimeList.add("Gungrave");
        actionAnimeList.add("Mezzo");

        ArrayList<String> adventureAnimeList = new ArrayList<>();
        adventureAnimeList.add("The Slayers Next");
        adventureAnimeList.add("My Neighbor Totoro");
        adventureAnimeList.add("Pokémon");

        ArrayList<Watchlist> watchlists = new ArrayList<>();
        watchlists.add(new Watchlist("Action Anime", actionAnimeList));
        watchlists.add(new Watchlist("Adventure Anime", adventureAnimeList));
        watchlists.add(new Watchlist("Empty List"));

        String expected = "Action Anime | [Elfen Lied, Gungrave, Mezzo]" + System.lineSeparator();
        expected += "Adventure Anime | [The Slayers Next, My Neighbor Totoro, Pokémon]" + System.lineSeparator();
        expected += "Empty List | []" + System.lineSeparator();
        assertEquals(encoder.encodeWatchlist(watchlists), expected);
    }

    @Test
    void encodeWatchlist_nullObject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            encoder.encodeWatchlist(null);
            fail();
        });
    }
}