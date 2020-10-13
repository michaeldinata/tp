package seedu.duke.watchlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WatchlistTest {
    private Watchlist emptyWatchlist;
    private Watchlist filledWatchlist;

    @BeforeEach
    void setUp() {
        ArrayList<String> animeList = new ArrayList<>();
        animeList.add("Cowboy Bebop");
        animeList.add("Fullmetal Alchemist");
        animeList.add("R.O.D - READ OR DIE");

        emptyWatchlist = new Watchlist("emptyWatchlist");
        filledWatchlist = new Watchlist("filledWatchlist", animeList);
    }

    @Test
    void testAnimeListToStringWithEmptyWatchlist() {
        String expected = "Uhh.. It's empty.. :(" + System.lineSeparator();
        assertEquals(emptyWatchlist.animeListToString(), expected);
    }

    @Test
    void testAnimeListToStringWithFilledWatchlist() {
        String expected = "1. Cowboy Bebop" + System.lineSeparator();
        expected += "2. Fullmetal Alchemist" + System.lineSeparator();
        expected += "3. R.O.D - READ OR DIE" + System.lineSeparator();
        assertEquals(filledWatchlist.animeListToString(), expected);
    }

    @Test
    void testToStringWithEmptyWatchlist() {
        String expected = "emptyWatchlist" + System.lineSeparator();
        expected += "Uhh.. It's empty.. :(" + System.lineSeparator();
        assertEquals(emptyWatchlist.toString(), expected);
    }

    @Test
    void testToStringWithFilledWatchlist() {
        String expected = "filledWatchlist" + System.lineSeparator();
        expected += "1. Cowboy Bebop" + System.lineSeparator();
        expected += "2. Fullmetal Alchemist" + System.lineSeparator();
        expected += "3. R.O.D - READ OR DIE" + System.lineSeparator();
        assertEquals(filledWatchlist.toString(), expected);
    }
}