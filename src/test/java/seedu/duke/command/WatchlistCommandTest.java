package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.anime.AnimeData;
import seedu.duke.exception.AniException;
import seedu.duke.human.User;
import seedu.duke.human.UserManagement;
import seedu.duke.storage.Storage;
import seedu.duke.watchlist.Watchlist;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WatchlistCommandTest {
    AnimeData animeData;
    UserManagement userManagement;

    //@@author OngDeZhi
    @BeforeEach
    void setUp() throws ParseException, AniException {
        animeData = new AnimeData(new ArrayList<>());
        Storage storage = new Storage("profile-test.txt", "watchlist-test.txt");
        userManagement = new UserManagement(storage);

        User activeUser = new User("Testing", "01/01/2000", "Male");
        userManagement.setActiveUser(activeUser);

        Watchlist secondWatchlist = new Watchlist("Second");
        secondWatchlist.addAnimeToList("The Slayers Next");
        secondWatchlist.addAnimeToList("Pokémon");

        ArrayList<Watchlist> watchlists = activeUser.getWatchlistList();
        watchlists.add(secondWatchlist);
        activeUser.setWatchlistList(watchlists);
    }

    @Test
    void execute_invalidParameters_throwsAniException() {
        WatchlistCommand watchlistCommand = new WatchlistCommand("");
        assertThrows(AniException.class, () -> {
            watchlistCommand.execute(animeData, userManagement);
        });
    }

    @Test
    void execute_unknownOption_throwsAniException() {
        WatchlistCommand watchlistCommand = new WatchlistCommand("-t test");
        assertThrows(AniException.class, () -> {
            watchlistCommand.execute(animeData, userManagement);
        });
    }

    @Test
    void execute_validParametersForCreateWatchlist_success() throws AniException {
        WatchlistCommand watchlistCommand = new WatchlistCommand("-n Test");
        String expected = "Watchlist created successfully!";
        assertEquals(watchlistCommand.execute(animeData, userManagement), expected);
    }

    @Test
    void execute_invalidWatchlistNameForCreateWatchlist_throwsAniException() {
        WatchlistCommand watchlistCommand = new WatchlistCommand("-n");
        assertThrows(AniException.class, () -> {
            watchlistCommand.execute(animeData, userManagement);
        });
    }

    @Test
    void execute_duplicateWatchlistNameForCreateWatchlist_success() {
        WatchlistCommand watchlistCommand = new WatchlistCommand("-n Default");
        assertThrows(AniException.class, () -> {
            watchlistCommand.execute(animeData, userManagement);
        });
    }

    @Test
    void execute_validParametersForListAllWatchlist_success() throws AniException {
        WatchlistCommand watchlistCommand = new WatchlistCommand("-l");
        String expected = "Currently, you have 2 watchlist(s):" + System.lineSeparator();
        expected += "\t1. Default" + System.lineSeparator();
        expected += "\t2. Second";
        assertEquals(watchlistCommand.execute(animeData, userManagement), expected);
    }
}